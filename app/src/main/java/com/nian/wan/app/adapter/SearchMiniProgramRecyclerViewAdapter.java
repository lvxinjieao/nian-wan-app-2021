package com.nian.wan.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.GlobalSearchBean;
import com.nian.wan.app.view.DialogMiniProgram;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.ToastUtil;

/**
 * 描述：搜索小程序Adapter
 * 作者：闫冰
 * 时间: 2018-06-04 9:34
 */
public class SearchMiniProgramRecyclerViewAdapter extends RecyclerView.Adapter<SearchMiniProgramRecyclerViewAdapter.ViewHolder>{
    private GlobalSearchBean dataBean;
    private Activity mActivity;
    private boolean isGetMore;


    public SearchMiniProgramRecyclerViewAdapter(GlobalSearchBean bean, Activity activity) {
        this.mActivity = activity;
        this.dataBean = bean;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_miniprogram, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (dataBean.getSamllgame().size()>1){

        }
        if (!TextUtils.isEmpty(dataBean.getSamllgame().get(position).getIcon())) {
            //游戏图标
            Glide.with(mActivity).load(dataBean.getSamllgame().get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgGamePic);
        }
        //游戏名称
        holder.tvGameName.setText(dataBean.getSamllgame().get(position).getGame_name());

        //去玩
        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(dataBean.getSamllgame().get(position).getQrcodeurl())){
                    DialogMiniProgram dialog = new DialogMiniProgram(mActivity,R.style.MyDialogStyle,dataBean.getSamllgame().get(position).getQrcodeurl());
                    dialog.show();
                }else {
                    ToastUtil.showToast("二维码参数为空");
                }
            }
        });

    }


    public void getMore(boolean more){
        this.isGetMore = more;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(dataBean.getSamllgame()!=null){
            if(dataBean.getSamllgame().size()>=3 && !isGetMore){
                return 3;
            }else{
                return dataBean.getSamllgame().size();
            }
        }
        return 0;
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
