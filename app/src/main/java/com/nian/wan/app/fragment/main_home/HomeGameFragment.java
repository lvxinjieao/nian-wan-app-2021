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
import android.widget.LinearLayout;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.adapter.HomeHotRecyclerAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

;

/**
 * 描述：首页热门-手游Fragment
 */
public class HomeGameFragment extends Fragment {

    @BindView(R.id.listView)
    RecyclerView listView;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    //首页热门游戏适配器
    private HomeHotRecyclerAdapter hotRecyclerViewAdapter;

    private int pageNumber = 1;
    private List<GameInfo> appInfos = new ArrayList<>();

    private static final String TAG = "HomeGameFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_game_list, null);
        ButterKnife.bind(this, view);

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        hotRecyclerViewAdapter = new HomeHotRecyclerAdapter(getActivity());

        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(hotRecyclerViewAdapter);
        Aria.download(this).register();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loadGame();
        return view;
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {

        @Override
        public void onRefresh() {
            loadGame();
        }

        @Override
        public void onLoadmore() {
            onLoadMore();
        }

    };


    /**
     * 监听首页
     */
    @Subscribe
    public void eventMethod(EvenBean evenBean) {
        if (2 == evenBean.isExpandable) {
            springview.setFocusable(false);
            springview.setEnable(false);
        } else if (1 == evenBean.isExpandable) {
            springview.setFocusable(true);
            springview.setEnable(true);
        }

        if (evenBean.reFresh == 1) {
            loadGame();
        }
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
                    Log.e("首页-热门-手游", msg.obj.toString());
                    ArrayList<GameInfo> appInf = HttpUtils.homeGame(msg.obj.toString());
                    if (appInf != null && appInf.size() != 0) {
                        appInfos.addAll(appInf);
                        hotRecyclerViewAdapter.setData(appInfos);
                        springview.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    } else {
                        llNoData.setVisibility(View.VISIBLE);
                        springview.setVisibility(View.GONE);
                    }
                    break;

                case 2:
                    Utils.TS("网络缓慢");
                    break;
            }
        }
    };


    /**
     * 获取热门游戏
     */
    private void loadGame() {
        pageNumber = 1;
        appInfos.clear();
        Map<String, String> params = new HashMap<>();
        params.put("rec_status", "2");
        params.put("limit", "10");
        params.put("p", "1");
        params.put("sdk_version", "1");
        HttpConstant.POST(handler, HttpConstant.API_HOME_GAME, params, false);
    }

    /**
     * 加载更多
     */
    private void onLoadMore() {
        pageNumber++;
        HashMap<String, String> moreParams = new HashMap<>();
        moreParams.put("rec_status", "2");
        moreParams.put("limit", "10");
        moreParams.put("p", pageNumber + "");
        moreParams.put("sdk_version", "1");
        HttpConstant.POST(moreHandler, HttpConstant.API_HOME_GAME, moreParams, false);
    }

    @SuppressLint("HandlerLeak")
    Handler moreHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (springview != null) {
                springview.onFinishFreshAndLoad();
            }

            switch (msg.what) {
                case 1:
                    Log.e("首页-热门-手游，加载更多", msg.obj.toString());
                    ArrayList<GameInfo> appInf = HttpUtils.homeGame(msg.obj.toString());
                    if (appInf != null && appInf.size() != 0) {
                        appInfos.addAll(appInf);
                        hotRecyclerViewAdapter.setData(appInfos);
                        springview.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtil.showToast("已经到底了~");
                    }

                    break;
                case 2:
                    Utils.TS("网络缓慢");
                    break;
            }
        }
    };


    /**
     * 这个方法判断  游戏是否安装成功
     */
    @Override
    public void onResume() {
        super.onResume();
        if (appInfos.size() > 0) {
            hotRecyclerViewAdapter.ConfirmationState();
        }
    }

    @Download.onWait
    void taskWait(DownloadTask task) {
        hotRecyclerViewAdapter.taskWait(task, task.getKey());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        hotRecyclerViewAdapter.taskStop(task, task.getKey());
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        hotRecyclerViewAdapter.taskRuning(task, task.getKey());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        hotRecyclerViewAdapter.taskCancel(task, task.getKey());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        hotRecyclerViewAdapter.taskFail(task, task.getKey());
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        hotRecyclerViewAdapter.taskComplete(task, task.getKey());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
