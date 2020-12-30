package com.nian.wan.app.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.DataBean;
import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.download.DownManager;
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
 * 首页游戏item
 * Created by LeBron on 2017/2/7.
 */
public class HomeGameHolder extends BaseHolder<GameInfo> {

    @BindView(R.id.img_icon)
    ShapeImageView imgIcon;

    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    @BindView(R.id.tv_game_name)
    TextView tvGameName;

    @BindView(R.id.tv_package)
    TextView tvPackage;

    @BindView(R.id.tv_type)//大小
            TextView tvType;

    @BindView(R.id.tv_num)//下载
            TextView tvNum;

    @BindView(R.id.tv_msg)//一句话
            TextView tvMsg;

    @BindView(R.id.rl_game_description)
    RelativeLayout rlGameDescription;


    @BindView(R.id.down_layout)
    RelativeLayout downLayout;

    @BindView(R.id.down_progressbar)
    RoundCornerProgressBar progressbar;

    @BindView(R.id.down_spend)
    TextView spend;

    @BindView(R.id.down_hint)
    TextView downHint;


    @BindView(R.id.img_line)
    ImageView imgHomeHotDescLine;


    @BindView(R.id.tv_hint)//人下载
            TextView tvHint;


    @BindView(R.id.tv_fanli)
    TextView tvFanLi;

    @BindView(R.id.btn_start)//开始下载
            TextView btnStart;


    @BindView(R.id.ll_hint_Msg)
    LinearLayout llHintMsg;

    private Activity activity;
    //private boolean isH5Game;  //是否是H5游戏
    private DownDataBean down;
    private int percent = -2;
    private GameInfo gameInfo;
    private boolean suo = true;


    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.gameholder_game_item, null);
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

        Status(percent);
    }


    public void init(final GameInfo gameInfo) {
        String jieQu = Utils.getJieQu(gameInfo.name);
        if (jieQu.length() >= 12) {
            tvGameName.setText(jieQu.substring(0, 12) + "...");
        } else {
            tvGameName.setText(jieQu);
        }
        Glide.with(x.app()).load(gameInfo.iconurl).error(R.drawable.default_picture).into(imgIcon);
        tvMsg.setText(gameInfo.features);


        tvNum.setText(gameInfo.playNum);
        btnStart.setText("下载");
        if (gameInfo.canDownload == 0) {
            btnStart.setBackgroundResource(R.drawable.bian_kuang_gray);
            btnStart.setTextColor(activity.getResources().getColorStateList(R.color.qiangray));
            btnStart.setEnabled(false);
        } else {
            btnStart.setBackgroundResource(R.drawable.bian_kuang_green);
            btnStart.setTextColor(activity.getResources().getColorStateList(R.color.zhuse_lan));
            btnStart.setEnabled(true);
        }
        if (!TextUtils.isEmpty(gameInfo.size)) {
            tvType.setText(gameInfo.size);
        } else {
            tvType.setText("0M");
        }
        tvHint.setText("下载");


        if (gameInfo.gift == 0) {
            tvPackage.setVisibility(View.GONE);
        } else {
            tvPackage.setVisibility(View.VISIBLE);
        }

        if (gameInfo.fanli == null || gameInfo.fanli.equals("0.00")) {
            tvFanLi.setVisibility(View.INVISIBLE);
        } else {
            tvFanLi.setVisibility(View.VISIBLE);
            tvFanLi.setText("返利" + gameInfo.fanli.replace(".00", "") + "%");
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.getLoginUser() != null) {
                    xiazai();
                } else {
                    new DialogGoLogin(activity, R.style.MyDialogStyle, "登录后可开始游戏~").show();
                }

            }
        });


    }

    /**
     * 下载按钮的执行动作
     */
    public void xiazai() {
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
                percent = 1;
                btnStart.setText("安装");
                downLayout.setVisibility(View.GONE);
                llHintMsg.setVisibility(View.VISIBLE);
                break;
            //已安装打开状态
            case 8:
                percent = 8;
                btnStart.setText("打开");
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
        }
    }

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
            btnStart.setText("等待");
            tvMsg.setVisibility(View.GONE);
            downLayout.setVisibility(View.VISIBLE);
            progressbar.setProgress(0);
            downHint.setText("--/--");
            spend.setText("等待");
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
            btnStart.setText("继续");
            downLayout.setVisibility(View.VISIBLE);
            llHintMsg.setVisibility(View.GONE);
            spend.setText("暂停中");

            if (task != null) {
                progressbar.setProgress(task.getPercent());
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
            btnStart.setText("重试");
            downLayout.setVisibility(View.GONE);
            llHintMsg.setVisibility(View.VISIBLE);
            DownManager.getInstance().setState(gameInfo.id);
            try {
                Aria.download(this).load(down.DownUrl).cancel();//删除下载数据记录Db
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
            percent = 4;
            progressbar.setProgress(task.getPercent());
            btnStart.setText("暂停");
            downLayout.setVisibility(View.VISIBLE);
            llHintMsg.setVisibility(View.GONE);
            if (task.getFileSize() == 0) {
                String formatSize = CleanMessageUtil.getFormatSize(task.getEntity().getCurrentProgress());
                downHint.setText(formatSize + "/0M");
            } else {
                String size = Utils.getSize(task.getEntity().getCurrentProgress(), task.getFileSize());
                spend.setText(task.getConvertSpeed());
                downHint.setText(size);
            }
        }
    }

    /**
     * 未下载状态
     */
    public void taskNoDown() {
        percent = -2;
        btnStart.setText("下载");
        downLayout.setVisibility(View.GONE);
        llHintMsg.setVisibility(View.VISIBLE);
    }
}
