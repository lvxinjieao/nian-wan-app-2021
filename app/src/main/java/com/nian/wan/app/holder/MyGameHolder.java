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
import android.widget.Button;
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
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.EvenBusBean;
import com.nian.wan.app.bean.MyDownDateBean;
import com.nian.wan.app.download.DownManager;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.CleanMessageUtil;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.LoadDialog;
import com.nian.wan.app.view.ShapeImageView;

import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

;

/**
 * 描述：我的游戏条目
 * 作者：钮家齐
 * 时间: 2018-08-10 17:26
 */

public class MyGameHolder extends RecyclerView.ViewHolder {
    private final Activity activity;
    @BindView(R.id.tv_start)
    public TextView tvStart;
    @BindView(R.id.tv_fanli)
    public TextView tvFanli;
    @BindView(R.id.img_icon)
    public ShapeImageView imgIcon;
    @BindView(R.id.tv_game_name)
    public TextView tvGameName;
    @BindView(R.id.rl_game_description)
    public RelativeLayout rlGameDescription;
    @BindView(R.id.tv_package)
    public TextView tvPackage;
    @BindView(R.id.down_progressbar)
    public RoundCornerProgressBar downProgressbar;
    @BindView(R.id.down_spend)
    public TextView downSpend;
    @BindView(R.id.down_hint)
    public TextView downHint;
    @BindView(R.id.down_layout)
    public RelativeLayout downLayout;
    @BindView(R.id.tv_hint)
    public TextView tvHint;
    @BindView(R.id.img_line)
    public ImageView imgLine;
    @BindView(R.id.tv_msg)
    public TextView tvMsg;
    @BindView(R.id.rl_content)
    public RelativeLayout rlContent;
    @BindView(R.id.ll_hint_msg)
    public LinearLayout llHintMsg;
    @BindView(R.id.btnDelete)
    public Button btnDelete;

    private boolean isH5Game;
    private DbManager db;
    private MyDownDateBean myDatabean;
    private int percent = -2;               //默认数据库没有下载记录   下载状态为-2
    private DownDataBean down;
    private LoadDialog loadDialog;


    public MyGameHolder(View itemView, Activity act, boolean isH5Game) {
        super(itemView);
        this.isH5Game = isH5Game;
        activity = act;
        ButterKnife.bind(this, itemView);
        db = DatabaseOpenHelper.getInstance();
    }

    public void setData(final MyDownDateBean databean){
        this.myDatabean = databean;
        //这一步是判断  数据库有没有这个游戏的下载记录 (根据当前游戏ID查询)
        down = DownManager.getInstance().getDownBean(databean.id);
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
        //给各个控件赋值 展示数据
        setdata(databean);
        if (!isH5Game){
            //如果不是H5游戏   刷新下载按钮状态
            Status(percent);
        }
    }


    public void setdata(final MyDownDateBean bean) {
        String jieQu = Utils.getJieQu(bean.name);
        if (jieQu.length()>=12){
            tvGameName.setText(jieQu.substring(0,12) + "...");
        }else {
            tvGameName.setText(jieQu);
        }
        Glide.with(x.app()).load(bean.iconurl).error(R.drawable.default_picture).into(imgIcon);
        tvMsg.setText(bean.features);
        tvStart.setText("下载");
        if (bean.canDownload == 0){
            tvStart.setBackgroundResource(R.drawable.bian_kuang_gray);
            tvStart.setTextColor(activity.getResources().getColorStateList(R.color.qiangray));
            tvStart.setEnabled(false);
        }else {
            tvStart.setBackgroundResource(R.drawable.bian_kuang_green);
            tvStart.setEnabled(true);
        }

        if (!TextUtils.isEmpty(bean.size)){
            tvHint.setText(bean.size);
        }else {
            tvHint.setText("0M");
        }
        if (bean.fanli == null || bean.fanli.equals("0.00")) {
            tvFanli.setVisibility(View.INVISIBLE);
        } else {
            tvFanli.setVisibility(View.VISIBLE);
            tvFanli.setText("返利" + bean.fanli.replace(".00","") + "%");
        }

        if (bean.gift == 0){
            tvPackage.setVisibility(View.GONE);
        }else {
            tvPackage.setVisibility(View.VISIBLE);
        }


        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下载
                xiazai();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除
                deleteItem(bean.record_id);
            }
        });

        rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, GameDetailActivity.class);
                intent.putExtra("game_id",bean.id);
                activity.startActivity(intent);
            }
        });
    }

    /**
     * 下载按钮的执行动作
     */
    public void xiazai() {
        down = DownManager.getInstance().getDownBean(myDatabean.id);
        if (down!=null){
            switch (percent) {
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
        }else {
            Intent intent = new Intent(activity, GameDetailActivity.class);
            intent.putExtra("game_id",myDatabean.id);
            activity.startActivity(intent);
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
                llHintMsg.setVisibility(View.VISIBLE);
                break;
            //已安装打开状态
            case 8:
                Log.e("下载状态","----已安装打开状态");
                percent = 8;
                tvStart.setText("打开");
                downLayout.setVisibility(View.GONE);
                llHintMsg.setVisibility(View.VISIBLE);
                break;
            //下载失败状态
            case 0:
                taskFail(null,null,true);
                break;
            //下载暂停状态
            case 2:
                taskStop(null,null,true);
                break;
        }
    }

    /**
     * 确认下载状态      例如 在安装界面  未点击安装按钮而是点击的返回按钮
     */
    public void  ConfirmationState(){
        if(percent==1||percent==8){
            taskComplete(null,null,true);
        }
    }

    /**
     * 等待
     */
    public void taskWait(DownloadTask task, String key, boolean execute) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(myDatabean.id);
        }
        if(down!=null&&(execute||key.equals(down.DownUrl))){
            Log.e("下载状态","----等待");
            percent = 3;
            tvStart.setText("等待");
            tvMsg.setVisibility(View.GONE);
            downLayout.setVisibility(View.VISIBLE);
            downProgressbar.setProgress(0);
            downHint.setText( "--/--" );
            downSpend.setText("等待");
        }
    }

    /**
     * 下载暂停状态
     */
    public void taskStop(DownloadTask task, String key, boolean execute) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(myDatabean.id);
        }
        //判断下载数据是否为空   如果为空 或者 key和下载地址不对应  说明更新的不应该是这个条目
        //应该更新 key 和下载地址相同的条目
        if(down!=null&&(execute||key.equals(down.DownUrl))){
            Log.e("下载状态","----暂停");
            percent = 2;
            tvStart.setText("继续");
            downLayout.setVisibility(View.GONE);
            llHintMsg.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 下载取消状态
     */
    public void taskCancel(DownloadTask task, String key, boolean execute) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(myDatabean.id);
        }
        if(down!=null&&(execute||key.equals(down.DownUrl))){
            Log.e("下载状态","----删除下载");
            taskNoDown();
        }
    }

    /**
     * 下载失败状态
     */
    public void taskFail(DownloadTask task, String key, boolean execute) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(myDatabean.id);
        }
        if(down!=null&&(execute||key.equals(down.DownUrl))){
            Log.e("下载状态","----下载失败");
            percent = 0;
            tvStart.setText("重试");
            downLayout.setVisibility(View.GONE);
            llHintMsg.setVisibility(View.VISIBLE);
            DownManager.getInstance().setState(myDatabean.id);
            ToastUtil.showToast("下载链接有误");
        }
    }

    /**
     * 下载完成未安装  及  安装完成打开状态
     */
    public void taskComplete(DownloadTask task, String key, boolean execute) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(myDatabean.id);
        }
        if(down!=null&&(execute||key.equals(down.DownUrl))){
            DownDataBean downDataBean = DownManager.getInstance().RealState(myDatabean.id);
            if(downDataBean!=null){
                if(downDataBean.btnStatus==-2){
                    EvenBusBean evenBusBean = new EvenBusBean();
                    evenBusBean.type = 10;
                    EventBus.getDefault().post(evenBusBean);
                    return;
                }
                Status(downDataBean.btnStatus);
            }
        }
    }

    /**
     * 下载中状态
     */
    public void taskRuning(DownloadTask task, String key, boolean execute) {
        if(down==null){
            down = DownManager.getInstance().getDownBean(myDatabean.id);
        }
        if(down!=null&&(execute||key.equals(down.DownUrl))){
            Log.e("下载状态","----下载中");
            percent = 4;
            downProgressbar.setProgress(task.getPercent());
            tvStart.setText("暂停");
            downLayout.setVisibility(View.VISIBLE);
            llHintMsg.setVisibility(View.GONE);
            if(task.getFileSize()==0){
                String formatSize = CleanMessageUtil.getFormatSize(task.getEntity().getCurrentProgress());
                downHint.setText( formatSize+ "/0M");
            }else{
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
        Log.e("下载状态","----未下载");
        percent = -2;
        tvStart.setText("下载");
        downLayout.setVisibility(View.GONE);
        llHintMsg.setVisibility(View.VISIBLE);
    }




    /**
     * 通知后端删除条目
     */
    private void deleteItem(String id){
        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("token",Utils.getLoginUser().token);
        map.put("record_id",id);
        Log.e("record_id-------",id);
        HttpConstant.POST(handler, HttpConstant.DownDel,map,false);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    try{
                        Log.e("删除记录返回数据",msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        if (code == 200){
                            if (down!=null){
                                //删除下载数据记录
                                Aria.download(this).load(down.DownUrl).cancel();
                                //删除数据库记录
                                db.deleteById(DownDataBean.class,down.id);
                                //删除本地下载文件    如果有则删除
                                DownManager.getInstance().Delete(down.id);
                            }

                            //删除完毕 通知页面刷新
                            EvenBusBean evenBusBean = new EvenBusBean();
                            evenBusBean.type = 10;
                            EventBus.getDefault().post(evenBusBean);
                            disDialog();
                        }else {
                            disDialog();
                            if (down!=null){
                                //删除下载数据记录
                                Aria.download(this).load(down.DownUrl).cancel();
                                //删除数据库记录
                                db.deleteById(DownDataBean.class,down.id);
                                //删除本地下载文件    如果有则删除
                                DownManager.getInstance().Delete(down.id);
                                //删除完毕 通知页面刷新
                                EvenBusBean evenBusBean = new EvenBusBean();
                                evenBusBean.type = 10;
                                EventBus.getDefault().post(evenBusBean);
                            }
                        }

                    }catch (Exception e){
                        disDialog();
                       // ToastUtil.showToast("删除失败，数据异常");
                        Log.e("删除记录异常",e.toString());
                    }
                    break;
                case 2:
                    disDialog();
                    //ToastUtil.showToast("删除失败，网络异常");
                    break;
            }
        }
    };



    private void showDialog(){
        if (loadDialog == null){
            loadDialog = new LoadDialog(activity, R.style.MyDialogStyle);
            loadDialog.show();
        }
    }

    private void disDialog(){
        if (loadDialog != null && loadDialog.isShowing()){
            loadDialog.dismiss();
        }
    }
}
