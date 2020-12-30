package com.nian.wan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.HomeGiftDetailActivity;
import com.nian.wan.app.bean.GetGiftBean;
import com.nian.wan.app.bean.GlobalSearchBean;
import com.nian.wan.app.utils.TimeUtils;
import com.nian.wan.app.utils.Utils;
import com.bumptech.glide.Glide;

import org.xutils.x;

import java.lang.ref.WeakReference;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/11
 * @Description: 全局搜索礼包RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class SearchGiftRecyclerViewAdapter extends RecyclerView
        .Adapter<SearchGiftRecyclerViewAdapter.ViewHolder> {


    private GlobalSearchBean giftBeanList;
    private WeakReference<Context> mWeakReference;
    private GetGiftBean getGiftBean;

    ImageView imgGamePic;
    TextView tvGameName;
    RoundCornerProgressBar pbNumber;
    TextView tvNumber;
    TextView tvValidDate;
    TextView tvGetGift;
    RelativeLayout rlSearchGameGiftContent;
    private boolean xian = true;


    public SearchGiftRecyclerViewAdapter(GlobalSearchBean giftBeanList, Context context) {
        this.giftBeanList = giftBeanList;
        mWeakReference = new WeakReference(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.global_search_gift_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setXian(boolean b){
        xian = b;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(x.app()).load(giftBeanList.getGift().get(position).getIcon()).error(R.drawable.default_picture).into(imgGamePic);
        //礼包名称
        tvGameName.setText("[" + Utils.getJieQu(giftBeanList.getGift().get(position).getGame_name()) + "]" +
                giftBeanList.getGift().get(position).getGiftbag_name());
        //礼包剩余数量
        int coast = giftBeanList.getGift().get(position).getNovice_surplus();
        tvNumber.setText("剩余："+coast);

        //礼包有效期
        String validDate = "0".equals(giftBeanList.getGift().get(position).getEnd_time()) ? "永久" : TimeUtils.timesThree(giftBeanList.getGift().get(position).getEnd_time());
        tvValidDate.setText(TimeUtils.timesThree(giftBeanList.getGift().get(position).getStart_time()) + "~" + validDate);
        //领取礼包
        tvGetGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), HomeGiftDetailActivity.class);
                intent.putExtra("gift_id", giftBeanList.getGift().get(position).getGift_id());
                mWeakReference.get().startActivity(intent);
            }
        });
        rlSearchGameGiftContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), HomeGiftDetailActivity.class);
                intent.putExtra("gift_id", giftBeanList.getGift().get(position).getGift_id());
                mWeakReference.get().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(giftBeanList.getGift()!=null){
            if(giftBeanList.getGift().size()>=3&&xian){
                return 3;
            }else{
                return giftBeanList.getGift().size();
            }
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            imgGamePic = (ImageView) itemView.findViewById(R.id.img_home_gift_detail_game_pic);
            tvGameName = (TextView) itemView.findViewById(R.id.tv_home_gift_detail_game_name);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_home_gift_detail_gift_number);
            tvValidDate = (TextView) itemView.findViewById(R.id.tv_home_gift_detail_valid_date);
            tvGetGift = (TextView) itemView.findViewById(R.id.tv_home_gift_detail_get_gift);
            rlSearchGameGiftContent = (RelativeLayout) itemView
                    .findViewById(R.id.rl_search_game_gift_content);
        }
    }

}
