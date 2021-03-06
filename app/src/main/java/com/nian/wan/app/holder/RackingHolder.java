package com.nian.wan.app.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
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
 * 描述：排行榜条目
 * 作者：闫冰
 * 时间: 2018-07-23 17:13
 */
public class RackingHolder extends BaseHolder<GameInfo> {

    @BindView(R.id.tv_racking)
    TextView tvRacking;

    @BindView(R.id.img_icon)
    ShapeImageView imgIcon;

    @BindView(R.id.btn_start)
    TextView tvStart;

    @BindView(R.id.tv_title)
    TextView tvGameName;

    @BindView(R.id.tv_hint)
    TextView tvHint;

    @BindView(R.id.tv_type)
    TextView tvType;

    @BindView(R.id.down_progressbar)
    RoundCornerProgressBar downProgressbar;

    @BindView(R.id.down_spend)
    TextView downSpend;

    @BindView(R.id.down_hint)
    TextView downHint;

    @BindView(R.id.down_layout)
    RelativeLayout downLayout;

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private Activity activity;
    private GameInfo gameInfo;
    private boolean isH5Game;
    private DownDataBean down;
    private int percent = -2;
    private boolean suo = true;

    @Override
    protected void refreshView(GameInfo gameInfo, int position, Activity activity) {
        this.activity = activity;
        this.gameInfo = gameInfo;
        //这一步是判断  数据库有没有这个游戏的下载记录 (根据当前游戏ID查询)

        down = DownManager.getInstance().getDownBean(gameInfo.id);
        if(down !=null){
            //如果有记录  判断当前下载状态
            percent = Aria.download(this).load(down.DownUrl).getTaskState();
            //percent的下载状态里没有  安装完成打开状态  需要自行判断
            if(down.packageName!=null&&Utils.isInstall(x.app(), down.packageName)){
                //根据包名判断如果该游戏已经安装过    下载状态改为已安装 =8
                percent = 8;
            }

            if (percent == 3){
                percent = -2;
            }
        }
        tvRacking.setText(position + 4 +"");
        init();
        if (!isH5Game){
            //如果不是H5游戏   刷新下载按钮状态
            Status(percent);
        }
    }

    public void setH5Game(boolean h5Game) {
        isH5Game = h5Game;
    }



    private void init(){
        String jieQu = Utils.getJieQu(gameInfo.name);
        if (jieQu.length()>=12){
            tvGameName.setText(jieQu.substring(0,12) + "...");
        }else {
            tvGameName.setText(jieQu);
        }
        Glide.with(x.app()).load(gameInfo.iconurl).error(R.drawable.default_picture).into(imgIcon);
        tvType.setText("类型："+gameInfo.type);

        if (isH5Game){
            tvStart.setText("开始游戏");
            tvHint.setText("人气："+gameInfo.playNum+"人在玩");
        }else {
            tvStart.setText("下载");
            if (gameInfo.canDownload == 0){
                tvStart.setBackgroundResource(R.drawable.bian_kuang_gray);
                tvStart.setTextColor(activity.getResources().getColorStateList(R.color.qiangray));
                tvStart.setEnabled(false);
            }else {
                tvStart.setBackgroundResource(R.drawable.rd_num2);
                tvStart.setTextColor(activity.getResources().getColorStateList(R.color.theme_red));
                tvStart.setEnabled(true);
            }

            if (!TextUtils.isEmpty(gameInfo.size)){
                tvHint.setText("大小："+gameInfo.size);
            }else {
                tvHint.setText("大小：0M");
            }
        }

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.getLoginUser() != null) {
                    if (!isH5Game){
                        xiazai();
                    }else{
                        StartGame(gameInfo);
                    }
                } else {
                    new DialogGoLogin(activity, R.style.MyDialogStyle, "登录后可开始游戏~").show();
                }

            }
        });

        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isH5Game){
                    Intent intent = new Intent(activity, GameDetailH5Activity.class);
                    intent.putExtra("game_id",gameInfo.id);
                    activity.startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, GameDetailActivity.class);
                    intent.putExtra("game_id",gameInfo.id);
                    activity.startActivity(intent);
                }
            }
        });
    }



    //开始游戏网络请求
    private void StartGame(GameInfo gameInfo) {

    }


    @Override
    protected View initView() {
        View view = LinearLayout.inflate(x.app(), R.layout.gameholder_racking_item, null);
        ButterKnife.bind(this, view);
        view.setTag(this);
        return view;
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
                    taskWait(null,null,true);
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
                taskWait(null,null,true);
                break;
            //下载完成未安装状态
            case 1:
                Log.e("下载状态","----下载完成未安装状态");
                percent = 1;
                tvStart.setText("安装");
                downLayout.setVisibility(View.GONE);
                tvType.setVisibility(View.VISIBLE);
                break;
            //已安装打开状态
            case 8:
                Log.e("下载状态","----已安装打开状态");
                percent = 8;
                tvStart.setText("打开");
                downLayout.setVisibility(View.GONE);
                tvType.setVisibility(View.VISIBLE);
                break;
            //下载失败状态
            case 0:
                taskFail(null,null,true);
                break;
            //下载暂停状态
            case 2:
                taskStop(null,null,true);
                break;
                default:
                    taskNoDown();
                    break;
        }
    }

    public void taskWait(DownloadTask task, String key, boolean b) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if(down!=null&&(b||key.equals(down.DownUrl))){
            Log.e("下载状态","----等待");
            percent = 3;
            tvStart.setText("等待");
            downLayout.setVisibility(View.VISIBLE);
            tvType.setVisibility(View.GONE);
            downProgressbar.setProgress(0);
            downHint.setText( "--/--" );
            downSpend.setText("等待");
        }
    }

    public void taskStop(DownloadTask task, String key, boolean b) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        //判断下载数据是否为空   如果为空 或者 key和下载地址不对应  说明更新的不应该是这个条目
        //应该更新 key 和下载地址相同的条目
        if(down!=null&&(b||key.equals(down.DownUrl))){
            Log.e("下载状态","----暂停");
            percent = 2;
            tvStart.setText("继续");
            downLayout.setVisibility(View.VISIBLE);
            tvType.setVisibility(View.GONE);
            downSpend.setText("暂停中");
            downProgressbar.setProgress(task.getPercent());
            String size = Utils.getSize(task.getEntity().getCurrentProgress(), task.getFileSize());
            downHint.setText(size);
        }
    }

    public void taskRuning(DownloadTask task, String key, boolean b) {
        if (down == null) {
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if (down != null && (b || key.equals(down.DownUrl))) {
            Log.e("下载状态", "----下载中");
            percent = 4;
            downProgressbar.setProgress(task.getPercent());
            tvStart.setText("暂停");
            downLayout.setVisibility(View.VISIBLE);
            tvType.setVisibility(View.GONE);
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

    public void taskCancel(DownloadTask task, String key, boolean b) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if(down!=null&&(b||key.equals(down.DownUrl))){
            Log.e("下载状态","----删除下载");
            taskNoDown();
        }
    }

    public void taskFail(DownloadTask task, String key, boolean b) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if(down!=null&&(b||key.equals(down.DownUrl))){
            Log.e("下载状态","----下载失败");
            ToastUtil.showToast("下载链接有误");
            percent = 0;
            tvStart.setText("重试");
            downLayout.setVisibility(View.GONE);
            tvType.setVisibility(View.VISIBLE);
            DownManager.getInstance().setState(gameInfo.id);
            try {
                Aria.download(this).load(down.DownUrl).cancel();//删除下载数据记录
                DatabaseOpenHelper.getInstance().deleteById(DownDataBean.class,down.id);//删除数据库记录
                DownManager.getInstance().Delete(down.id);//删除本地下载文件    如果有则删除
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    public void taskComplete(DownloadTask task, String key, boolean b) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(gameInfo.id);
        }
        if(down!=null&&(b||key.equals(down.DownUrl))){
            down.btnStatus = 0;
            DownDataBean downDataBean = DownManager.getInstance().RealState(gameInfo.id);
            if(downDataBean!=null){
                Status(downDataBean.btnStatus);
            }
        }
    }

    /**
     * 未下载状态
     */
    public void taskNoDown() {
        Log.e("下载状态","----未下载");
        percent = -2;
        tvStart.setText("下载");
        downLayout.setVisibility(View.GONE);
        tvType.setVisibility(View.VISIBLE);
    }

    public void ConfirmationState() {
        if(percent==1||percent==8){
            taskComplete(null,null,true);
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
                    }else{
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
}
