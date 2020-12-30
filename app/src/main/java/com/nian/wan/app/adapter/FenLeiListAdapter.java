package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.GameTypeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class FenLeiListAdapter extends BaseAdapter {
    private final Activity activity;
    public List<GameInfo> list = new ArrayList<>();
    private boolean isH5Game;

    public FenLeiListAdapter(Activity activity,boolean isH5Game){
        this.activity = activity;
        this.isH5Game = isH5Game;
    }

    public void setList(List<GameInfo> list){
        this.list = list;
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
        GameTypeHolder holder = null;
        if (convertView == null){
            holder = new GameTypeHolder();
        }else {
            holder = (GameTypeHolder) convertView.getTag();
        }
        holder.setH5Game(isH5Game);
        holder.setData(list.get(position),position,activity);
        return holder.getContentView();
    }
}
