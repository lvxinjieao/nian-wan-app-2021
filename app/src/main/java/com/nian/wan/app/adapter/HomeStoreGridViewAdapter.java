package com.nian.wan.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.HomeStoreGoodsBean;
import com.bumptech.glide.Glide;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/23
 * @Description: 首页商城GrideView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class HomeStoreGridViewAdapter extends BaseAdapter {

    private List<HomeStoreGoodsBean> homeStoreGoodsBeans = new ArrayList<>();
    private WeakReference<Context> weakReference;

    public HomeStoreGridViewAdapter(Context context) {
        this.weakReference = new WeakReference(context);
    }

    public void setData(List<HomeStoreGoodsBean> homeStoreGoodsBeans) {
        this.homeStoreGoodsBeans = homeStoreGoodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return homeStoreGoodsBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(weakReference.get())
                    .inflate(R.layout.home_store_goods_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (!TextUtils.isEmpty(homeStoreGoodsBeans.get(i).getCover())) {
            Glide.with(x.app()).load( homeStoreGoodsBeans.get(i).getCover()).into(holder.imgHomeStoreGoodsPic);
        }
        holder.tvHomeStoreGoodsName.setText(homeStoreGoodsBeans.get(i).getGood_name());
        holder.tvHomeStoreGoodsCostScore.setText(homeStoreGoodsBeans.get(i).getPrice());
        holder.tvHomeStoreGoodsItemNumber.setText(homeStoreGoodsBeans.get(i).getNumber());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_home_store_goods_pic)
        ImageView imgHomeStoreGoodsPic;
        @BindView(R.id.tv_home_store_goods_name)
        TextView tvHomeStoreGoodsName;
        @BindView(R.id.tv_home_store_goods_cost_score)
        TextView tvHomeStoreGoodsCostScore;
        @BindView(R.id.tv_home_store_goods_item_number)
        TextView tvHomeStoreGoodsItemNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
