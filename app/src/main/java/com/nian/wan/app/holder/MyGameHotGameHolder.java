package com.nian.wan.app.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
 * 我的游戏-推荐游戏
 * Created by LeBron on 2017/2/7.
 */

public class MyGameHotGameHolder extends BaseHolder<GameInfo> {
    @BindView(R.id.img_icon)
    ShapeImageView imgIcon;
    @BindView(R.id.tv_game_name)
    TextView tvGameName;
    @BindView(R.id.rl_game_description)
    RelativeLayout rlGameDescription;
    @BindView(R.id.tv_package)
    TextView tvPackage;
    @BindView(R.id.down_progressbar)
    RoundCornerProgressBar progressbar;
    @BindView(R.id.down_spend)
    TextView spend;
    @BindView(R.id.down_hint)
    TextView downHint;
    @BindView(R.id.down_layout)
    RelativeLayout downLayout;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.img_line)
    ImageView imgHomeHotDescLine;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.btn_start)
    TextView btnStart;
    @BindView(R.id.tv_fanli)
    TextView tvFanLi;
    @BindView(R.id.ll_hint_Msg)
    LinearLayout llHintMsg;
    @BindView(R.id.img_tag)
    ImageView imgTag;

    private Activity activity;
    private boolean isH5Game;  //是否是H5游戏
    private DownDataBean down;
    private int percent = -2;
    private GameInfo mGameInfo;
    private boolean suo = true;


    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.holder_mygame_hotgame_item, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }

    public void setIsH5Game(boolean isH5Game) {
        this.isH5Game = isH5Game;
    }


    @Override
    protected void refreshView(final GameInfo gameInfo, int position, final Activity activity) {
        String s = activity.getClass().toString();
        this.activity = activity;
        this.mGameInfo = gameInfo;
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
        if (!isH5Game) {
            //如果不是H5游戏   刷新下载按钮状态
            Status(percent);
        }
    }


    public void init(GameInfo gameInfo) {
        String jieQu = Utils.getJieQu(gameInfo.name);
        if (jieQu.length() >= 12) {
            tvGameName.setText(jieQu.substring(0, 12) + "...");
        } else {
            tvGameName.setText(jieQu);
        }
        Glide.with(x.app()).load(gameInfo.iconurl).error(R.drawable.default_picture).into(imgIcon);
        tvMsg.setText(gameInfo.features);
        if (isH5Game) {
            tvNum.setText(gameInfo.playNum);
            btnStart.setText("开始");
            tvType.setText(gameInfo.type);
            tvHint.setText("人在玩");
            imgTag.setBackgroundResource(R.mipmap.tag_h5);
        } else {
            tvNum.setText(gameInfo.playNum);
            btnStart.setText("下载");
            imgTag.setBackgroundResource(R.mipmap.tag_shouyou);
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
        }

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
                    xiazai(); //下载
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
        down = DownManager.getInstance().getDownBean(mGameInfo.id);
        switch (percent) {
            //未下载
            case -2:
                if (suo) {
                    suo = false;
                    Utils.getDownLoadUrl(handler, mGameInfo.id);
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
                btnStart.setText("安装");
                downLayout.setVisibility(View.GONE);
                llHintMsg.setVisibility(View.VISIBLE);
                break;
            //已安装打开状态
            case 8:
                Log.e("下载状态", "----已安装打开状态");
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
                        downDataBean.id = mGameInfo.id;
                        downDataBean.DownUrl = dataBean.url;
                        DownManager.getInstance().down(downDataBean);

                        mGameInfo.record_id = dataBean.record_id;
                        DownManager.getInstance().copy(mGameInfo);
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
            down = DownManager.getInstance().getDownBean(mGameInfo.id);
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
            down = DownManager.getInstance().getDownBean(mGameInfo.id);
        }
        //判断下载数据是否为空   如果为空 或者 key和下载地址不对应  说明更新的不应该是这个条目
        //应该更新 key 和下载地址相同的条目
        if (down != null && (execute || key.equals(down.DownUrl))) {
            Log.e("下载状态", "----暂停");
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
                downHint.setText(mGameInfo.size);
            }
        }
    }

    /**
     * 下载取消状态
     */
    public void taskCancel(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(mGameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            Log.e("下载状态", "----删除下载");
            taskNoDown();
        }
    }

    /**
     * 下载失败状态
     */
    public void taskFail(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(mGameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            Log.e("下载状态", "----下载失败");
            percent = 0;
            btnStart.setText("重试");
            downLayout.setVisibility(View.GONE);
            llHintMsg.setVisibility(View.VISIBLE);
            DownManager.getInstance().setState(mGameInfo.id);
            try {
                Aria.download(this).load(down.DownUrl).cancel();//删除下载数据记录
                DatabaseOpenHelper.getInstance().deleteById(DownDataBean.class, down.id);//删除数据库记录
                DownManager.getInstance().Delete(down.id);//删除本地下载文件    如果有则删除
            } catch (DbException e) {
                e.printStackTrace();
            }
            ToastUtil.showToast("下载链接有误");
        }
    }

    /**
     * 下载完成未安装  及  安装完成打开状态
     */
    public void taskComplete(DownloadTask task, String key, boolean execute) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(mGameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            DownDataBean downDataBean = DownManager.getInstance().RealState(mGameInfo.id);
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
            down = DownManager.getInstance().getDownBean(mGameInfo.id);
        }
        if (down != null && (execute || key.equals(down.DownUrl))) {
            Log.e("下载状态", "----下载中");
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
        Log.e("下载状态", "----未下载");
        percent = -2;
        btnStart.setText("下载");
        downLayout.setVisibility(View.GONE);
        llHintMsg.setVisibility(View.VISIBLE);
    }
}
