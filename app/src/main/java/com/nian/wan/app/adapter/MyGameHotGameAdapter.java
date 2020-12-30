package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.MyGameHotGameHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;

/**
 * 推荐游戏adapter
 */
public class MyGameHotGameAdapter extends BaseAdapter {

    private final Activity activity;
    private ArrayList<GameInfo> appInfos = new ArrayList<>();
    private ArrayList<MyGameHotGameHolder> holderData = new ArrayList<>();

    public MyGameHotGameAdapter(Activity activity){
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
        MyGameHotGameHolder hot_holder = null;
        if (view == null) {
            hot_holder = new MyGameHotGameHolder();
            holderData.add(hot_holder);
        } else {
            hot_holder = (MyGameHotGameHolder) view.getTag();
        }
        GameInfo appInfo = appInfos.get(i);
        if (appInfo.sdk_version == 3){
            hot_holder.setIsH5Game(true);
        }else {
            hot_holder.setIsH5Game(false);
        }
        hot_holder.setData(appInfo, i, activity);
        return hot_holder.getContentView();
    }

    public void setList(ArrayList<GameInfo> appInfos){
        this.appInfos = appInfos;
        notifyDataSetChanged();
    }

    public ArrayList<GameInfo> getList() {
        return appInfos;
    }

    public void taskWait(DownloadTask task, String key) {
        for (int i = 0;i<holderData.size();i++){
            holderData.get(i).taskWait(task,key,false);
        }
    }

    public void taskStop(DownloadTask task, String key) {
        for (int i = 0;i<holderData.size();i++){
            holderData.get(i).taskStop(task,key,false);
        }
    }

    public void taskRuning(DownloadTask task, String key) {
        for (int i = 0;i<holderData.size();i++){
            holderData.get(i).taskRuning(task,key,false);
        }
    }

    public void taskCancel(DownloadTask task, String key) {
        for (int i = 0;i<holderData.size();i++){
            holderData.get(i).taskCancel(task,key,false);
        }
    }

    public void taskFail(DownloadTask task, String key) {
        for (int i = 0;i<holderData.size();i++){
            holderData.get(i).taskFail(task,key,false);
        }
    }

    public void taskComplete(DownloadTask task, String key) {
        for (int i = 0;i<holderData.size();i++){
            holderData.get(i).taskComplete(task,key,false);
        }
    }

    public void ConfirmationState() {
        for (int i = 0;i<holderData.size();i++){
            holderData.get(i).ConfirmationState();
        }
    }
}
