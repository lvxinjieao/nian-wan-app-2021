package com.nian.wan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.activity.MyGameActivity;
import com.nian.wan.app.activity.broadcatInterface.IMyFootprintCheckBoxInteface;
import com.nian.wan.app.bean.MyFootprintGroupBean;
import com.nian.wan.app.bean.MyFootprintItemBean;
import com.bumptech.glide.Glide;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 我的收藏-我的足迹ExpandableLisview适配器
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/8 11:45
 * @Modified By:
 * @Modified Date:
 */
public class MyFootprintExpandableListviewAdapter extends BaseExpandableListAdapter {

    //我的足迹组集合
    private List<MyFootprintGroupBean> mMyFootprintGroupBean = new ArrayList<>();
    //我的足迹子集合
    private Map<String, List<MyFootprintItemBean>> mFootprintItemBean = new HashMap<>();
    //弱引用上下文
    private WeakReference<Context> mWeakReference;
    //是否显示所有CheckBox
    private boolean isShowAllCheckBox;
    //CheckBox选中接口
    private IMyFootprintCheckBoxInteface mMyFootprintCheckBoxInteface;

    //注册足迹CheckBox接口
    public void setmMyFootprintCheckBoxInteface(IMyFootprintCheckBoxInteface mMyFootprintCheckBoxInteface) {
        this.mMyFootprintCheckBoxInteface = mMyFootprintCheckBoxInteface;
    }

    public MyFootprintExpandableListviewAdapter(Context context) {
        this.mWeakReference = new WeakReference(context);
    }

    public void setData(List<MyFootprintGroupBean> myFootprintGroupBeen, Map<String, List<MyFootprintItemBean>> footprintItemBean){
        this.mMyFootprintGroupBean = myFootprintGroupBeen;
        this.mFootprintItemBean = footprintItemBean;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mMyFootprintGroupBean.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = mMyFootprintGroupBean.get(groupPosition).getId();
        return mFootprintItemBean.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mMyFootprintGroupBean.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<MyFootprintItemBean> myFootprintItemBeen = mFootprintItemBean.get(mMyFootprintGroupBean.get(groupPosition).getId());
        return myFootprintItemBeen.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (null == convertView) {
            convertView = View.inflate(mWeakReference.get(), R.layout.item_footprint_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        final MyFootprintGroupBean myFootprintGroupBean = (MyFootprintGroupBean) getGroup(groupPosition);
        groupViewHolder.tvMyFootprintDate.setText(myFootprintGroupBean.getDate());
        groupViewHolder.cbMyFootprintGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFootprintGroupBean.setChoosed(((CheckBox) v).isChecked());
                //调用组选中接口
                mMyFootprintCheckBoxInteface.checkGroup(groupPosition, ((CheckBox) v).isChecked());

                if (((CheckBox) v).isChecked()){
                    MyGameActivity.myFootPrintIsCheckAll = true;
                }else {
                    MyGameActivity.myFootPrintIsCheckAll = false;
                }
            }
        });

        groupViewHolder.cbMyFootprintGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck){
                    MyGameActivity.myFootPrintIsCheckAll = true;
                }else {
                    MyGameActivity.myFootPrintIsCheckAll = false;
                }
            }
        });
        groupViewHolder.cbMyFootprintGroup.setVisibility(isShowAllCheckBox == true ? View.VISIBLE : View.GONE);
        groupViewHolder.cbMyFootprintGroup.setChecked(myFootprintGroupBean.isChoosed());
        notifyDataSetChanged();
        return convertView;
    }


    //子元素
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ItemViewHolder itemViewHolder;
        if (null == convertView) {
            convertView = View.inflate(mWeakReference.get(), R.layout.item_footprint_item, null);
            itemViewHolder = new ItemViewHolder(convertView);
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        //获取当前组的子元素
        final MyFootprintItemBean myFootprintItemBean = (MyFootprintItemBean) getChild(groupPosition, childPosition);
        if (!TextUtils.isEmpty(myFootprintItemBean.getGamePic())) {
            Glide.with(x.app()).load(myFootprintItemBean.getGamePic()).error(R.drawable.default_picture).into(itemViewHolder.imgMyFootprintPic);
        }
        itemViewHolder.tvMyFootprintName.setText(myFootprintItemBean.getGameName());
        //此句代码对于组元素选中后其所属的子元素全部选中起作用
        itemViewHolder.cbMyFootprintItemIsCheck.setChecked(myFootprintItemBean.isChoosed());
        itemViewHolder.cbMyFootprintItemIsCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFootprintItemBean.setChoosed(((CheckBox) v).isChecked());
                //更新子元素CheckBox状态
                itemViewHolder.cbMyFootprintItemIsCheck.setChecked(((CheckBox) v).isChecked());
                //调用子元素选中接口
                mMyFootprintCheckBoxInteface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
            }
        });
        //当isShowAllCheckBox为true显示CheckBox,否则不显示
        itemViewHolder.cbMyFootprintItemIsCheck.setVisibility(isShowAllCheckBox == true ? View.VISIBLE : View.GONE);
        //如果当前的子元素是当前组最后一个,显示分割线
        if (isLastChild && getChild(groupPosition, childPosition) != null) {
            itemViewHolder.vLine.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.vLine.setVisibility(View.GONE);
        }
        itemViewHolder.tvMyFootprintDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mWeakReference.get(), GameDetailH5Activity.class);
                intent.putExtra("game_id", Integer.parseInt(myFootprintItemBean.getGameId()));
                mWeakReference.get().startActivity(intent);
            }
        });
        notifyDataSetChanged();
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    static class GroupViewHolder {

        @BindView(R.id.cb_my_footprint_is_check)
        CheckBox cbMyFootprintGroup;
        @BindView(R.id.tv_my_footprint_date)
        TextView tvMyFootprintDate;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ItemViewHolder {

        @BindView(R.id.img_my_footprint_pic)
        ImageView imgMyFootprintPic;
        @BindView(R.id.tv_my_footprint_name)
        TextView tvMyFootprintName;
        @BindView(R.id.tv_my_footprint_details)
        TextView tvMyFootprintDetails;
        @BindView(R.id.cb_my_footprint_item_is_check)
        CheckBox cbMyFootprintItemIsCheck;
        @BindView(R.id.vLine)
        View vLine;

        ItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void isShowAllCheckBox(boolean isShowAll) {
        this.isShowAllCheckBox = isShowAll;
        this.notifyDataSetChanged();
    }

}
