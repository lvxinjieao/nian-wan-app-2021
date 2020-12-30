package com.nian.wan.app.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.bean.ForeShowServerBean;
import com.nian.wan.app.bean.SetOpenServerPushBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.LoadDialog;
import com.nian.wan.app.view.ShapeImageView;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import android.widget.LinearLayout;;
import android.widget.RelativeLayout;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description: 预告开服RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class ForeShowServerRecyclerViewAdapter extends
        RecyclerView.Adapter<ForeShowServerRecyclerViewAdapter.ViewHolder> {
    private List<ForeShowServerBean> mForeShowServerBeen = new ArrayList<>();
    private WeakReference<Context> mWeakReference;
    private SetOpenServerPushBean setOpenServerPushBean;
    private LoadDialog loadDialog;


    public ForeShowServerRecyclerViewAdapter(Context context) {
        mWeakReference = new WeakReference(context);

    }

    public void setData(List<ForeShowServerBean> mForeShowServerBeen) {
        this.mForeShowServerBeen = mForeShowServerBeen;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foreshow_server, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(x.app()).load(mForeShowServerBeen.get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgForeshowServerPic);
        String start_time = mForeShowServerBeen.get(position).getStart_time();
        long l = Long.parseLong(start_time);
        Log.e("开服条目", "当前时间：" + System.currentTimeMillis() / 1000 + "---开服时间：" + l);
        long l1 = System.currentTimeMillis() / 1000;
        if (l - l1 < 1800) {
            holder.tvJijiang.setVisibility(View.VISIBLE);
            holder.llForeshowUnpush.setVisibility(View.GONE);
        }
        holder.rlForeShowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != Utils.getPersistentUserInfo()) {
                    showDialog();
                    if (holder.tvForeshowServerUnpush.getText().equals("已设置通知")){
                        setOpenServerPush(mForeShowServerBeen.get(position).getServer_id(),"2",holder);
                    }else {
                        setOpenServerPush(mForeShowServerBeen.get(position).getServer_id(),"1",holder);
                    }
                } else {
                    new DialogGoLogin((Activity) mWeakReference.get(),
                            R.style.MyDialogStyle, "暂时无法设置游戏通知哦~T_T~").show();
                }
            }
        });
        holder.tvForeshowGameName.setText(Utils.truncationCharacter(14, "...",
                Utils.getJieQu(mForeShowServerBeen.get(position).getGame_name())));
        holder.tvForeshowServerName.setText(Utils.truncationCharacter(5, "...",
                mForeShowServerBeen.get(position).getServer_name()));
        holder.tvForeshowServerDate.setText(mForeShowServerBeen.get(position).getStart_date());
        if (1 == mForeShowServerBeen.get(position).getIsnotice()) {
            setPushStyle(holder, true);
        } else {
            setPushStyle(holder, false);
        }
        //跳转游戏详情
        holder.rlForeshowDataContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                intent.putExtra("game_id", Integer.parseInt(mForeShowServerBeen.get(position)
                        .getGame_id()));
                mWeakReference.get().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (0 == mForeShowServerBeen.size()) {
            return 0;
        } else {
            return mForeShowServerBeen.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_foreshow_server_pic)
        ShapeImageView imgForeshowServerPic;
        @BindView(R.id.img_foreshow_server_unpush)
        ImageView imgForeshowServerUnpush;
        @BindView(R.id.tv_foreshow_server_unpush)
        TextView tvForeshowServerUnpush;
        @BindView(R.id.ll_foreshow_unpush)
        LinearLayout llForeshowUnpush;
        @BindView(R.id.tv_foreshow_game_name)
        TextView tvForeshowGameName;
        @BindView(R.id.tv_foreshow_server_name)
        TextView tvForeshowServerName;
        @BindView(R.id.tv_foreshow_server_date)
        TextView tvForeshowServerDate;
        @BindView(R.id.rl_foreshow_data_content)
        RelativeLayout rlForeshowDataContent;
        @BindView(R.id.tv_jijiang)
        TextView tvJijiang;
        @BindView(R.id.rl_foreshow_layout)
        RelativeLayout rlForeShowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 设置推送按钮样式
     *
     * @param holder
     * @param isEnabled 是否设置通知
     */
    private void setPushStyle(ViewHolder holder, boolean isEnabled) {
        if (isEnabled) {
            holder.tvForeshowServerUnpush.setText("已设置通知");
            holder.tvForeshowServerUnpush
                    .setTextColor(mWeakReference.get().getResources().getColor(R.color.green));
            holder.imgForeshowServerUnpush.setImageDrawable(ContextCompat
                    .getDrawable(mWeakReference.get(), R.drawable.service_trailer_notified));
        } else {
            holder.tvForeshowServerUnpush.setText("设置通知");
            holder.tvForeshowServerUnpush
                    .setTextColor(mWeakReference.get().getResources()
                            .getColor(R.color.font_gray_1));
            holder.imgForeshowServerUnpush.setImageDrawable(ContextCompat
                    .getDrawable(mWeakReference.get(), R.drawable.service_trailer_notice));
        }
    }


    /**
     * 设置开服通知
     *
     * @param serverId 区服id
     * @param type     1 设置通知 2 取消通知
     */
    private void setOpenServerPush(String serverId, String type, ViewHolder holder) {
        if (null != Utils.getPersistentUserInfo()) {
            Map<String, String> openServerPushParams = new HashMap<>();
            openServerPushParams.put("server_id", serverId);
            openServerPushParams.put("type", type);
            openServerPushParams.put("sdk_version", "1");
            setPushStyle(holder, type.equals("2") ? false : true);
            Type t = new TypeToken<HttpResult<Object>>() {
            }.getType();
            new HttpUtils<Object>(t, HttpConstant.API_HOME_ALREADY_OPEN_SERVER_PUSH, openServerPushParams, "设置开服通知", true) {
                @Override
                protected void _onSuccess(Object s, String msg) {
                    disDialog();
                }

                @Override
                protected void _onError(String message, int code) {
                    ToastUtil.showToast(message);
                    disDialog();
                }

                @Override
                protected void _onError() {
                    disDialog();
                }
            };


        } else {

            DialogLoginActivity loginActivity = new DialogLoginActivity(mWeakReference.get(),
                    "LOGIN");
            Activity activity = (Activity) mWeakReference.get();
            FragmentTransaction transaction = activity
                    .getFragmentManager()
                    .beginTransaction();
            transaction.add(loginActivity, "WoDe");
            transaction.show(loginActivity);
            transaction.commitAllowingStateLoss();
        }

    }


    private void showDialog(){
        if (loadDialog == null){
            loadDialog = new LoadDialog(mWeakReference.get(),R.style.MyDialogStyle);
        }
        loadDialog.show();
    }

    private void disDialog(){
        if (loadDialog != null && loadDialog.isShowing()){
            loadDialog.dismiss();
        }
    }

}
