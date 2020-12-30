package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.BoutiqueGameHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;

public class BoutiqueGameAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<GameInfo> appInfos = new ArrayList<>();
    private ArrayList<BoutiqueGameHolder> holders = new ArrayList<>();


    public BoutiqueGameAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setList(ArrayList<GameInfo> apps) {
        this.appInfos = apps;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (appInfos.size() < 1 || appInfos == null) {
            return 0;
        } else if (appInfos.size() >= 3) {
            return 3;
        } else {
            return appInfos.size();
        }
    }


    @Override
    //指定的索引对应的数据项
    public Object getItem(int position) {
        return appInfos.get(position);
    }

    @Override
    //指定的索引对应的数据项ID
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoutiqueGameHolder holder = null;
        if (convertView == null) {
            holder = new BoutiqueGameHolder();
            holders.add(holder);
        } else {
            holder = (BoutiqueGameHolder) convertView.getTag();
        }

        GameInfo gameInfo = appInfos.get(position);
        holder.setData(gameInfo, position, activity);
        return holder.getContentView();
    }


    public void taskWait(DownloadTask task, String key) {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).taskWait(task, key, false);
        }
    }

    public void taskStop(DownloadTask task, String key) {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).taskStop(task, key, false);
        }
    }

    public void taskRuning(DownloadTask task, String key) {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).taskRuning(task, key, false);
        }
    }

    public void taskCancel(DownloadTask task, String key) {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).taskCancel(task, key, false);
        }
    }

    public void taskFail(DownloadTask task, String key) {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).taskFail(task, key, false);
        }
    }

    public void taskComplete(DownloadTask task, String key) {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).taskComplete(task, key, false);
        }
    }

    public void ConfirmationState() {
        for (int i = 0; i < holders.size(); i++) {
            holders.get(i).ConfirmationState();
        }
    }

}
