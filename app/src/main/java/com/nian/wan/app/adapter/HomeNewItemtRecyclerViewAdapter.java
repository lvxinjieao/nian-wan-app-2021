package com.nian.wan.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.NewItemtHolder;
import com.arialyy.aria.core.download.DownloadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/11
 * @Description: 首页新上架RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class HomeNewItemtRecyclerViewAdapter extends RecyclerView.Adapter<NewItemtHolder> {
    private List<GameInfo> homeGameBean = new ArrayList<>();
    private List<NewItemtHolder> HolderList = new ArrayList<>();
    private Activity activity;
    private boolean isH5Game;  //是否是H5游戏

    public HomeNewItemtRecyclerViewAdapter(Activity context, boolean isH5Game) {
        activity = context;
        this.isH5Game = isH5Game;
    }


    public void setData(List<GameInfo> homeGameBean) {
        this.homeGameBean = homeGameBean;
        this.notifyDataSetChanged();
    }

    @Override
    public NewItemtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gameholder_game_item, parent, false);
        NewItemtHolder viewHolder = new NewItemtHolder(view,activity,isH5Game);
        HolderList.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewItemtHolder holder, final int position) {
        final GameInfo gameInfo = homeGameBean.get(position);
        holder.setData(gameInfo);
    }

    @Override
    public int getItemCount() {
       return homeGameBean.size();
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
