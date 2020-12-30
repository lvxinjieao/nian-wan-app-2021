package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.HomeGameListBean;
import com.nian.wan.app.holder.JingPin_Holder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 齐天大圣 on 2016/10/8.
 */

public class OneAdapter extends BaseAdapter {

    private final Activity activity;
    public List<HomeGameListBean.MsgEntity> list = new ArrayList<>();

    public OneAdapter(Activity activity){
        this.activity = activity;
    }

    public void setData(List<HomeGameListBean.MsgEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void getDate(){

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        JingPin_Holder gridHolder = null;
        if(view==null){
            gridHolder = new JingPin_Holder();
        }else{
            gridHolder = (JingPin_Holder)view.getTag();
        }
            HomeGameListBean.MsgEntity homegamelist = list.get(i);
            gridHolder.setData(homegamelist,i,activity);
        return gridHolder.getContentView();
    }
}
