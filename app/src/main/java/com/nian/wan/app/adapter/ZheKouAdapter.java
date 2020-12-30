package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.ZheKouHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：折扣专区列表Adapter
 * 作者：闫冰
 * 时间: 2018-07-19 20:38
 */
public class ZheKouAdapter extends BaseAdapter{

    private Activity activity;
    private List<GameInfo> listData = new ArrayList<>();
    private List<ZheKouHolder> holderData = new ArrayList<>();

    public ZheKouAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListData(List<GameInfo> listData) {
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
        ZheKouHolder holder =null;
        if (view == null){
            holder = new ZheKouHolder();
            holderData.add(holder);
        }else {
            holder = (ZheKouHolder) view.getTag();
        }

        holder.setData(listData.get(i),i,activity);
        return holder.getContentView();
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
