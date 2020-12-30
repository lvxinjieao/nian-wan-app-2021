package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.AppInfo;
import com.nian.wan.app.holder.GassHolder;

import java.util.ArrayList;

/**
 * Created by 齐天大圣 on 2016/10/10.
 */

public class FiveAdapter extends BaseAdapter {

    private final Activity activity;
    public ArrayList<AppInfo> list = new ArrayList<AppInfo>();

    public FiveAdapter(Activity activity){
        this.activity = activity;
    }
    public void setData(ArrayList<AppInfo> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list.size()==0){
            return 0;
        }else{
            if(list.size()>4){
                return 4;
            }else{
                return list.size();
            }
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
        GassHolder hot_holder = null;
        if(view==null){
            hot_holder = new GassHolder();
        }else{
            hot_holder = (GassHolder)view.getTag();
        }
        AppInfo appInfo = list.get(i);
        hot_holder.setData(appInfo,i,activity);
        return hot_holder.getContentView();
    }
}
