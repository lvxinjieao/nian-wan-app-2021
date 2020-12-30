package com.nian.wan.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.UserBalanceBean;
import com.bumptech.glide.Glide;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/2 11:40
 * @Modified By:
 * @Modified Date:
 */
public class BalanceBindPtbRecyclerViewAdapter extends RecyclerView.Adapter<BalanceBindPtbRecyclerViewAdapter.ViewHolder> {

    //弱引用上下文
    private WeakReference<Context> mWeakReference;
    //数据源
    private List<UserBalanceBean.BinddataBean> dataBeanList;



    public BalanceBindPtbRecyclerViewAdapter(Context context) {
        this.mWeakReference = new WeakReference(context);
    }

    public void setData(List<UserBalanceBean.BinddataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mWeakReference.get())
                .inflate(R.layout.viewpager_balance_bind_platform_coin_item, null,
                        false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定平台币的游戏图片
        Glide.with(x.app()).load(dataBeanList.get(position).getIcon()).error(R.drawable.default_picture).into(holder.imgGamePic);
        //绑定平台币的游戏名称
        holder.tvGameName.setText(dataBeanList.get(position).getGame_name());
        //游戏中绑定平台币的数量
        holder.tvBindNumber.setText(dataBeanList.get(position).getBind_balance());
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgGamePic;
        private TextView tvGameName;
        private TextView tvBindNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            //绑定平台币的游戏图片
            imgGamePic = (ImageView) itemView
                    .findViewById(R.id.img_personal_center_balance_bind_game_icon);
            //绑定平台币的游戏名称
            tvGameName = (TextView) itemView
                    .findViewById(R.id.tv_personal_center_balance_bind_game_name);
            //游戏中绑定平台币的数量
            tvBindNumber = (TextView) itemView
                    .findViewById(R.id.img_personal_center_balance_bind_game_money);
        }
    }

}
