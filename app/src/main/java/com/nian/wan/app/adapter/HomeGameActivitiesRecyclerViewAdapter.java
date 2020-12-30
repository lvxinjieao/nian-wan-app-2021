package com.nian.wan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.XinWenDetActivity;
import com.nian.wan.app.bean.HomeGameActivitiesBean;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description:
 * @Modify By: 首页游戏活动RecyclerView适配器
 * @ModifyDate:
 */

public class HomeGameActivitiesRecyclerViewAdapter extends
        RecyclerView.Adapter<HomeGameActivitiesRecyclerViewAdapter.ViewHolder> {


    private List<HomeGameActivitiesBean> list;
    private WeakReference<Context> mWeakReference;

    public HomeGameActivitiesRecyclerViewAdapter(List<HomeGameActivitiesBean> activitiesBeanList,
                                                 Context context) {
        this.list = activitiesBeanList;
        this.mWeakReference = new WeakReference(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_game_activities_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        if (null!=mActivitiesBeanList.getData().get(position).ge)
        holder.tvHomeGameActivitieName.setText(list.get(position)
                .getTitle());
        holder.tvHomeGameActivitiesDescription.setText(list.get(position)
                .getDescription());
        if ("app_huodong".equals(list.get(position).getType())) {
            holder.tvHomeGameActivitiesActivities.setVisibility(View.VISIBLE);
            holder.tvHomeGameActivitiesPublish.setVisibility(View.GONE);
        } else {
            holder.tvHomeGameActivitiesActivities.setVisibility(View.GONE);
            holder.tvHomeGameActivitiesPublish.setVisibility(View.VISIBLE);
        }
        holder.tvHomeGameActivitiesStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), XinWenDetActivity.class);
                intent.putExtra("type_id", 2);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("topTitle", list.get(position).getBelong_game());
                intent.putExtra("URL", list.get(position).getUrl());
                mWeakReference.get().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_home_game_activities_start)
        TextView tvHomeGameActivitiesStart;
        @BindView(R.id.tv_home_game_activitie_name)
        TextView tvHomeGameActivitieName;
        @BindView(R.id.tv_home_game_activities_publish)
        TextView tvHomeGameActivitiesPublish;
        @BindView(R.id.tv_home_game_activities_activities)
        TextView tvHomeGameActivitiesActivities;
        @BindView(R.id.tv_home_game_activities_description)
        TextView tvHomeGameActivitiesDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
