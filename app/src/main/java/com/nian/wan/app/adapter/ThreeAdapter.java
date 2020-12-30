package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.HomeGameListBean;
import com.nian.wan.app.holder.New_Holder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 齐天大圣 on 2016/10/8.
 */

public class ThreeAdapter extends BaseAdapter {

    private final Activity activity;

    public ThreeAdapter(Activity activity){
        this.activity = activity;
    }
    public List<HomeGameListBean.MsgEntity> list = new ArrayList<>();


    public void setData(List<HomeGameListBean.MsgEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list.size()==0){
            return 0;
        }else{
            return list.size();
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
        New_Holder gridHolder = null;
        if(view==null){
            gridHolder = new New_Holder();
        }else{
            gridHolder = (New_Holder)view.getTag();
        }
        HomeGameListBean.MsgEntity homeGameListBean = list.get(i);
            gridHolder.setData(homeGameListBean,0,activity);
        return gridHolder.getContentView();
    }
}
