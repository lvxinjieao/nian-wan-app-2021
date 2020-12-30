package com.nian.wan.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.RulesBean;
import com.nian.wan.app.holder.GuiZeHolder;

import java.util.ArrayList;

/**
 * 描述：规则Adapter
 * 作者：钮家齐
 * 时间: 2018-01-19 16:55
 */
public class GuiZeAdapter extends BaseAdapter {

    private ArrayList<RulesBean> guiZeBeans1 = new ArrayList<>();

    public GuiZeAdapter(ArrayList<RulesBean> guiZeBeans){
        this.guiZeBeans1 = guiZeBeans;
    }

    @Override
    public int getCount() {
        return guiZeBeans1.size();
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
        GuiZeHolder guiZeHolder = null;
//        if(view==null){
            guiZeHolder = new GuiZeHolder();
//        }else{
//            guiZeHolder = (GuiZeHolder)view.getTag();
//        }
        guiZeHolder.setData(guiZeBeans1.get(i),i,null);
        return guiZeHolder.getContentView();
    }
}
