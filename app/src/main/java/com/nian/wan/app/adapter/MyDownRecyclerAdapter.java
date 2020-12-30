package com.nian.wan.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.MyDownDateBean;
import com.nian.wan.app.holder.MyGameHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述：我的游戏条目适配器
 * 作者：钮家齐
 * 时间: 2018-08-10 17:26
 */
public class MyDownRecyclerAdapter extends RecyclerView.Adapter<MyGameHolder>{
    private Activity activity;
    private List<MyDownDateBean> gameInfos = new ArrayList<>();
    private ArrayList<MyGameHolder> HolderList = new ArrayList<>();
    private boolean isH5Game;

    public MyDownRecyclerAdapter(Activity activity, boolean isH5Game) {
        this.activity = activity;
        this.isH5Game = isH5Game;
    }

    public void setData(List<MyDownDateBean> gameInfo) {
        this.gameInfos = gameInfo;
        notifyDataSetChanged();
    }

    @Override
    public MyGameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_down_game_item, parent, false);
        MyGameHolder HotHolder = new MyGameHolder(view,activity,isH5Game);
        HolderList.add(HotHolder);
        return HotHolder;
    }

    @Override
    public void onBindViewHolder(MyGameHolder holder, int position) {
        MyDownDateBean downloadEntity = gameInfos.get(position);
        holder.setData(downloadEntity);
    }

    @Override
    public int getItemCount() {
        return gameInfos.size();
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

    public void deleteItem(){
        notifyDataSetChanged();
    }
}
