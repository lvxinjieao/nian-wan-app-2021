package com.nian.wan.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.HomeHotHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述：首页游戏条目适配器
 */
public class HomeHotRecyclerAdapter extends RecyclerView.Adapter<HomeHotHolder> {

    private Activity activity;
    private List<GameInfo> gameInfos = new ArrayList<>();
    private ArrayList<HomeHotHolder> HolderList = new ArrayList<>();

    public HomeHotRecyclerAdapter(Activity activity) {
        this.activity = activity;
    }


    public void setData(List<GameInfo> gameInfos) {
        this.gameInfos = gameInfos;
        notifyDataSetChanged();
    }

    @Override
    public HomeHotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gameholder_home_item, parent, false);
        HomeHotHolder HotHolder = new HomeHotHolder(view, activity);
        HolderList.add(HotHolder);
        return HotHolder;
    }

    @Override
    public void onBindViewHolder(HomeHotHolder holder, int position) {
        final GameInfo gameInfo = gameInfos.get(position);
        holder.setData(gameInfo);
    }

    @Override
    public int getItemCount() {
        return gameInfos.size();
    }


    public void ConfirmationState() {
        for (int i = 0; i < HolderList.size(); i++) {
            HolderList.get(i).ConfirmationState();
        }
    }

    public void taskWait(DownloadTask task, String key) {
        for (int i = 0; i < HolderList.size(); i++) {
            HolderList.get(i).taskWait(task, key, false);
        }
    }

    public void taskStop(DownloadTask task, String key) {
        for (int i = 0; i < HolderList.size(); i++) {
            HolderList.get(i).taskStop(task, key, false);
        }
    }

    public void taskCancel(DownloadTask task, String key) {
        for (int i = 0; i < HolderList.size(); i++) {
            HolderList.get(i).taskCancel(task, key, false);
        }
    }

    public void taskFail(DownloadTask task, String key) {
        for (int i = 0; i < HolderList.size(); i++) {
            HolderList.get(i).taskFail(task, key, false);
        }
    }

    public void taskComplete(DownloadTask task, String key) {
        for (int i = 0; i < HolderList.size(); i++) {
            HolderList.get(i).taskComplete(task, key, false);
        }
    }

    public void taskRuning(DownloadTask task, String key) {
        for (int i = 0; i < HolderList.size(); i++) {
            HolderList.get(i).taskRuning(task, key, false);
        }
    }
}
