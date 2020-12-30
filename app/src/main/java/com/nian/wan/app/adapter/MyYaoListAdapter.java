package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.MyYaoqingBean;
import com.nian.wan.app.holder.MyYaoqingHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的邀请Adapter
 * Created by Administrator on 2017/2/9.
 */

public class MyYaoListAdapter extends BaseAdapter {

    private final Activity con;
    public List<MyYaoqingBean> list = new ArrayList<MyYaoqingBean>();

    public MyYaoListAdapter(Activity activity){
        this.con = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyYaoqingHolder holder = null;

        if (convertView == null) {
            holder = new MyYaoqingHolder();
        } else {
            holder = (MyYaoqingHolder) convertView.getTag();
        }
        MyYaoqingBean myYaoqingBean = list.get(position);
        holder.setData(myYaoqingBean,position,con);
        return holder.getContentView();
    }

    public void setList(List<MyYaoqingBean> b){
       this.list=b;
        notifyDataSetChanged();
    }

    public List<MyYaoqingBean> getList(){
        return list;
    }
}
