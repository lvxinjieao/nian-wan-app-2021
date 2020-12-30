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
 * @Description: 全局搜索游戏RecyclerView适配器
 */
public class SearchGameRecyclerViewAdapter extends RecyclerView.Adapter<HomeHotHolder> {


    private List<GameInfo> gameInfos = new ArrayList<>();
    private Activity activity;
    private ArrayList<HomeHotHolder> HolderList = new ArrayList<>();
    private boolean xian = true;


    public SearchGameRecyclerViewAdapter(List<GameInfo> gameInfos, Activity context) {
        this.gameInfos = gameInfos;
        activity = context;
    }

    @Override
    public HomeHotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gameholder_home_item, parent, false);
        HomeHotHolder HotHolder = new HomeHotHolder(view,activity);
        HolderList.add(HotHolder);
        return HotHolder;
    }

    @Override
    public void onBindViewHolder(HomeHotHolder holder, int position) {
        final GameInfo gameInfo = gameInfos.get(position);
//        if (gameInfo.sdk_version == 3){
//            holder.setData(gameInfo,true);
//        }else {
            holder.setData(gameInfo);
//        }
    }

    @Override
    public int getItemCount() {
        if(gameInfos != null){
            if(gameInfos.size() >=3 && xian){
                return 3;
            }else{
                return gameInfos.size();
            }
        }
        return 0;
    }


    public void setXian(boolean b){
        xian = b;
        notifyDataSetChanged();
    }

    public void ConfirmationState() {
        for (int i = 0;i<HolderList.size();i++){
            HolderList.get(i).ConfirmationState();
        }
    }

    public void taskWait(DownloadTask task, String key) {
        for (int i = 0;i<HolderList.size();i++){
            HolderList.get(i).taskWait(task,key,false);
        }
    }

    public void taskStop(DownloadTask task, String key) {
        for (int i = 0;i<HolderList.size();i++){
            HolderList.get(i).taskStop(task,key,false);
        }
    }

    public void taskCancel(DownloadTask task, String key) {
        for (int i = 0;i<HolderList.size();i++){
            HolderList.get(i).taskCancel(task,key,false);
        }
    }

    public void taskFail(DownloadTask task, String key) {
        for (int i = 0;i<HolderList.size();i++){
            HolderList.get(i).taskFail(task,key,false);
        }
    }

    public void taskComplete(DownloadTask task, String key) {
        for (int i = 0;i<HolderList.size();i++){
            HolderList.get(i).taskComplete(task,key,false);
        }
    }

    public void taskRuning(DownloadTask task, String key) {
        for (int i = 0;i<HolderList.size();i++){
            HolderList.get(i).taskRuning(task,key,false);
        }
    }

}