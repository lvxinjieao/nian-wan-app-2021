package com.nian.wan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.XinWenDetActivity;
import com.nian.wan.app.bean.GlobalSearchBean;
import com.nian.wan.app.utils.Utils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description:
 * @Modify By: 全局搜索活动RecyclerView适配器
 * @ModifyDate:
 */

public class SearchActivitiesRecyclerViewAdapter extends
        RecyclerView.Adapter<SearchActivitiesRecyclerViewAdapter.ViewHolder> {


    private GlobalSearchBean mActivitiesBeanList;
    private WeakReference<Context> mWeakReference;
    private boolean xian = true;

    public SearchActivitiesRecyclerViewAdapter(GlobalSearchBean activitiesBeanList, Context context) {
        this.mActivitiesBeanList = activitiesBeanList;
        this.mWeakReference = new WeakReference(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_game_activities_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvHomeGameActivitieName.setText("《" + Utils.getJieQu(mActivitiesBeanList.getArticle().get(position).getBelong_game()) + "》"
                + mActivitiesBeanList.getArticle().get(position).getTitle());
        holder.tvHomeGameActivitiesDescription.setText(mActivitiesBeanList.getArticle()
                .get(position).getDescription());
        if (mActivitiesBeanList.getArticle().get(position).getHdtype().equals("公告")) {
            holder.tvHomeGameActivitiesPublish.setVisibility(View.VISIBLE);
            holder.tvHomeGameActivitiesActivities.setVisibility(View.GONE);
        } else {
            holder.tvHomeGameActivitiesPublish.setVisibility(View.GONE);
            holder.tvHomeGameActivitiesActivities.setVisibility(View.VISIBLE);
        }
        holder.tvHomeGameActivitiesStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), XinWenDetActivity.class);
                intent.putExtra("type_id", 2);
                intent.putExtra("id", mActivitiesBeanList.getArticle().get(position).getId());
                intent.putExtra("topTitle", mActivitiesBeanList.getArticle().get(position).getBelong_game());
                intent.putExtra("URL", mActivitiesBeanList.getArticle().get(position).getArticle_detail_url());
                mWeakReference.get().startActivity(intent);
            }
        });
        holder.llMyGiftHotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), XinWenDetActivity.class);
                intent.putExtra("type_id", 2);
                intent.putExtra("id", mActivitiesBeanList.getArticle().get(position).getId());
                intent.putExtra("topTitle", mActivitiesBeanList.getArticle().get(position).getBelong_game());
                intent.putExtra("URL", mActivitiesBeanList.getArticle().get(position).getArticle_detail_url());
                mWeakReference.get().startActivity(intent);
            }
        });
    }

    public void setXian(boolean b){
        xian = b;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(mActivitiesBeanList.getArticle()!=null){
            if(mActivitiesBeanList.getArticle().size()>=3&&xian){
                return 3;
            }else{
                return mActivitiesBeanList.getArticle().size();
            }
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        /*        @BindView(R.id.img_home_game_activities_pic)
                ShapeImageView imgHomeGameActivitiesPic;*/
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
        @BindView(R.id.ll_my_gift_hot_text)
        LinearLayout llMyGiftHotText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
