package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.FenBean;
import com.nian.wan.app.holder.FenHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏分类Adapter
 * Created by Administrator on 2017/4/22.
 */

public class FenAdapter extends BaseAdapter {

    private final Activity activity;
    private List<FenBean.GroupBean> fenBeen = new ArrayList<>();
    private int anInt = 666;

    public FenAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if(fenBeen==null){
            return 0;
        }else{
            return fenBeen.size();
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
        FenHolder gridHolder = null;
        if(view==null){
            gridHolder = new FenHolder();
        }else{
            gridHolder = (FenHolder)view.getTag();
        }
        FenBean.GroupBean groupBean = fenBeen.get(i);
        if(anInt==i){
            groupBean.isColor = true;
        }else{
            groupBean.isColor = false;
        }
        gridHolder.setData(groupBean,i,activity);
        return gridHolder.getContentView();
    }

    public void setList(List<FenBean.GroupBean> a){
        this.fenBeen = a;
        notifyDataSetChanged();
    }

    public void ShuaXin(int i) {
        anInt = i;
        notifyDataSetChanged();
    }
}
