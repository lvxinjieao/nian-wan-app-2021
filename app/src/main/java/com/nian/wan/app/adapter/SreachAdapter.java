package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.SearchGameBean;
import com.nian.wan.app.holder.SreachListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class SreachAdapter extends BaseAdapter {
    private final Activity act;
    private List<SearchGameBean.MsgEntity> gamelist = new ArrayList<>();

    public SreachAdapter(Activity activity){
        this.act = activity;
    }

    public void setList(List<SearchGameBean.MsgEntity> list){
        this.gamelist = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gamelist.size();
    }

    @Override
    public Object getItem(int position) {
        return gamelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SreachListHolder holder = null;
        if (convertView == null){
            holder = new SreachListHolder();
        }else {
            holder = (SreachListHolder) convertView.getTag();
        }
        SearchGameBean.MsgEntity msgEntity = gamelist.get(position);
        holder.setData(msgEntity,position,act);

        return holder.getContentView();
    }

    public List<SearchGameBean.MsgEntity> getList(){
        return gamelist;
    }

}
