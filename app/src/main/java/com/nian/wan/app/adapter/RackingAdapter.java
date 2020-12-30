package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.RackingHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：排行榜适配器
 * 作者：闫冰
 * 时间: 2018-07-23 17:15
 */
public class RackingAdapter extends BaseAdapter {
    private Activity activity;
    private List<GameInfo> listData = new ArrayList<>();
    private List<RackingHolder> holderData = new ArrayList<>();
    private boolean isH5Game;

    public void setListData(List<GameInfo> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public RackingAdapter(Activity activity,boolean isH5Game) {
        this.activity = activity;
        this.isH5Game = isH5Game;
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
        RackingHolder rackingHolder = null;
        if (view == null){
            rackingHolder = new RackingHolder();
            holderData.add(rackingHolder);
        }else {
            rackingHolder = (RackingHolder) view.getTag();
        }
        rackingHolder.setH5Game(isH5Game);
        rackingHolder.setData(listData.get(i),i,activity);
        return rackingHolder.getContentView();
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
