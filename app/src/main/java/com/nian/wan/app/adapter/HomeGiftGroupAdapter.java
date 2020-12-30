package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.HomeGiftBean;
import com.nian.wan.app.holder.HomeGiftGroupHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述：手游礼包
 * 作者：闫冰
 * 时间: 2018-09-29 19:47
 */
public class HomeGiftGroupAdapter extends BaseAdapter {
    private Activity activity;
    private List<HomeGiftBean> listData = new ArrayList<>();

    public HomeGiftGroupAdapter(Activity activity) {
        this.activity = activity;
    }

    public List<HomeGiftBean> getData() {
        return listData;
    }

    public void setData(List<HomeGiftBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listData.size();
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
        HomeGiftGroupHolder homeGiftGroupHolder =null;
        if (view == null){
            homeGiftGroupHolder = new HomeGiftGroupHolder();
        }else {
            homeGiftGroupHolder = (HomeGiftGroupHolder) view.getTag();
        }
        homeGiftGroupHolder.setData(listData.get(i),i,activity);
        return homeGiftGroupHolder.getContentView();
    }
}
