package com.nian.wan.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;
import com.bumptech.glide.Glide;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/10
 * @Description: 全局搜索热门推荐RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class SearchHotRecyclerViewAdapter extends RecyclerView.Adapter<SearchHotRecyclerViewAdapter.ViewHolder> {

    private ArrayList<GameInfo> searchHotGame = new ArrayList<>();
    private WeakReference<Context> mWeakReference;

    public SearchHotRecyclerViewAdapter( Context context) {
        mWeakReference = new WeakReference(context);
    }

    public void setSearchHotGame(ArrayList<GameInfo> searchHotGame) {
        this.searchHotGame = searchHotGame;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_hot_game_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Glide.with(x.app()).load(searchHotGame.get(position).iconurl).error(R.drawable.default_picture).into(holder.imgGamePic);
        holder.tvGameName.setText(searchHotGame.get(position).name);
        if (searchHotGame.get(position).sdk_version == 3){
            holder.imgTag.setBackgroundResource(R.mipmap.tag_h5);
        }else {
            holder.imgTag.setBackgroundResource(R.mipmap.tag_shouyou);
        }

        holder.imgGamePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != Utils.getPersistentUserInfo()) {
                    if (searchHotGame.get(position).sdk_version == 3){
                        Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                        intent.putExtra("game_id", searchHotGame.get(position).id);
                        mWeakReference.get().startActivity(intent);
                    }else {
                        Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                        intent.putExtra("game_id", searchHotGame.get(position).id);
                        mWeakReference.get().startActivity(intent);
                    }
                } else {
                    new DialogGoLogin((Activity) mWeakReference.get(), R.style.MyDialogStyle, "暂时无法玩游戏哦~T_T~").show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchHotGame.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGamePic;
        TextView tvGameName;
        ImageView imgTag;


        public ViewHolder(View itemView) {
            super(itemView);
            imgGamePic = itemView.findViewById(R.id.btn_home_recently_pic);
            tvGameName = itemView.findViewById(R.id.btn_home_recently_name);
            imgTag = itemView.findViewById(R.id.img_tag);
        }
    }

}
