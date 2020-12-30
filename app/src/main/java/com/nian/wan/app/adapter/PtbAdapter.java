package com.nian.wan.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.PtbBean;
import com.nian.wan.app.holder.Ptb_Holder;

import java.util.List;

/**
 * Created by LeBron on 2016/10/8.
 */

public class PtbAdapter extends BaseAdapter{

private List<PtbBean> giftBeanList;

    private Context context;
    public PtbAdapter(List<PtbBean> giftBeanList, Context context) {
        this.giftBeanList = giftBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return giftBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return giftBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ptb_Holder holder=null;

        if (convertView == null) {
            holder=new Ptb_Holder();
        } else {
            holder = (Ptb_Holder) convertView.getTag();
        }
        PtbBean giftBean = giftBeanList.get(position);
        holder.setData(giftBean,position,null);
        return holder.getContentView();
    }
}
