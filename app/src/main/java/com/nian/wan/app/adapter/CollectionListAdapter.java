package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.ShouCang;
import com.nian.wan.app.holder.CollectionHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 收藏Adapter
 */
public class CollectionListAdapter extends BaseAdapter {
    private final Activity activity;
    private List<CollectionHolder> mHolders = new LinkedList<>();
    private List<ShouCang.MsgBean.OneMonthBean> list = new ArrayList<>();


    public CollectionListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollectionHolder collectionHolder = null;
        if (convertView == null) {
            collectionHolder = new CollectionHolder();
            mHolders.add(collectionHolder);
        } else {
            collectionHolder = (CollectionHolder) convertView.getTag();
        }
        ShouCang.MsgBean.OneMonthBean oneMonthBean = list.get(position);
        collectionHolder.setData(oneMonthBean, position, activity);
        return collectionHolder.getContentView();
    }

    public void yin() {
        ListIterator<CollectionHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext()) {
            CollectionHolder holder = iterator.next();
            holder.hide();
        }
    }

    public void xian() {
        ListIterator<CollectionHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext()) {
            CollectionHolder holder = iterator.next();
            holder.display();
        }
    }

    public void setZhi(boolean a) {
        for (int i = list.size(); i < list.size() * 2; i++) {
            CollectionHolder collectionHolder = mHolders.get(i);
            collectionHolder.setBu(a);
        }
    }

    public void setList(ArrayList<ShouCang.MsgBean.OneMonthBean> a) {
        this.list = a;
        notifyDataSetChanged();
    }
}
