package com.nian.wan.app.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.holder.GameTypeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/11
 * @Description: 首页热门RecyclerView适配器
 * @Modify By:
 * @ModifyDate:
 */
public class GameTypeAdapter extends BaseAdapter {
    private Activity activity;
    private List<GameInfo> gameInfos = new ArrayList<>();
    private boolean isH5Game;


    public GameTypeAdapter(Activity activity, boolean isH5Game) {
        this.activity = activity;
        this.isH5Game = isH5Game;
    }

    public void setData(List<GameInfo> gameInfos) {
        this.gameInfos = gameInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gameInfos.size();
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
        GameTypeHolder HotHolder = null;
        if (view == null){
            HotHolder = new GameTypeHolder();
        }else {
            HotHolder = (GameTypeHolder) view.getTag();
        }
        HotHolder.setH5Game(isH5Game);
        HotHolder.setData(gameInfos.get(i),i,activity);
        return HotHolder.getContentView();
    }

}
