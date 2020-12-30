package com.nian.wan.app.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.MyZuJiBean;
import com.nian.wan.app.holder.ZujiHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * 足迹Adapter
 * Created by Administrator on 2017/5/4.
 */

public class FootprintAdapter extends BaseAdapter {
    private final  Activity activity;
    private List<ZujiHolder> mHolders = new ArrayList<>();
    private List<MyZuJiBean.MsgEntity> listDate = new ArrayList<>();

    public FootprintAdapter(List<MyZuJiBean.MsgEntity> listDate, Activity activity){
        this.activity = activity;
        this.listDate = listDate;
    }

    public void setList(List<MyZuJiBean.MsgEntity> list){
        this.listDate = list;
    }

    @Override
    public int getCount() {
        return listDate.size();
    }

    @Override
    public Object getItem(int position) {
        return listDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ZujiHolder holder = null;
        if(convertView==null){
            holder = new ZujiHolder();
            Log.e("数值","执行了");
            mHolders.add(holder);
        }else{
            holder = (ZujiHolder)convertView.getTag();
            Log.e("数值","没执行");
        }
        MyZuJiBean.MsgEntity listZuji = listDate.get(position);
        holder.setData(listZuji,position, activity);
        return holder.getContentView();
    }


    public void yin(){
        ListIterator<ZujiHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext()) {
            ZujiHolder holder = iterator.next();
            holder.hide();
        }
    }
    public void xian(){
        ListIterator<ZujiHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext()) {
            ZujiHolder holder = iterator.next();
            holder.display();
        }
    }

    public void setZhi(boolean a){
        ListIterator<ZujiHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext()) {
            ZujiHolder holder = iterator.next();
            holder.setBu(a);
        }
    }

}
