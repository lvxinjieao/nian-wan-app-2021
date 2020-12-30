package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.MyOrderBean;
import com.nian.wan.app.holder.MyOrderHolder;

import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends BaseAdapter {

    private List<MyOrderBean> listData = new ArrayList<>();
    private Activity mActivity;

    public MyOrderAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public List<MyOrderBean> getListData() {
        return listData;
    }

    public void setListData(List<MyOrderBean> listData) {
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
        MyOrderHolder holder = null;
        if (convertView == null) {
            holder = new MyOrderHolder();
        } else {
            holder = (MyOrderHolder) convertView.getTag();
        }
        MyOrderBean bean = listData.get(position);
        holder.setData(bean, position, mActivity);
        return holder.getContentView();
    }
}
