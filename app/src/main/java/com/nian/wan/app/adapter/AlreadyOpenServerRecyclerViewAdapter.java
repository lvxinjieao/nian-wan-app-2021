package com.nian.wan.app.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.bean.AlreadyOpenServerBean;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;
import com.nian.wan.app.view.ShapeImageView;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description: 已开服RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class AlreadyOpenServerRecyclerViewAdapter extends
        RecyclerView.Adapter<AlreadyOpenServerRecyclerViewAdapter.ViewHolder> {


    private List<AlreadyOpenServerBean> mOpenServerBeanList = new ArrayList<>();
    private WeakReference<Context> mWeakReference;

    public AlreadyOpenServerRecyclerViewAdapter(Context context) {
        this.mWeakReference = new WeakReference(context);
    }

    public void setData(List<AlreadyOpenServerBean> mOpenServerBeanList) {
        this.mOpenServerBeanList = mOpenServerBeanList;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_open_server, parent, false);
        mWeakReference = new WeakReference(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(x.app()).load(mOpenServerBeanList.get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgOpenServerPic);
        holder.tvOpenServerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mWeakReference.get(), "开始游戏", Toast.LENGTH_SHORT).show();
            }
        });
        //游戏名称
        holder.tvOpenServerGameName.setText(Utils.truncationCharacter(14, "..."
                , Utils.getJieQu(mOpenServerBeanList.get(position).getGame_name())));
        //区服名称
        holder.tvOpenServerName.setText(Utils.truncationCharacter(5, "...",
                mOpenServerBeanList.get(position).getServer_name()));
        //已开服时间
        holder.tvOpenServerDate.setText(mOpenServerBeanList.get(position).getPastTime());
        if (mOpenServerBeanList.get(position).getSdk_version() == 3) {
            holder.tvOpenServerStart.setText("开始");
        } else {
            holder.tvOpenServerStart.setText("下载");
            if (mOpenServerBeanList.get(position).getXia_status() == 0) {
                holder.tvOpenServerStart.setBackgroundResource(R.drawable.bian_kuang_gray);
                holder.tvOpenServerStart.setTextColor(mWeakReference.get().getResources().getColorStateList(R.color.qiangray));
                holder.tvOpenServerStart.setEnabled(false);
            } else {
                holder.tvOpenServerStart.setBackgroundResource(R.drawable.bian_kuang_green);
                holder.tvOpenServerStart.setTextColor(mWeakReference.get().getResources().getColorStateList(R.color.zhuse_lan));
                holder.tvOpenServerStart.setEnabled(true);
            }
        }
        //开始游戏
        holder.tvOpenServerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != Utils.getPersistentUserInfo()) {

                    Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                    intent.putExtra("game_id", Integer.valueOf(mOpenServerBeanList.get(position).getGame_id()));
                    mWeakReference.get().startActivity(intent);

                } else {
                    new DialogGoLogin((Activity) mWeakReference.get(), R.style.MyDialogStyle, "暂时无法玩游戏哦~").show();
                }
            }
        });
        //跳转游戏详情
        holder.rlOpenServerDataContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOpenServerBeanList.get(position).getSdk_version() == 3) {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", Integer.parseInt(mOpenServerBeanList.get(position).getGame_id()));
                    mWeakReference.get().startActivity(intent);
                } else {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                    intent.putExtra("game_id", Integer.parseInt(mOpenServerBeanList.get(position).getGame_id()));
                    mWeakReference.get().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (0 == mOpenServerBeanList.size()) {
            return 0;
        } else {
            return mOpenServerBeanList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_open_server_pic)
        ShapeImageView imgOpenServerPic;
        @BindView(R.id.tv_open_server_start)
        TextView tvOpenServerStart;
        @BindView(R.id.tv_open_server_game_name)
        TextView tvOpenServerGameName;
        @BindView(R.id.tv_open_server_name)
        TextView tvOpenServerName;
        @BindView(R.id.tv_open_server_date)
        TextView tvOpenServerDate;
        @BindView(R.id.rl_open_server_data_content)
        RelativeLayout rlOpenServerDataContent;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
