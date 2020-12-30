package com.nian.wan.app.fragment.main_home;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.adapter.BoutiqueGameAdapter;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.view.GridViews;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页--精品推荐
 */
public class BoutiqueGameFragment extends Fragment {

    @BindView(R.id.gridView)
    GridViews gridView;

    public BoutiqueGameAdapter adapter;

    public ArrayList<GameInfo> appInfos;
    public Activity activity;
    public int ye = 1;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    ArrayList<GameInfo> apps = HttpUtils.DNSJinp1(msg.obj.toString());
                    if (apps != null && apps.size() != 0) {
                        appInfos.addAll(apps);
                        adapter.setList(appInfos);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.boutique_game_layout, null);
        ButterKnife.bind(this, view);

        activity = getActivity();
        appInfos = new ArrayList<>();
        Aria.download(this).register();
        adapter = new BoutiqueGameAdapter(activity);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int game_id = appInfos.get(position).id;
                Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                intent.putExtra("game_id", game_id);
                startActivity(intent);
            }
        });

        initData();

        return view;
    }


    public void initData() {

        new AsyncTask<Handler,Void,Void>(){
            @Override
            protected Void doInBackground(Handler... objects) {
                ye = 1;
                appInfos.clear();
                HashMap<String, String> map = new HashMap<>();
                map.put("rec_status", "2");
                map.put("limit", "3");
                map.put("p", ye + "");
                map.put("sdk_version", "1");
                HttpConstant.POST(objects[0], HttpConstant.API_HOME_GAME, map, false);
                return null;
            }
        }.execute(handler);

//        ye = 1;
//        appInfos.clear();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("rec_status", "2");
//        map.put("limit", "3");
//        map.put("p", ye + "");
//        map.put("sdk_version", "1");
//        HttpCom.POST(handler, HttpCom.API_HOME_GAME, map, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (appInfos.size() != 0) {
            adapter.ConfirmationState();
        }
    }

    @Download.onWait
    void taskWait(DownloadTask task) {
        adapter.taskWait(task, task.getKey());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        adapter.taskStop(task, task.getKey());
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        adapter.taskRuning(task, task.getKey());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        adapter.taskCancel(task, task.getKey());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        adapter.taskFail(task, task.getKey());
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        adapter.taskComplete(task, task.getKey());
    }


}
