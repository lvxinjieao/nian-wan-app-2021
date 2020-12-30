package com.nian.wan.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.bean.MyCollectionHotGameBean;
import com.nian.wan.app.bean.ShouCangBean;
import com.bumptech.glide.Glide;

import android.widget.RelativeLayout;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @Description: 个人中心-我的收藏-热门游戏RecyclerView适配器
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/7 9:32
 * @Modified By:
 * @Modified Date:
 */
public class MyCollectionRecommendRecyclerAdapter extends
        RecyclerView.Adapter<MyCollectionRecommendRecyclerAdapter.ViewHolder> {

    private List<MyCollectionHotGameBean> mHotGameBeanList;
    private WeakReference<Context> mWeakReference;
    private ShouCangBean shouCangBean;
    private ImageView imgGamePic;
    private TextView tvGameName;
    private TextView tvGameType;
    private TextView tvGameCollecedNumber;
    private TextView tvGameDescription;
    private TextView tvGameCollected;
    private TextView tvLiBao;
    private TextView tvFanLi;
    private TextView textView2;
    private RelativeLayout rlItemLayout;
    private String gameUrl;


    public MyCollectionRecommendRecyclerAdapter(Context context) {
        this.mWeakReference = new WeakReference(context);
    }

    public void setmHotGameBeanList(List<MyCollectionHotGameBean> mHotGameBeanList) {
        this.mHotGameBeanList = mHotGameBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gameholder_game_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        tvLiBao.setVisibility(View.GONE);
        tvFanLi.setVisibility(View.GONE);

        Glide.with(x.app()).load(mHotGameBeanList.get(position).getGamePic()).error(R.drawable.default_picture).into(imgGamePic);

        rlItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHotGameBeanList.get(position).getSdk_version().equals("3")) {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", Integer.parseInt(mHotGameBeanList.get(position).getGameId()));
                    mWeakReference.get().startActivity(intent);
                } else {
                    Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                    intent.putExtra("game_id", Integer.parseInt(mHotGameBeanList.get(position).getGameId()));
                    mWeakReference.get().startActivity(intent);
                }
            }
        });


        tvGameCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), GameDetailActivity.class);
                intent.putExtra("game_id", Integer.parseInt(mHotGameBeanList.get(position).getGameId()));
                mWeakReference.get().startActivity(intent);
            }
        });


        //热门游戏名称
        tvGameName.setText(mHotGameBeanList.get(position).getGameName());
        //热门游戏类型
        tvGameType.setText(mHotGameBeanList.get(position).getGameType());
        //热门游戏收藏人数
        tvGameCollecedNumber.setText(mHotGameBeanList.get(position).getPlay_num());
        //热门游戏描述
        tvGameDescription.setText(mHotGameBeanList.get(position).getGameDescription());
        if ("1".equals(mHotGameBeanList.get(position).getIsCollected())) {
            tvGameCollected.setEnabled(false);
            tvGameCollected.setTextColor(mWeakReference.get().getResources().getColor(R.color.font_gray));
            tvGameCollected.setText("已收藏");
            tvGameCollected.setBackground(ContextCompat.getDrawable(mWeakReference.get(), R.drawable.zhi_jiao_gray));
        }

        if (mHotGameBeanList.get(position).getSdk_version().equals("3")) {
            tvGameCollected.setText("去玩");
        } else {
            tvGameCollected.setText("下载");
        }
    }

    @Override
    public int getItemCount() {
        return mHotGameBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            imgGamePic = (ImageView) itemView.findViewById(R.id.img_icon);
            tvGameName = (TextView) itemView.findViewById(R.id.tv_game_name);
            tvGameType = (TextView) itemView.findViewById(R.id.tv_type);
            tvGameCollecedNumber = (TextView) itemView.findViewById(R.id.tv_num);
            tvGameDescription = (TextView) itemView.findViewById(R.id.tv_msg);
            tvGameCollected = (TextView) itemView.findViewById(R.id.btn_start);
            tvLiBao = (TextView) itemView.findViewById(R.id.tv_package);
            tvFanLi = (TextView) itemView.findViewById(R.id.tv_fanli);
            textView2 = (TextView) itemView.findViewById(R.id.tv_hint);
            rlItemLayout = itemView.findViewById(R.id.btn_item_layout);
        }
    }


}
