package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.ZuiXin;
import com.nian.wan.app.holder.New_Holder;

import java.util.ArrayList;


/**
 * 游戏详情页Adapter
 * Created by 齐天大圣 on 2016/10/8.
 */

public class FourAdapter extends BaseAdapter {

    private final Activity activity;

    public FourAdapter(Activity activity){
        this.activity = activity;
    }
    public ArrayList<ZuiXin> list = new ArrayList<>();


    public void setData(ArrayList<ZuiXin> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list.size()==0){
            return 4;
        }else{
            return 4;
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
//        ZuiXin appInfo = list.get(i);
//            gridHolder.setData(appInfo,0,activity);
        return gridHolder.getContentView();
    }
}
