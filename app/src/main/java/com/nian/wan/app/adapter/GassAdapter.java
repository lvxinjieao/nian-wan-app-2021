package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GassBean;
import com.nian.wan.app.holder.GassAndPeoplePlayHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class GassAdapter extends BaseAdapter {
    private final Activity activity;
    public List<GassBean> list = new ArrayList<>();


    public GassAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setList(List<GassBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list.size() == 0) {
            return 0;
        } else {
            if (list.size() > 4) {
                return 4;
            } else {
                return list.size();
            }
        }
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
        GassAndPeoplePlayHolder gassAndPeoplePlayHolder = null;
        if (convertView == null) {
            gassAndPeoplePlayHolder = new GassAndPeoplePlayHolder();
        } else {
            gassAndPeoplePlayHolder = (GassAndPeoplePlayHolder) convertView.getTag();
        }
        GassBean listGass = list.get(position);
        gassAndPeoplePlayHolder.setData(listGass, position, activity);
        return gassAndPeoplePlayHolder.getContentView();
    }
}
