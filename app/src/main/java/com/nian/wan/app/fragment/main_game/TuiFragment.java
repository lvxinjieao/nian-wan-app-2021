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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.adapter.TuiAdapter;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

;

/**
 * 游戏推荐Fragment
 */
@SuppressLint("ValidFragment")
public class TuiFragment extends Fragment {

    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.springview)
    SpringView springview;

    @BindView(R.id.ll_no_data)
    LinearLayout wu;

    private TuiAdapter tuiAdapter;
    private Activity con;
    public int ye = 1;
    private ArrayList<GameInfo> appInfos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tui, null);
        ButterKnife.bind(this, view);
        Reash();
        con = getActivity();
        initdata();
        Aria.download(this).register();
        return view;
    }


    private void initdata() {
        tuiAdapter = new TuiAdapter(getActivity());
        listview.setAdapter(tuiAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("game_id", appInfos.get(i).id);
                intent.setClass(con, GameDetailActivity.class);
                con.startActivity(intent);
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

    //刷新
    public void Reash() {
        ye = 1;
        appInfos.clear();
        HashMap<String, String> map = new HashMap<>();
        map.put("rec_status", "1");
        map.put("p", ye + "");
        map.put("sdk_version", "1");
        HttpConstant.POST(bhandler, HttpConstant.API_HOME_GAME, map, false);
    }

    //加载
    public void onLoad() {
        ye = ++ye;
        HashMap<String, String> map = new HashMap<>();
        map.put("rec_status", "1");
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
                    Log.e("游戏-推荐", msg.obj.toString());
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
        tuiAdapter.taskWait(task, task.getKey());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        tuiAdapter.taskStop(task, task.getKey());
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        tuiAdapter.taskRuning(task, task.getKey());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        tuiAdapter.taskCancel(task, task.getKey());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        tuiAdapter.taskFail(task, task.getKey());
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        tuiAdapter.taskComplete(task, task.getKey());
    }
}
