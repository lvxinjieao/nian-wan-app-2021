package com.nian.wan.app.fragment.main_home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.HomeNewItemtRecyclerViewAdapter;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.http.HttpConstant;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import android.widget.LinearLayout;;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页新上架
 */
public class HomeNewItemsFragment extends Fragment {

    @BindView(R.id.rc_home_new_item_list)
    RecyclerView mRcHomeNewItemList;

    //  首页新上架游戏适配器
    private HomeNewItemtRecyclerViewAdapter homeNewItemtRecyclerViewAdapter;
    private List<GameInfo> listData = new ArrayList<>();

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    private int pageNumber = 1;
    private int barLayoutState;
    private boolean isH5Game;

    public void setH5Game(boolean h5Game) {
        isH5Game = h5Game;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.home_new_item_list, null);
        ButterKnife.bind(this, view);

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(getActivity()));
        springview.setFooter(new SimpleFooter(getActivity()));

        refresh();

        homeNewItemtRecyclerViewAdapter = new HomeNewItemtRecyclerViewAdapter(getActivity(), isH5Game);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRcHomeNewItemList.setLayoutManager(linearLayoutManager);
        mRcHomeNewItemList.setAdapter(homeNewItemtRecyclerViewAdapter);

        Aria.download(this).register();
        return view;
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            refresh();
        }

        @Override
        public void onLoadmore() {
            loadMore();
        }

    };


    /**
     * 获取新上架游戏
     */
    private void refresh() {
        pageNumber = 1;
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("rec_status", "3");
        hotGameParams.put("limit", "10");
        hotGameParams.put("order", "id desc");
        hotGameParams.put("p", "1");
        if (isH5Game) {
            hotGameParams.put("sdk_version", "3");
        } else {
            hotGameParams.put("sdk_version", "1");
        }
        HttpConstant.POST(handler, HttpConstant.API_HOME_GAME, hotGameParams, false);
    }


    /**
     * 更多新上架游戏
     */
    private void loadMore() {
        pageNumber++;
        HashMap<String, String> loadMoreParams = new HashMap<>();
        loadMoreParams.put("rec_status", "3");
        loadMoreParams.put("limit", "10");
        loadMoreParams.put("order", "id desc");
        loadMoreParams.put("p", pageNumber + "");
        if (isH5Game) {
            loadMoreParams.put("sdk_version", "3");
        } else {
            loadMoreParams.put("sdk_version", "1");
        }
        HttpConstant.POST(maoreHandler, HttpConstant.API_HOME_GAME, loadMoreParams, false);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springview != null) {
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("新上架", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        String message = jsonObject.getString("msg");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (code == 200) {
                            listData.clear();
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    springview.setVisibility(View.VISIBLE);
                                    llNoData.setVisibility(View.GONE);
                                    JSONObject jsonData = jsonArray.getJSONObject(i);
                                    GameInfo gameInfo = new GameInfo();
                                    gameInfo.iconurl = jsonData.getString("icon");
                                    gameInfo.name = jsonData.getString("game_name");
                                    gameInfo.id = jsonData.getInt("id");
                                    gameInfo.features = jsonData.getString("features");
                                    gameInfo.type = jsonData.getString("game_type_name");
                                    gameInfo.playNum = jsonData.getString("play_num");
                                    gameInfo.GameUrl = jsonData.getString("play_url");
                                    gameInfo.fanli = jsonData.getString("ratio");
                                    gameInfo.gift = jsonData.getInt("gift_id");
                                    gameInfo.size = jsonData.getString("game_size");
                                    gameInfo.canDownload = jsonData.getInt("xia_status");
                                    listData.add(gameInfo);
                                }
                                homeNewItemtRecyclerViewAdapter.setData(listData);
                            } else {
                                springview.setVisibility(View.GONE);
                                llNoData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            ToastUtil.showToast(message);
                        }
                    } catch (Exception e) {
                        Log.e("获取新上架数据异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };


    @SuppressLint("HandlerLeak")
    Handler maoreHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springview != null) {
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("更多新上架", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        String message = jsonObject.getString("msg");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (code == 200) {
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    springview.setVisibility(View.VISIBLE);
                                    llNoData.setVisibility(View.GONE);
                                    JSONObject jsonData = jsonArray.getJSONObject(i);
                                    GameInfo gameInfo = new GameInfo();
                                    gameInfo.iconurl = jsonData.getString("icon");
                                    gameInfo.name = jsonData.getString("game_name");
                                    gameInfo.id = jsonData.getInt("id");
                                    gameInfo.features = jsonData.getString("features");
                                    gameInfo.type = jsonData.getString("game_type_name");
                                    gameInfo.playNum = jsonData.getString("play_num");
                                    gameInfo.GameUrl = jsonData.getString("play_url");
                                    gameInfo.fanli = jsonData.getString("ratio");
                                    gameInfo.gift = jsonData.getInt("gift_id");
                                    gameInfo.canDownload = jsonData.getInt("xia_status");
                                    listData.add(gameInfo);
                                }
                                homeNewItemtRecyclerViewAdapter.setData(listData);
                            } else {
                                ToastUtil.showToast("已经到底了~");
                            }
                        } else {
                            ToastUtil.showToast(message);
                        }
                    } catch (Exception e) {
                        Log.e("更多新上架异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if (listData.size() != 0) {
            homeNewItemtRecyclerViewAdapter.ConfirmationState();
        }
    }

    @Download.onWait
    void taskWait(DownloadTask task) {
        homeNewItemtRecyclerViewAdapter.taskWait(task, task.getKey());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        homeNewItemtRecyclerViewAdapter.taskStop(task, task.getKey());
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        homeNewItemtRecyclerViewAdapter.taskRuning(task, task.getKey());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        homeNewItemtRecyclerViewAdapter.taskCancel(task, task.getKey());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        homeNewItemtRecyclerViewAdapter.taskFail(task, task.getKey());
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        homeNewItemtRecyclerViewAdapter.taskComplete(task, task.getKey());
    }
}
