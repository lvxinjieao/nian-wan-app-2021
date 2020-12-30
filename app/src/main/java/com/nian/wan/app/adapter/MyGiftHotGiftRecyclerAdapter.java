package com.nian.wan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.HomeGiftDetailActivity;
import com.nian.wan.app.bean.MyGiftHotGiftBean;
import com.nian.wan.app.utils.Utils;
import com.bumptech.glide.Glide;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @Description: 个人中心-我的礼包-热门礼包
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/6 10:31
 * @Modified By:
 * @Modified Date:
 */
public class MyGiftHotGiftRecyclerAdapter extends RecyclerView
        .Adapter<MyGiftHotGiftRecyclerAdapter.ViewHolder> {


    private List<MyGiftHotGiftBean> mHotGiftBeanList;
    private WeakReference<Context> mWeakReference;

    public MyGiftHotGiftRecyclerAdapter(List<MyGiftHotGiftBean> hotGiftBeanList, Context context) {
        this.mHotGiftBeanList = hotGiftBeanList;
        this.mWeakReference = new WeakReference(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_gift_det, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(x.app()).load(mHotGiftBeanList.get(position).getGiftPic()).error(R.drawable.default_picture).into(holder.imgMyGiftHotPic);
        holder.tvMyGiftHotName.setText("《" + Utils.getJieQu(mHotGiftBeanList.get(position).getGameName()) + "》" + mHotGiftBeanList.get(position).getGiftName());
        holder.tvMyGiftHotPercent.setText(String.valueOf("剩余："+mHotGiftBeanList.get(position).getGiftCurrentNumber()));
        holder.tvMyGiftValidity.setText("有效期："+mHotGiftBeanList.get(position).getGiftValidityTime());
        if (mHotGiftBeanList.get(position).getGameType() == 3){
            holder.imgHint.setBackgroundResource(R.mipmap.tag_h5);
        }else {
            holder.imgHint.setBackgroundResource(R.mipmap.tag_shouyou);
        }
        holder.bindData(mHotGiftBeanList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mHotGiftBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMyGiftHotPic;
        ImageView imgHint;
        TextView tvMyGiftHotName;
        TextView tvMyGiftHotPercent;
        TextView tvMyGiftValidity;
        TextView tvMyGiftHotGet;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgMyGiftHotPic = (ImageView) itemView.findViewById(R.id.img_my_gift_hot_pic);
            tvMyGiftHotName = (TextView) itemView.findViewById(R.id.tv_my_gift_hot_name);
            tvMyGiftHotPercent = (TextView) itemView.findViewById(R.id.tv_my_gift_hot_percent);
            tvMyGiftValidity = (TextView) itemView.findViewById(R.id.tv_my_gift_hot_validity);
            tvMyGiftHotGet = (TextView) itemView.findViewById(R.id.tv_my_gift_hot_get);
            imgHint = (ImageView) itemView.findViewById(R.id.img_tag);
        }

        public void bindData(final MyGiftHotGiftBean giftBean, int position) {
            tvMyGiftHotGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mWeakReference.get(), HomeGiftDetailActivity.class);
                    intent.putExtra("gift_id", giftBean.getGiftId());
                    intent.putExtra("game_icon", giftBean.getGiftPic());
                    mWeakReference.get().startActivity(intent);
                }
            });
        }
    }

}
