package com.nian.wan.app.fragment.main_game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.adapter.HotAdapter;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import android.widget.LinearLayout;;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class HotFragment extends Fragment {

    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.springview)
    SpringView springview;

    @BindView(R.id.ll_no_data)
    LinearLayout wu;

    private HotAdapter tuiAdapter;
    private Activity conte;
    private ArrayList<GameInfo> appInfos = new ArrayList<>();
    public int ye = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_hot, null);
        ButterKnife.bind(this, view);
        Reash();
        conte = getActivity();
        initData();
        Aria.download(this).register();
        return view;
    }

    private void initData() {
        appInfos = new ArrayList<>();
        tuiAdapter = new HotAdapter(getActivity());
        listview.setAdapter(tuiAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("game_id", appInfos.get(i).id);
                intent.setClass(conte, GameDetailActivity.class);
                conte.startActivity(intent);
            }
        });

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(getActivity()));
        springview.setFooter(new SimpleFooter(getActivity()));
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            Reash();
        }

        @Override
        public void onLoadmore() {
            onLoad();
        }
    };

    public void Reash() {
        ye = 1;
        appInfos.clear();
        HashMap<String, String> map = new HashMap<>();
        map.put("rec_status", "2");
        map.put("p", ye + "");
        map.put("sdk_version", "1");
        HttpConstant.POST(bhandler, HttpConstant.API_HOME_GAME, map, false);
    }

    public void onLoad() {
        ye = ++ye;
        HashMap<String, String> map = new HashMap<>();
        map.put("rec_status", "2");
        map.put("p", ye + "");
        map.put("sdk_version", "1");
        HttpConstant.POST(handler, HttpConstant.API_HOME_GAME, map, false);
    }


    /**
     * 加载更多
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springview != null) {
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    Log.e("游戏-热门，加载更多", msg.obj.toString());
                    ArrayList<GameInfo> appInf = HttpUtils.DNSJinp1(msg.obj.toString());
                    if (appInf != null && appInf.size() != 0) {
                        appInfos.addAll(appInf);
                        tuiAdapter.setList(appInfos);
                        springview.setVisibility(View.VISIBLE);
                        wu.setVisibility(View.GONE);
                    } else {
                        ToastUtil.showToast("已经到底了~");
                    }
                    break;
                case 2:
                    // Utils.TS("网络异常");
                    break;
            }
        }
    };

    /**
     * 刚进来
     */
    @SuppressLint("HandlerLeak")
    Handler bhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springview != null) {
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    Log.e("游戏-热门", msg.obj.toString());
                    ArrayList<GameInfo> appInf = HttpUtils.DNSJinp1(msg.obj.toString());
                    if (appInf != null && appInf.size() != 0) {
                        appInfos.addAll(appInf);
                        tuiAdapter.setList(appInfos);
                        springview.setVisibility(View.VISIBLE);
                        wu.setVisibility(View.GONE);
                    } else {
                        springview.setVisibility(View.GONE);
                        wu.setVisibility(View.VISIBLE);
                    }

                    break;
                case 2:
                    //Utils.TS("网络异常");
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Reash();
        if (appInfos.size() != 0) {
            tuiAdapter.ConfirmationState();
        }
    }

    @Download.onWait
    void taskWait(DownloadTask task) {
        Log.e("下载状态hot", "----taskWait");
        tuiAdapter.taskWait(task, task.getKey());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        Log.e("下载状态hot", "----taskStop");
        tuiAdapter.taskStop(task, task.getKey());
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        Log.e("下载状态hot", "----taskRuning");
        tuiAdapter.taskRuning(task, task.getKey());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        Log.e("下载状态hot", "----taskCancel");
        tuiAdapter.taskCancel(task, task.getKey());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        Log.e("下载状态hot", "----taskFail");
        tuiAdapter.taskFail(task, task.getKey());
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        Log.e("下载状态hot", "----taskComplete");
        tuiAdapter.taskComplete(task, task.getKey());
    }


}
