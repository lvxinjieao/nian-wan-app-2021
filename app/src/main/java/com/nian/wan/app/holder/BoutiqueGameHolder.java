package com.nian.wan.app.holder;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.DataBean;
import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.download.DownManager;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;

import org.xutils.ex.DbException;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoutiqueGameHolder extends BaseHolder<GameInfo> {

    @BindView(R.id.game_icon)
    public ImageView game_icon;

    @BindView(R.id.game_name)
    public TextView game_name;

    @BindView(R.id.game_num)
    public TextView game_num;

    @BindView(R.id.game_download)
    public TextView game_download;

    private Activity activity;

    private DownDataBean down;
    private int percent = -2;
    private GameInfo gameInfo;
    private boolean suo = true;


    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.boutique_game_item, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }


    @Override
    protected void refreshView(final GameInfo gameInfo, int position, final Activity activity) {
        String s = activity.getClass().toString();
        this.activity = activity;
        this.gameInfo = gameInfo;

        //这一步是判断  数据库有没有这个游戏的下载记录 (根据当前游戏ID查询)
        down = DownManager.getInstance().getDownBean(gameInfo.id);
        if (down != null) {
            //如果有记录  判断当前下载状态
            percent = Aria.download(this).load(down.DownUrl).getTaskState();
            //percent的下载状态里没有  安装完成打开状态  需要自行判断
            if (down.packageName != null && Utils.isInstall(x.app(), down.packageName)) {
                //根据包名判断如果该游戏已经安装过    下载状态改为已安装 =8
                percent = 8;
            }

            if (percent == 3) {
                percent = -2;
            }
        }
        init(gameInfo);
        status(percent);
    }


    public void init(final GameInfo gameInfo) {

        Glide.with(x.app()).load(gameInfo.iconurl).error(R.drawable.default_picture).transform(new RoundedCorners(6)).into(game_icon);

        String jieQu = Utils.getJieQu(gameInfo.name);
        game_name.setText(jieQu);

        String player = gameInfo.playNum;
        if (!TextUtils.isEmpty(player))
            game_num.setText(player + "人在玩");
        else
            game_num.setText("推荐游戏");

        game_download.setText("下载");
        if (gameInfo.canDownload == 0) {
            game_download.setBackgroundResource(R.drawable.bian_kuang_gray);
            game_download.setTextColor(activity.getResources().getColorStateList(R.color.gray2));
            game_download.setEnabled(false);
        } else {
            game_download.setBackgroundResource(R.drawable.bian_kuang_green);
            game_download.setTextColor(activity.getResources().getColorStateList(R.color.green));
            game_download.setEnabled(true);
        }


        game_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.getLoginUser() != null) {
                    download(); //下载
                } else {
                    new DialogGoLogin(activity, R.style.MyDialogStyle, "登录后可开始游戏~").show();
                }
            }
        });
    }

    /**
     * 下载按钮的执行动作
     */
    public void download() {
        down = DownManager.getInstance().getDownBean(gameInfo.id);
        switch (percent) {
            //未下载
            case -2:
                if (suo) {
                    suo = false;
                    Utils.getDownLoadUrl(handler, gameInfo.id);
                    taskWait(null, null, true);
                }
                break;
            //下载中
            case 4:
                Aria.download(this).load(down.DownUrl).stop();
                break;
            //暂停中
            case 2:
                DownManager.getInstance().down(down);
                break;
            //下载成功
            case 1:
                DownManager.getInstance().install(down);
                break;
            //安装成功
            case 8:
                DownDataBean open = DownManager.getInstance().open(down);
                status(open.btnStatus);
                break;
            //下载失败
            case 0:
                DownManager.getInstance().down(down);
                break;
        }
    }

    /**
     * 刷新状态
     */
    public void status(int btnStatus) {
        switch (btnStatus) {
            //未下载状态
            case -2:
                taskNoDown();
                break;
            //等待状态
            case 3:
                taskWait(null, null, true);
                break;
            //下载完成未安装状态
            case 1:
                percent = 1;
                game_download.setText("安装");
//                game_num.setVisibility(View.VISIBLE);
//                downLayout.setVisibility(View.GONE);
                break;
            //已安装打开状态
            case 8:
                percent = 8;
                game_download.setText("打开");
//                game_num.setVisibility(View.VISIBLE);
//                downLayout.setVisibility(View.GONE);
                break;
            //下载失败状态
            case 0:
                taskFail(null, null, true);
                break;
            //下载暂停状态
            case 2:
                taskStop(null, null, true);
                break;
        }
    }

    /**
     * 获得下载链接
     */
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    //解析网络请求 获取游戏链接
                    DataBean dataBean = HttpUtils.DNSdownUrl(msg.obj.toString());
                    if (dataBean != null && !dataBean.url.equals("")) {
                        DownDataBean downDataBean = new DownDataBean();
                        downDataBean.id = gameInfo.id;
                        downDataBean.DownUrl = dataBean.url;
                        DownManager.getInstance().down(downDataBean);

                        gameInfo.record_id = dataBean.record_id;
                        DownManager.getInstance().copy(gameInfo);
                    } else {
                        Utils.TS("游戏链接为空");
                        taskNoDown();
                    }
                    suo = true;
                    break;

                case 2:
                    Utils.TS("获取下载链接失败");
                    taskNoDown();
                    suo = true;
                    break;
            }
        }
    };

    /**
     * 确认下载状态      例如 在安装界面  未点击安装按钮而是点击的返回按钮
     */
    public void ConfirmationState() {
        if (percent == 1 || percent == 8) {
            taskComplete(null, null, true);
        }
    }

    /**
     * 等待
     */
    public void taskWait(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            percent = 3;
            game_download.setText("等待");
//            game_num.setVisibility(View.GONE);
//            downLayout.setVisibility(View.VISIBLE);
//            progressbar.setProgress(0);
//            downHint.setText( "--/--" );
//            spend.setText("等待");
        }
    }

    /**
     * 下载暂停状态
     */
    public void taskStop(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        //判断下载数据是否为空   如果为空 或者 key和下载地址不对应  说明更新的不应该是这个条目
        //应该更新 key 和下载地址相同的条目
        if (down != null && (execute || key.equals(down.DownUrl))) {
            percent = 2;
            game_download.setText("继续");
//            game_num.setVisibility(View.GONE);
//            downLayout.setVisibility(View.VISIBLE);
//            spend.setText("暂停中");
//            if (task!=null){
//                progressbar.setProgress(task.getPercent());
//                String size = Utils.getSize(task.getEntity().getCurrentProgress(), task.getFileSize());
//                downHint.setText(size);
//            }else {
//                downHint.setText(gameInfo.size);
//            }
        }
    }

    /**
     * 下载取消状态
     */
    public void taskCancel(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            taskNoDown();
        }
    }

    /**
     * 下载失败状态
     */
    public void taskFail(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            ToastUtil.showToast("下载链接有误");
            percent = 0;
            game_download.setText("重试");
//            game_num.setVisibility(View.VISIBLE);
//            downLayout.setVisibility(View.GONE);
            DownManager.getInstance().setState(gameInfo.id);
            try {
                Aria.download(this).load(down.DownUrl).cancel();//删除下载数据记录
                DatabaseOpenHelper.getInstance().deleteById(DownDataBean.class, down.id);//删除数据库记录
                DownManager.getInstance().Delete(down.id);//删除本地下载文件    如果有则删除
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载完成未安装  及  安装完成打开状态
     */
    public void taskComplete(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            DownDataBean downDataBean = DownManager.getInstance().RealState(gameInfo.id);
            if (downDataBean != null) {
                status(downDataBean.btnStatus);
            }
        }
    }

    /**
     * 未下载状态
     */
    public void taskNoDown() {
        percent = -2;
        game_download.setText("下载");
//        game_num.setVisibility(View.VISIBLE);
//        downLayout.setVisibility(View.GONE);
    }

    /**
     * 下载中状态
     */
    public void taskRuning(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            percent = 4;
            game_download.setText("暂停");
//            game_num.setVisibility(View.GONE);
//            progressbar.setProgress(task.getPercent());
//            downLayout.setVisibility(View.VISIBLE);
//            if(task.getFileSize()==0){
//                String formatSize = CleanMessageUtil.getFormatSize(task.getEntity().getCurrentProgress());
//                downHint.setText( formatSize+ "/0M");
//            }else{
//                String size = Utils.getSize(task.getEntity().getCurrentProgress(), task.getFileSize());
//                spend.setText(task.getConvertSpeed());
//                downHint.setText(size);
//            }
        }
    }
}
