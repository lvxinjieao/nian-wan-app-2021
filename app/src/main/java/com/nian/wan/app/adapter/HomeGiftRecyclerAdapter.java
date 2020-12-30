package com.nian.wan.app.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.BaseMulDataBean;
import com.nian.wan.app.bean.HomeGiftBodyBean;
import com.nian.wan.app.bean.HomeGiftFootBean;
import com.nian.wan.app.bean.HomeGiftHeadBean;
import com.nian.wan.app.bean.MyFootprintGroupBean;
import com.nian.wan.app.bean.MyFootprintItemBean;
import com.nian.wan.app.holder.BaseMulViewHolder;

import java.util.List;
import java.util.Map;

/**
 * @Modified By: 首页礼包RecyclerView适配器
 */
public class HomeGiftRecyclerAdapter extends RecyclerView.Adapter<BaseMulViewHolder> {

    /**
     * 定义三种布局类型
     */
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_BODY = 2;
    public static final int TYPE_FOOT = 3;

    //我的足迹组集合
    private List<MyFootprintGroupBean> mMyFootprintGroupBean;
    //我的足迹子集合
    private Map<String, List<MyFootprintItemBean>> mMyFootprintItemBean;

    /**
     * 数据集合
     */
    private List<BaseMulDataBean> mList;


    @Override
    public BaseMulViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TestInitDate();
        //根据不同的布局类型，设置创建相关的holder
        switch (viewType) {
            case TYPE_HEAD:
                return new HeadHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_gift_head, parent, false));
            case TYPE_BODY:
                return new BodyHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_gift_body, parent, false));
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

    public List<BaseMulDataBean> getDatas() {
        return mList;
    }

    /**
     * 设置第一个布局的数据
     */
    class HeadHolder extends BaseMulViewHolder<HomeGiftHeadBean> {
        TextView tvGameName;

        public HeadHolder(View itemView) {
            super(itemView);
            tvGameName = (TextView) itemView.findViewById(R.id.tv_game_name);
        }

        @Override
        public void bindData(HomeGiftHeadBean dataModel, int postion) {
            tvGameName.setText(dataModel.getGameName());
        }

    }


    /**
     * 设置第二个布局的数据
     */
    class BodyHolder extends BaseMulViewHolder<HomeGiftBodyBean> {

        TextView tvGiftName;
        TextView tvCopyGiftCode;
        //        TextView tvGiftCode;
        TextView tvGiftDescription;
        ExpandableListView expandableListView;
        View view;

        public BodyHolder(View itemView) {
            super(itemView);
            tvGiftName = (TextView) itemView.findViewById(R.id.tv_my_gift_name);
            tvCopyGiftCode = (TextView) itemView.findViewById(R.id.btn_copy_gift_code);
//            tvGiftCode = (TextView) itemView.findViewById(R.id.tv_my_gift_code);
            expandableListView = (ExpandableListView) itemView
                    .findViewById(R.id.ev_expand_other_package);
            tvGiftDescription = (TextView) itemView.findViewById(R.id.tv_my_gift_description);
            view = itemView.findViewById(R.id.vw_line);

            Log.i("->>", "重复执行BodyHolder");
        }

        @Override
        public void bindData(final HomeGiftBodyBean dataModel, int postion) {
            Log.i("->>", "重复执行bindData");
            tvGiftName.setText(dataModel.getGiftName());
        /*    tvGiftCode.setText(dataModel.getGiftCode()
                    .substring(dataModel.getGiftCode().length() - 5,
                            dataModel.getGiftCode().length()));*/
            tvGiftDescription.setText(dataModel.getGiftDescription());
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

                    Toast.makeText(view.getContext(), dataModel.getGiftCode(), Toast.LENGTH_SHORT)
                            .show();
                }
            });
            MyFootprintExpandableListviewAdapter listviewAdapter =
                    new MyFootprintExpandableListviewAdapter(itemView.getContext());
            listviewAdapter.setData(mMyFootprintGroupBean, mMyFootprintItemBean);
            expandableListView.setAdapter(listviewAdapter);
        }

    }


    /**
     * 设置第三个布局的数据
     */
    class FootHolder extends BaseMulViewHolder<HomeGiftFootBean> {

        TextView textView;

        public FootHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.holder3_tv);
        }

        @Override
        public void bindData(HomeGiftFootBean dataModel, int postion) {
            textView.setText(dataModel.getTitle());
        }


    }


    /**
     * 测试方法
     */
 /*   public void TestInitDate() {
        mMyFootprintGroupBean = new ArrayList<>();
        mMyFootprintItemBean = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            mMyFootprintGroupBean.add(new MyFootprintGroupBean(String.valueOf(i),
                    "超级好玩的游戏" + i));
            List<MyFootprintItemBean> footprintItemBeanList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                footprintItemBeanList.add(new MyFootprintItemBean(String.valueOf(j),
                        mMyFootprintGroupBean.get(i).getDate() + "的足迹", ""));
            }
            mMyFootprintItemBean.put(mMyFootprintGroupBean.get(i).getId(), footprintItemBeanList);
        }
    }*/


}
