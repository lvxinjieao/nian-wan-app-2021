package com.nian.wan.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.HomeMiniBean;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogMiniProgram;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：首页小程序
 * 作者：闫冰
 * 时间: 2018-06-04 9:34
 */
public class HomeMiniProgramRecyclerViewAdapter extends RecyclerView.Adapter<HomeMiniProgramRecyclerViewAdapter.ViewHolder>{
    private List<HomeMiniBean> mHomeMiniBeanList = new ArrayList<>();
    private WeakReference<Context> mWeakReference;
    private Activity mActivity;


    public HomeMiniProgramRecyclerViewAdapter(Activity activity) {
        mWeakReference = new WeakReference(activity);
        this.mActivity = activity;
    }

    public void setData(List<HomeMiniBean> homeMiniBeans) {
        this.mHomeMiniBeanList = homeMiniBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_miniprogram, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (!TextUtils.isEmpty(mHomeMiniBeanList.get(position).getIcon())) {
            //游戏图标
            Glide.with(mActivity).load(mHomeMiniBeanList.get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgGamePic);
        }
        //游戏名称
        holder.tvGameName.setText(Utils.truncationCharacter(14, "...",
                mHomeMiniBeanList.get(position).getGame_name()));

        //去玩
        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mHomeMiniBeanList.get(position).getQrcodeurl())){
                    DialogMiniProgram dialog = new DialogMiniProgram(mActivity,R.style.MyDialogStyle,mHomeMiniBeanList.get(position).getQrcodeurl());
                    dialog.show();
                }else {
                    ToastUtil.showToast("二维码参数为空");
                }
            }
        });

    }




    @Override
    public int getItemCount() {
        if (mHomeMiniBeanList.size() == 0) {
            return 0;
        } else {
            return mHomeMiniBeanList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGamePic;
        TextView tvGameName;
        TextView btnPlay;


        public ViewHolder(View itemView) {
            super(itemView);
            imgGamePic = (ImageView) itemView.findViewById(R.id.img_icon);
            tvGameName = (TextView) itemView.findViewById(R.id.tv_game_name);
            btnPlay= (TextView) itemView.findViewById(R.id.btn_play);
        }
    }
}
