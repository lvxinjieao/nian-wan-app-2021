package com.nian.wan.app.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.HomeGiftDetailActivity;
import com.nian.wan.app.bean.BaseMulDataBean;
import com.nian.wan.app.bean.GiftBodyBean;
import com.nian.wan.app.bean.GiftFootBean;
import com.nian.wan.app.bean.GiftHeadBean;
import com.nian.wan.app.holder.BaseMulViewHolder;
import com.nian.wan.app.utils.Utils;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.ToastUtil;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @Author: XiaYuShi
 * @Description:
 * @Date: Created in 2017/10/19 16:15
 * @Modified By:
 * @Modified Date:
 */
public class MyGiftRecyclerAdapter extends RecyclerView.Adapter<BaseMulViewHolder> {

    /**
     * 定义三种布局类型
     */
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_BODY = 2;
    public static final int TYPE_FOOT = 3;

    /**
     * 数据集合
     */
    private List<BaseMulDataBean> mList;

    private WeakReference<Context> mWeakReference;


    @Override
    public BaseMulViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据不同的布局类型，设置创建相关的holder
        switch (viewType) {
            case TYPE_HEAD:
                return new HeadHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_gift_head, parent, false));
            case TYPE_BODY:
                return new BodyHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_gift_body, parent, false));
            case TYPE_FOOT:
                return new FootHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_gift_foot, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseMulViewHolder holder, int position) {
        if (holder instanceof HeadHolder) {
            //绑定数据
            holder.bindData(mList.get(position), position);
        }
        if (holder instanceof BodyHolder) {
            holder.bindData(mList.get(position), position);
        }
        if (holder instanceof FootHolder) {
            holder.bindData(mList.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return mList.get(position).getType();
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setDatas(List<BaseMulDataBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        mWeakReference = new WeakReference(context);
    }

    public List<BaseMulDataBean> getDatas() {
        return mList;
    }

    /**
     * 设置第一个布局的数据
     */
    class HeadHolder extends BaseMulViewHolder<GiftHeadBean> {
        TextView tvGameName;
        ImageView imgGiftPic;
        ImageView imgTag;

        public HeadHolder(View itemView) {
            super(itemView);
            imgGiftPic = (ImageView) itemView.findViewById(R.id.img_gift_pic);
            tvGameName = (TextView) itemView.findViewById(R.id.tv_game_name);
            imgTag = (ImageView) itemView.findViewById(R.id.img_tag);
        }

        @Override
        public void bindData(GiftHeadBean dataModel, int postion) {
            tvGameName.setText(Utils.getJieQu(dataModel.getGameName()));
            tvGameName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //判断是H5游戏还是手游，跳转到相应详情页（后期优化）
                }
            });

            if (dataModel.getSdkVersion() == 3){
                imgTag.setBackgroundResource(R.mipmap.tag_h5);
            }else {
                imgTag.setBackgroundResource(R.mipmap.tag_shouyou);
            }
            Glide.with(x.app()).load(dataModel.getGameImage()).error(R.drawable.default_picture).into(imgGiftPic);
        }


    }


    /**
     * 设置第二个布局的数据
     */
    class BodyHolder extends BaseMulViewHolder<GiftBodyBean> {

        TextView tvGiftName;
        TextView tvCopyGiftCode;
        TextView tvGiftCode;
        TextView tvGiftDescription;
        LinearLayout layoutMain;
        View view;

        public BodyHolder(View itemView) {
            super(itemView);
            tvGiftName = (TextView) itemView.findViewById(R.id.tv_my_gift_name);
            tvCopyGiftCode = (TextView) itemView.findViewById(R.id.btn_copy_gift_code);
            tvGiftCode = (TextView) itemView.findViewById(R.id.tv_my_gift_code);
            tvGiftDescription = (TextView) itemView.findViewById(R.id.tv_my_gift_description);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layout_main);
            view = itemView.findViewById(R.id.vw_line);
            Log.i("->>", "重复执行BodyHolder");
        }

        @Override
        public void bindData(final GiftBodyBean dataModel, final int position) {
            Log.i("->>", "重复执行bindData");
            tvGiftName.setText("["+dataModel.getGiftName()+"]");
            tvGiftCode.setText(dataModel.getGiftCode());
            tvGiftDescription.setText(dataModel.getGiftDescription());
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mWeakReference.get(), HomeGiftDetailActivity.class);
                    intent.putExtra("gift_id", dataModel.getGiftId());
                    mWeakReference.get().startActivity(intent);
                }
            });
            //如果为true则显示分割线否则不显示
            if (mList.indexOf(dataModel) == mList.size() - 1) {
                view.setVisibility(View.GONE);
            }
            tvCopyGiftCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) view.getContext()
                            .getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    ClipData mClipData = ClipData
                            .newPlainText("Label", dataModel.getGiftCode());
                    cm.setPrimaryClip(mClipData);

                    ToastUtil.showToast("已复制");
                }
            });
        }
    }


    /**
     * 设置第三个布局的数据
     */
    class FootHolder extends BaseMulViewHolder<GiftFootBean> {

        TextView textView;

        public FootHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.holder3_tv);
        }

        @Override
        public void bindData(GiftFootBean dataModel, int postion) {
            textView.setText(dataModel.getTitle());
        }

    }


}
