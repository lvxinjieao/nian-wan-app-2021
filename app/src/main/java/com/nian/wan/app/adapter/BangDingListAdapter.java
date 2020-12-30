package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.BangBiBean;
import com.nian.wan.app.holder.BangbiHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 绑定平台币
 * Created by Administrator on 2017/4/25.
 */

public class BangDingListAdapter extends BaseAdapter {

    private final Activity activity;
    private List<BangBiBean.MsgBean> strings = new ArrayList<>();

    public BangDingListAdapter(Activity a){
        activity = a;
    }

    @Override
    public int getCount() {
        return strings.size();
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
        BangbiHolder bangbiHolder = null;
        if(convertView==null){
            bangbiHolder = new BangbiHolder();
        }else{
            bangbiHolder = (BangbiHolder) convertView.getTag();
        }
        BangBiBean.MsgBean msgBean = strings.get(position);
        bangbiHolder.setData(msgBean,position,activity);
        return bangbiHolder.getContentView();
    }



    public void setList(List<BangBiBean.MsgBean> list){
        strings = list;
        notifyDataSetChanged();
    }
    public List<BangBiBean.MsgBean> getList(){
        return strings;
    }
}
