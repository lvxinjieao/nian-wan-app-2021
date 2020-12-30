package com.nian.wan.app.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.XinWenDetActivity;
import com.nian.wan.app.bean.HomePublishBean;
import com.nian.wan.app.utils.Utils;
import android.widget.LinearLayout;;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 首页活动-公告RecyclerView适配器
 */
public class PublishRecyclerViewAdapter extends RecyclerView.Adapter<PublishRecyclerViewAdapter.ViewHolder> {

    private List<HomePublishBean> mGamePublishBeanList = new ArrayList<>();
    private WeakReference<Context> weakReference;


    public PublishRecyclerViewAdapter(Context context) {
        this.weakReference = new WeakReference(context);
    }

    public void setData(List<HomePublishBean> mGamePublishBeanList) {
        this.mGamePublishBeanList = mGamePublishBeanList;
        this.notifyDataSetChanged();
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_activities_publish, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //公告标题
        if (!TextUtils.isEmpty(mGamePublishBeanList.get(position).getBelong_game())) {
            holder.tvPublishTitle.setText("【" + Utils.getJieQu(mGamePublishBeanList.get(position).getBelong_game()) + "】" + Utils.getJieQu(mGamePublishBeanList.get(position).getTitle()) + Utils.getJieQu(mGamePublishBeanList.get(position).getDescription()));
        } else {
            holder.tvPublishTitle.setText(Utils.getJieQu(mGamePublishBeanList.get(position).getTitle())
                    + Utils.getJieQu(mGamePublishBeanList.get(position).getDescription()));
        }
        holder.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(weakReference.get(), XinWenDetActivity.class);
                intent.putExtra("type_id", 2);
                intent.putExtra("id", mGamePublishBeanList.get(position).getId());
                intent.putExtra("topTitle", mGamePublishBeanList.get(position).getTitle());
                intent.putExtra("URL", mGamePublishBeanList.get(position).getUrl());
                weakReference.get().startActivity(intent);
            }
        });

        //公告发布时间
        holder.tvPublishDate.setText(mGamePublishBeanList.get(position).getStart_time());
    }

    @Override
    public int getItemCount() {
        if (0 == mGamePublishBeanList.size()) {
            return 0;
        } else {
            return mGamePublishBeanList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPublishTitle;
        TextView tvIsReadPublish;
        TextView tvPublishDate;
        LinearLayout llLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPublishTitle = (TextView) itemView.findViewById(R.id.tv_publish_title);
            tvPublishDate = (TextView) itemView.findViewById(R.id.tv_publish_date);
            llLayout = (LinearLayout) itemView.findViewById(R.id.al_layout);
        }
    }

}
