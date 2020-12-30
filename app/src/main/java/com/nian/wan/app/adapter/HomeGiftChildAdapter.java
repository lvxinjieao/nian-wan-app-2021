package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.HomeGiftBean;
import com.nian.wan.app.holder.HomeGiftChildHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeGiftChildAdapter extends BaseAdapter {
    private Activity activity;
    private List<HomeGiftBean.GblistBean> listData = new ArrayList<>();

    public HomeGiftChildAdapter(Activity activity) {
        this.activity = activity;
    }

    public List<HomeGiftBean.GblistBean> getData() {
        return listData;
    }

    public void setData(List<HomeGiftBean.GblistBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (listData.size()>0){
            return listData.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HomeGiftChildHolder homeGiftChildHolder =null;
        if (view == null){
            homeGiftChildHolder = new HomeGiftChildHolder();
        }else {
            homeGiftChildHolder = (HomeGiftChildHolder) view.getTag();
        }
        homeGiftChildHolder.setData(listData.get(i),i,activity);
        return homeGiftChildHolder.getContentView();
    }
}
