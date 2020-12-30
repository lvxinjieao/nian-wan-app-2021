package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameGiftBeam;
import com.nian.wan.app.holder.GiftDetHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏详情libao
 * Created by Administrator on 2017/5/2.
 */

public class GiftDetListAdapter extends BaseAdapter {


    private Activity activity;
    private List<GameGiftBeam.MsgEntity.GiftEntity> giftBeenList = new ArrayList<>();

    public GiftDetListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return giftBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return giftBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GiftDetHolder holder = null;

        if (convertView == null) {
            holder = new GiftDetHolder();
        } else {
            holder = (GiftDetHolder) convertView.getTag();
        }
        GameGiftBeam.MsgEntity.GiftEntity gameGiftBeam = giftBeenList.get(position);
        holder.setData(gameGiftBeam, position, activity);
        return holder.getContentView();
    }

    public void setList(List<GameGiftBeam.MsgEntity.GiftEntity> list) {
        this.giftBeenList = list;
        notifyDataSetChanged();
    }

    public List<GameGiftBeam.MsgEntity.GiftEntity> getList() {
        return giftBeenList;
    }
}
