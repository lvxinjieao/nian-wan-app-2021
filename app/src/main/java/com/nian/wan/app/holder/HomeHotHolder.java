package com.nian.wan.app.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.bean.DataBean;
import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.download.DownManager;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.CleanMessageUtil;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.ShapeImageView;

import org.xutils.ex.DbException;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

;

/**
 * 描述：首页游戏条目
 */
public class HomeHotHolder extends RecyclerView.ViewHolder {

    private final Activity activity;

    @BindView(R.id.game_bg)
    public ShapeImageView game_bg;

    @BindView(R.id.game_desc)
    public TextView game_desc;

    @BindView(R.id.game_icon)
    public ShapeImageView game_icon;

    @BindView(R.id.game_name)
    public TextView game_name;

    @BindView(R.id.game_package)
    public TextView game_package;

    @BindView(R.id.game_size)
    public TextView game_size;

    @BindView(R.id.game_player_num)
    public TextView game_player_num;


    @BindView(R.id.game_fanli)
    public TextView game_fanli;


    @BindView(R.id.game_download)
    public TextView game_download;

    @BindView(R.id.down_layout)
    public RelativeLayout downLayout;

    @BindView(R.id.down_progressbar)
    public RoundCornerProgressBar downProgressbar;

    @BindView(R.id.down_spend)
    public TextView downSpend;

    @BindView(R.id.down_hint)
    public TextView downHint;

    @BindView(R.id.ll_hint_msg)
    public LinearLayout llHintMsg;

    private GameInfo gameInfo;
    private int percent = -2;               //默认数据库没有下载记录   下载状态为-2
    private boolean suo = true;             //获取游戏链接过程中  按钮是否可点击
    private DownDataBean down;


    public HomeHotHolder(View itemView, Activity act) {
        super(itemView);
        activity = act;
        ButterKnife.bind(this, itemView);
    }

    public void setData(final GameInfo gameInfo) {
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
        //给各个控件赋值 展示数据
        setdata(gameInfo);
        Status(percent);
    }


    public void setdata(final GameInfo gameInfo) {
        String jieQu = Utils.getJieQu(gameInfo.name);
        if (jieQu.length() >= 12) {
            game_name.setText(jieQu.substring(0, 12) + "...");
        } else {
            game_name.setText(jieQu);
        }

        Glide.with(x.app()).load(gameInfo.bg_img).error(R.drawable.default_picture).into(game_bg);


        Glide.with(x.app()).load(gameInfo.iconurl).error(R.drawable.default_picture).into(game_icon);

        game_desc.setText(gameInfo.features);

        game_download.setText("下载游戏");

        if (gameInfo.canDownload == 0) {
            game_download.setBackgroundResource(R.drawable.bian_kuang_gray);
            game_download.setTextColor(activity.getResources().getColorStateList(R.color.qiangray));
            game_download.setEnabled(false);
        } else {
            game_download.setBackgroundResource(R.drawable.bian_kuang_green);
            game_download.setTextColor(activity.getResources().getColorStateList(R.color.zhuse_lan));
            game_download.setEnabled(true);
        }

        if (!TextUtils.isEmpty(gameInfo.size)) {
            game_size.setText(gameInfo.size);
        } else {
            game_size.setText("0M");
        }


        if (!TextUtils.isEmpty(gameInfo.playNum)) {
            game_player_num.setText("已有" + gameInfo.playNum + "人下载");
        } else {
            game_player_num.setText("");
        }


        if (gameInfo.fanli == null || gameInfo.fanli.equals("0.00")) {
            game_fanli.setVisibility(View.INVISIBLE);
        } else {
            game_fanli.setVisibility(View.VISIBLE);
            game_fanli.setText("返利" + gameInfo.fanli.replace(".00", "") + "%");
        }

        if (gameInfo.gift == 0) {
            game_package.setVisibility(View.GONE);
        } else {
            game_package.setVisibility(View.VISIBLE);
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

        game_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, GameDetailActivity.class);
                intent.putExtra("game_id", gameInfo.id);
                activity.startActivity(intent);
            }
        });
    }

    //开始游戏网络请求
    private void StartGame(GameInfo gameInfo) {

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
                Status(open.btnStatus);
                break;
            //下载失败
            case 0:
                DownManager.getInstance().down(down);
                break;
        }
    }

    /**
     * 刷新状态
     *
     * @param btnStatus
     */
    public void Status(int btnStatus) {
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
                Log.e("下载状态", "----下载完成未安装状态");
                percent = 1;
                game_download.setText("安装游戏");
//                downLayout.setVisibility(View.GONE);
//                llHintMsg.setVisibility(View.VISIBLE);
                break;
            //已安装打开状态
            case 8:
                Log.e("下载状态", "----已安装打开状态");
                percent = 8;
                game_download.setText("打开游戏");
                downLayout.setVisibility(View.GONE);
                llHintMsg.setVisibility(View.VISIBLE);
                break;
            //下载失败状态
            case 0:
                taskFail(null, null, true);
                break;
            //下载暂停状态
            case 2:
                taskStop(null, null, true);
                break;

            default:
                taskNoDown();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            taskNoDown();
        }
    };

    /**
     * 获得下载链接
     */
    @SuppressLint("HandlerLeak")
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
                        mHandler.sendEmptyMessageDelayed(1, HttpConstant.Time);
                    } else {
                        Utils.TS("游戏链接为空");
                    }
                    suo = true;
                    break;
                case 2:
                    Utils.TS("获取下载链接失败");
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
            llHintMsg.setVisibility(View.GONE);
            downLayout.setVisibility(View.VISIBLE);
            downProgressbar.setProgress(0);
            downHint.setText("--/--");
            downSpend.setText("等待");
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
            mHandler.removeMessages(1);
            Log.e("下载状态", "----暂停");
            percent = 2;
            game_download.setText("继续");
            downLayout.setVisibility(View.VISIBLE);
            llHintMsg.setVisibility(View.GONE);
            downSpend.setText("暂停中");
            if (task != null) {
                downProgressbar.setProgress(task.getPercent());
                String size = Utils.getSize(task.getEntity().getCurrentProgress(), task.getFileSize());
                downHint.setText(size);
            } else {
                downHint.setText(gameInfo.size);
            }
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
            mHandler.removeMessages(1);
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
            mHandler.removeMessages(1);
            percent = 0;
            game_download.setText("重试");
            downLayout.setVisibility(View.GONE);
            llHintMsg.setVisibility(View.VISIBLE);
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
            mHandler.removeMessages(1);
            DownDataBean downDataBean = DownManager.getInstance().RealState(gameInfo.id);
            if (downDataBean != null) {
                Status(downDataBean.btnStatus);
            }
        }
    }

    /**
     * 下载中状态
     */
    public void taskRuning(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            Log.e("下载状态", "----下载中");
            mHandler.removeMessages(1);
            percent = 4;
            downProgressbar.setProgress(task.getPercent());
            game_download.setText("暂停");
            downLayout.setVisibility(View.VISIBLE);
            llHintMsg.setVisibility(View.GONE);
            if (task.getFileSize() == 0) {
                String formatSize = CleanMessageUtil.getFormatSize(task.getEntity().getCurrentProgress());
                downHint.setText(formatSize + "/0M");
            } else {
                String size = Utils.getSize(task.getEntity().getCurrentProgress(), task.getFileSize());
                downSpend.setText(task.getConvertSpeed());
                downHint.setText(size);
            }
        }
    }

    /**
     * 未下载状态
     */
    public void taskNoDown() {
        Log.e("下载状态", "----未下载");
        percent = -2;
        game_download.setText("下载");
        downLayout.setVisibility(View.GONE);
        llHintMsg.setVisibility(View.VISIBLE);
    }
}
