package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.PointRecordBean;
import com.nian.wan.app.holder.PointRecordHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class PointRecordListAdapter extends BaseAdapter {
    private List<PointRecordBean> listData = new ArrayList<>();
    private Activity mActivity;
    private int type;

    public PointRecordListAdapter(Activity mActivity, int type) {
        this.mActivity = mActivity;
        this.type = type;
    }

    public List<PointRecordBean> getListData() {
        return listData;
    }

    public void setListData(List<PointRecordBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PointRecordHolder holder = null;
        if (convertView == null) {
            holder = new PointRecordHolder(type);
        } else {
            holder = (PointRecordHolder) convertView.getTag();
        }
        PointRecordBean bean = listData.get(position);
        holder.setData(bean, position, mActivity);
        return holder.getContentView();
    }
}
