package com.nian.wan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.XinWenDetActivity;
import com.nian.wan.app.bean.HomeActivitiesBean;
import com.nian.wan.app.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description:
 * @Modify By: 首页活动RecyclerView适配器
 * @ModifyDate:
 */

public class ActivitiesRecyclerViewAdapter extends
        RecyclerView.Adapter<ActivitiesRecyclerViewAdapter.ViewHolder> {

    private List<HomeActivitiesBean> mActivitiesBeanList = new ArrayList<>();
    private WeakReference<Context> weakReference;

    public ActivitiesRecyclerViewAdapter(Context context) {
        this.weakReference = new WeakReference(context);
    }

    public void setData(List<HomeActivitiesBean> mActivitiesBeanList) {
        this.mActivitiesBeanList = mActivitiesBeanList;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_activities_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //活动封面
        try {

            if (!TextUtils.isEmpty(mActivitiesBeanList.get(position).getCover())) {
                Glide.with(x.app()).load(mActivitiesBeanList.get(position).getCover()).error(R.drawable.goods_default)
                        .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.imgGamePic);
            }
            holder.imgGamePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(weakReference.get(), XinWenDetActivity.class);
                    intent.putExtra("type_id", 2);
                    intent.putExtra("id", mActivitiesBeanList.get(position).getId());
                    intent.putExtra("topTitle", mActivitiesBeanList.get(position).getBelong_game());
                    intent.putExtra("URL", mActivitiesBeanList.get(position).getUrl());
                    weakReference.get().startActivity(intent);
                }
            });
            //活动标题
            if (!TextUtils.isEmpty(mActivitiesBeanList.get(position).getBelong_game())){
                holder.tvGameTitle.setText("["+ Utils.getJieQu(mActivitiesBeanList.get(position).getBelong_game())+"]"
                        +Utils.getJieQu(mActivitiesBeanList.get(position).getTitle())
                        +Utils.getJieQu(mActivitiesBeanList.get(position).getDescription()));
            }else {
                holder.tvGameTitle.setText(Utils.getJieQu(mActivitiesBeanList.get(position).getTitle())
                        + Utils.getJieQu(mActivitiesBeanList.get(position).getDescription()));
            }
            //活动时间
            holder.tvGameDate.setText("活动时间：" + mActivitiesBeanList.get(position).getStart_time() + "~"
                    + mActivitiesBeanList.get(position).getEnd_time());
            //活动即将开始
            if (-1 == mActivitiesBeanList.get(position).getArticle_status()) {
                holder.llNotUnderWay.setVisibility(View.GONE);
                holder.llUnderWay.setVisibility(View.GONE);
                holder.llComingWay.setVisibility(View.VISIBLE);
            }
            //活动正在进行中
            if (1 == mActivitiesBeanList.get(position).getArticle_status()) {
                holder.llNotUnderWay.setVisibility(View.GONE);
                holder.llUnderWay.setVisibility(View.VISIBLE);
                holder.llComingWay.setVisibility(View.GONE);
            }
            //活动已结束
            if (0 == mActivitiesBeanList.get(position).getArticle_status()) {
                holder.llNotUnderWay.setVisibility(View.VISIBLE);
                holder.llUnderWay.setVisibility(View.GONE);
                holder.llComingWay.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (0 == mActivitiesBeanList.size()) {
            return 0;
        } else {
            return mActivitiesBeanList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGamePic;
        TextView tvGameTitle;
        TextView tvGameDate;
        LinearLayout llUnderWay;
        LinearLayout llNotUnderWay;
        LinearLayout llComingWay;

        public ViewHolder(View itemView) {
            super(itemView);
            imgGamePic = (ImageView) itemView.findViewById(R.id.img_home_game_activities_pic);
            tvGameTitle = (TextView) itemView.findViewById(R.id.tv_home_game_activities_title);
            tvGameDate = (TextView) itemView
                    .findViewById(R.id.tv_home_game_activities_date);
            llUnderWay = (LinearLayout) itemView.findViewById(R.id.ll_game_activities_under_way);
            llNotUnderWay = (LinearLayout) itemView
                    .findViewById(R.id.ll_game_activities_not_under_way);
            llComingWay = (LinearLayout) itemView
                    .findViewById(R.id.ll_game_activities_coming_way);
        }
    }
}
