package com.nian.wan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.bean.RecentlyPlayBean;
import com.bumptech.glide.Glide;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/10
 * @Description: 首页最近在玩RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class HomeSlideRecentAdapter extends RecyclerView.Adapter<HomeSlideRecentAdapter.ViewHolder> {

    private List<RecentlyPlayBean> listData;
    private WeakReference<Context> mWeakReference;

    public HomeSlideRecentAdapter(List<RecentlyPlayBean> listBean, Context context) {
        this.listData = listBean;
        mWeakReference = new WeakReference(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_recently_play_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(listData.get(position).getIcon())) {
            Glide.with(x.app()).load(listData.get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgGamePic);
        }
        if (listData.get(position).getSdk_version().equals("3")){
            holder.imgTag.setBackgroundResource(R.mipmap.tag_h5);
        }else {
            holder.imgTag.setBackgroundResource(R.mipmap.tag_shouyou);
        }
        holder.tvGameName.setText(listData.get(position).getGame_name());
        holder.btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.ll_recent_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listData.get(position).getSdk_version().equals("3")){
                    Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", Integer.parseInt(listData.get(position).getId()));
                    mWeakReference.get().startActivity(intent);
                }else {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                    intent.putExtra("game_id", Integer.parseInt(listData.get(position).getId()));
                    mWeakReference.get().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGamePic;
        TextView tvGameName;
        Button btnStartGame;
        LinearLayout ll_recent_play;
        ImageView imgTag;


        public ViewHolder(View itemView) {
            super(itemView);
            imgGamePic = (ImageView) itemView.findViewById(R.id.btn_home_recently_pic);
            tvGameName = (TextView) itemView.findViewById(R.id.btn_home_recently_name);
            btnStartGame = (Button) itemView.findViewById(R.id.btn_home_recently_start);
            ll_recent_play = (LinearLayout) itemView.findViewById(R.id.ll_recent_play);
            imgTag = (ImageView) itemView.findViewById(R.id.img_tag);
        }
    }

}
