package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.HomeGameHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;

/**
 * 热门游戏adapter
 * Created by Administrator on 2017/4/22.
 */

public class HotAdapter extends BaseAdapter {

    private final Activity activity;
    private ArrayList<GameInfo> appInfos = new ArrayList<>();
    private ArrayList<HomeGameHolder> holderData = new ArrayList<>();

    public HotAdapter(Activity activity) {
        this.activity = activity;
    }


    @Override
    public int getCount() {
        if (appInfos.size() == 0) {
            return 0;
        } else {
            return appInfos.size();
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
        HomeGameHolder hot_holder = null;
        if (view == null) {
            hot_holder = new HomeGameHolder();
            holderData.add(hot_holder);
        } else {
            hot_holder = (HomeGameHolder) view.getTag();
        }
        GameInfo appInfo = appInfos.get(i);
        hot_holder.setData(appInfo, 0, activity);
        return hot_holder.getContentView();
    }

    public void setList(ArrayList<GameInfo> appInfos) {
        this.appInfos = appInfos;
        notifyDataSetChanged();
    }

    public void taskWait(DownloadTask task, String key) {
        for (int i = 0; i < holderData.size(); i++) {
            holderData.get(i).taskWait(task, key, false);
        }
    }

    public void taskStop(DownloadTask task, String key) {
        for (int i = 0; i < holderData.size(); i++) {
            holderData.get(i).taskStop(task, key, false);
        }
    }

    public void taskRuning(DownloadTask task, String key) {
        for (int i = 0; i < holderData.size(); i++) {
            holderData.get(i).taskRuning(task, key, false);
        }
    }

    public void taskCancel(DownloadTask task, String key) {
        for (int i = 0; i < holderData.size(); i++) {
            holderData.get(i).taskCancel(task, key, false);
        }
    }

    public void taskFail(DownloadTask task, String key) {
        for (int i = 0; i < holderData.size(); i++) {
            holderData.get(i).taskFail(task, key, false);
        }
    }

    public void taskComplete(DownloadTask task, String key) {
        for (int i = 0; i < holderData.size(); i++) {
            holderData.get(i).taskComplete(task, key, false);
        }
    }

    public void ConfirmationState() {
        for (int i = 0; i < holderData.size(); i++) {
            holderData.get(i).ConfirmationState();
        }
    }
}
