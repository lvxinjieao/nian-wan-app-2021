package com.nian.wan.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.UserMessageBean;
import com.nian.wan.app.utils.TimeUtils;
import com.nian.wan.app.view.ShapeImageView;
import com.bumptech.glide.Glide;
import android.widget.RelativeLayout;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/11
 * @Description: 用户消息RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class UserMessageRecyclerViewAdapter extends RecyclerView.Adapter<UserMessageRecyclerViewAdapter.ViewHolder> {


    private List<UserMessageBean> mUserMessageBeanList = new ArrayList<>();
    private WeakReference<Context> mWeakReference;


    public UserMessageRecyclerViewAdapter(Context context) {
        mWeakReference = new WeakReference(context);
    }


    public void setData(List<UserMessageBean> homeHotBeanList) {
        this.mUserMessageBeanList = homeHotBeanList;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(x.app()).load(mUserMessageBeanList.get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgHomeGiftDetailGamePic);
        switch (mUserMessageBeanList.get(position).getType()) {
            //后台
            case "1":
                break;
            //实名认证
            case "2":
                holder.tvUserMessageName.setText("实名认证");
                break;
            //预约提醒
            case "3":
                holder.tvUserMessageName.setText("预约通知");
                break;
            //开服提醒
            case "4":
                holder.tvUserMessageName.setText("开服通知");
                break;
        }
        holder.tvUserMessageDate.setText(TimeUtils.timesFormat(mUserMessageBeanList.get(position).getCreate_time()));
        holder.tvUserMessageDescription.setText(mUserMessageBeanList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (mUserMessageBeanList.size() == 0) {
            return 0;
        } else {
            return mUserMessageBeanList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_user_message_content)
        RelativeLayout rlUserMessageContent;
        @BindView(R.id.img_home_gift_detail_game_pic)
        ShapeImageView imgHomeGiftDetailGamePic;
        @BindView(R.id.tv_user_message_name)
        TextView tvUserMessageName;
        @BindView(R.id.tv_user_message_date)
        TextView tvUserMessageDate;
        @BindView(R.id.tv_user_message_description)
        TextView tvUserMessageDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
