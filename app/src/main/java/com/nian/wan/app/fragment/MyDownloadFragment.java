package com.nian.wan.app.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadTask;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.adapter.MyCollectionRecommendRecyclerAdapter;
import com.nian.wan.app.adapter.MyDownRecyclerAdapter;
import com.nian.wan.app.adapter.MyGameHotGameAdapter;
import com.nian.wan.app.bean.DeleteFootBean;
import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.EvenBusBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.bean.MyCollectionBean;
import com.nian.wan.app.bean.MyCollectionHotGameBean;
import com.nian.wan.app.bean.MyDownDateBean;
import com.nian.wan.app.bean.SingleHotGameBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

;

/**
 * 描述：我的下载Fragment
 */
public class MyDownloadFragment extends Fragment {

    @BindView(R.id.rc_my_download_list)
    RecyclerView rcMyDownloadList;

    @BindView(R.id.springView)
    SpringView springView;

    @BindView(R.id.rl_my_download_none)
    RelativeLayout rlMyDownloadNone;

    @BindView(R.id.img_my_download_more_hot_game)
    LinearLayout imgMyDownloadMoreHotGame;

    @BindView(R.id.rc_my_download_hot_game)
    ListView rcMyDownloadHotGame;

    @BindView(R.id.ll_my_download_hot_game)
    LinearLayout llMyDownloadHotGame;

    //下载的游戏列表适配器
    private MyDownRecyclerAdapter hotRecyclerViewAdapter;
    //下载的游戏列表数据
    private List<GameInfo> appInfos = new ArrayList<>();
    //最新游戏适配器
    private MyCollectionRecommendRecyclerAdapter myCollectionRecommendRecyclerAdapter;
    //最新游戏数据源
    private List<MyCollectionHotGameBean> mHotGameBeen;
    private MyCollectionBean myCollectionBean;
    private ArrayList<SingleHotGameBean> singleHotGameBean;
    private DeleteFootBean deleteFootBean;
    private int limit = 1;
    public static final int NEED_REFRESH = 0x01;
    //===========================================分割线================================================
    List<MyDownDateBean> list = new ArrayList<>();
    List<MyDownDateBean> bendilist = new ArrayList<>();
    private DbManager db;
    private int page = 1;


    private MyGameHotGameAdapter hotGameAdapter;
    private ArrayList<GameInfo> hotGameInfos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_download, null);
        ButterKnife.bind(this, view);
        Aria.download(this).register();

        hotGameAdapter = new MyGameHotGameAdapter(getActivity());
        rcMyDownloadHotGame.setAdapter(hotGameAdapter);

        hotRecyclerViewAdapter = new MyDownRecyclerAdapter(getActivity(), false);
        rcMyDownloadList.setAdapter(hotRecyclerViewAdapter);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setFooter(new SimpleFooter(getActivity()));
        springView.setHeader(new SimpleHeader(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getDownRecord(page);
            }

            @Override
            public void onLoadmore() {
                page = page + 1;
                getDownRecordMore(page);
            }
        });
//        initView();
        return view;
    }


    public List<MyDownDateBean> initData() {
        bendilist.clear();
        List<DownloadEntity> NotComplet = Aria.download(this).getAllNotCompleteTask();       //未完成的
        List<DownloadEntity> AllComplete = Aria.download(this).getAllCompleteTask();        //下载好的
        if (NotComplet != null || AllComplete != null) {
            rlMyDownloadNone.setVisibility(View.GONE);
            rcMyDownloadList.setVisibility(View.VISIBLE);

            if (NotComplet != null) {
                for (int i = 0; i < NotComplet.size(); i++) {
                    MyDownDateBean downData = getDownData(NotComplet.get(i));
                    if (downData != null) {
                        bendilist.add(downData);
                    }
                }
            }

            if (AllComplete != null) {
                for (int i = 0; i < AllComplete.size(); i++) {
                    MyDownDateBean downData = getDownData(AllComplete.get(i));
                    if (downData != null) {
                        bendilist.add(downData);
                    }
                }
            }

            if (bendilist.size() > 0) {
                rlMyDownloadNone.setVisibility(View.GONE);
                rcMyDownloadList.setVisibility(View.VISIBLE);
                llMyDownloadHotGame.setVisibility(View.GONE);
            } else {
                rlMyDownloadNone.setVisibility(View.VISIBLE);
                rcMyDownloadList.setVisibility(View.GONE);
                llMyDownloadHotGame.setVisibility(View.VISIBLE);
            }

        } else {
            rlMyDownloadNone.setVisibility(View.VISIBLE);
            rcMyDownloadList.setVisibility(View.GONE);
            llMyDownloadHotGame.setVisibility(View.VISIBLE);
        }

        return bendilist;
    }


    /**
     * 获取(刷新）下载记录
     */
    private void getDownRecord(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Utils.getLoginUser().token);
        map.put("p", page + "");
        map.put("sdk_version", "1");
        HttpConstant.POST(downRecordHandler, HttpConstant.DownList, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler downRecordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springView != null) {
                springView.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("下载记录返回数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (code == 200 && jsonArray.length() > 0) {
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonData = (JSONObject) jsonArray.get(i);
                                MyDownDateBean downData = new MyDownDateBean();
                                downData.name = Utils.getJieQu(jsonData.getString("game_name"));
                                downData.id = jsonData.getInt("game_id");
                                downData.record_id = jsonData.getString("id");
                                downData.iconurl = jsonData.getString("icon");
                                downData.features = jsonData.getString("features");
                                downData.playNum = jsonData.getString("dow_num");
                                downData.size = jsonData.getString("game_size");
                                downData.canDownload = jsonData.getInt("xia_status");
                                list.add(downData);
                            }
                            getDown(initData(), list);
                        } else {
                            rlMyDownloadNone.setVisibility(View.VISIBLE);
                            rcMyDownloadList.setVisibility(View.GONE);
                            llMyDownloadHotGame.setVisibility(View.VISIBLE);
                            springView.setVisibility(View.GONE);
                            getNewGame();
                        }

                    } catch (Exception e) {
                        Log.e("下载记录返回数据异常", e.toString());
                        rlMyDownloadNone.setVisibility(View.VISIBLE);
                        rcMyDownloadList.setVisibility(View.GONE);
                        llMyDownloadHotGame.setVisibility(View.VISIBLE);
                        springView.setVisibility(View.GONE);
                        getNewGame();
                    }
                    break;
                case 2:
                    Log.e("下载记录返回数据", "网络错误");
                    rlMyDownloadNone.setVisibility(View.VISIBLE);
                    rcMyDownloadList.setVisibility(View.GONE);
                    llMyDownloadHotGame.setVisibility(View.VISIBLE);
                    springView.setVisibility(View.GONE);
                    getNewGame();
                    break;
            }
        }
    };

    /**
     * 加载更多下载记录
     */
    private void getDownRecordMore(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Utils.getLoginUser().token);
        map.put("p", page + "");
        map.put("sdk_version", "1");
        HttpConstant.POST(downRecordMoreHandler, HttpConstant.DownList, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler downRecordMoreHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springView != null) {
                springView.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("更多下载记录返回数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (code == 200 && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonData = (JSONObject) jsonArray.get(i);
                                MyDownDateBean downData = new MyDownDateBean();
                                downData.name = Utils.getJieQu(jsonData.getString("game_name"));
                                downData.id = jsonData.getInt("game_id");
                                downData.record_id = jsonData.getString("id");
                                downData.iconurl = jsonData.getString("icon");
                                downData.features = jsonData.getString("features");
                                downData.playNum = jsonData.getString("dow_num");
                                downData.canDownload = 1;
                                list.add(downData);
                            }
                            getDown(initData(), list);
                        } else {
                            ToastUtil.showToast("已经到底了~");
                        }

                    } catch (Exception e) {
                        ToastUtil.showToast("数据异常");
                        Log.e("下载记录返回数据异常", e.toString());
                    }
                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };


    //收到删除条目消息，刷新条目列表
    @Subscribe
    public void Refresh(EvenBusBean bean) {
        if (bean.type == 10) {
            list.clear();
            getDownRecord(1);
//            List<DownloadEntity> NotComplet = Aria.download(this).getAllNotCompletTask();       //未完成的
//            List<DownloadEntity> AllComplete = Aria.download(this).getAllCompleteTask();        //下载好的
//            if(NotComplet!=null||AllComplete!=null){
//                rlMyDownloadNone.setVisibility(View.GONE);
//                rcMyDownloadList.setVisibility(View.VISIBLE);
//                if(NotComplet!=null&&NotComplet.size()>0){
//                    for (int i = 0;i<NotComplet.size();i++){
//                        MyDownDateBean downData = getDownData(NotComplet.get(i));
//                        if(downData!=null){
//                            list.add(downData);
//                        }
//                    }
//                }
//                if(AllComplete!=null&&AllComplete.size()>0){
//                    for (int i = 0;i<AllComplete.size();i++){
//                        MyDownDateBean downData = getDownData(AllComplete.get(i));
//                        if(downData!=null){
//                            list.add(downData);
//                        }
//                    }
//                }
//                if(list.size()>0){
//                    rlMyDownloadNone.setVisibility(View.GONE);
//                    rcMyDownloadList.setVisibility(View.VISIBLE);
//                    llMyDownloadHotGame.setVisibility(View.GONE);
//                    hotRecyclerViewAdapter.setData(list);
//                }else{
//                    rlMyDownloadNone.setVisibility(View.VISIBLE);
//                    rcMyDownloadList.setVisibility(View.GONE);
//                    llMyDownloadHotGame.setVisibility(View.VISIBLE);
//                    getNewGame();
//                }
//            }else{
//                rlMyDownloadNone.setVisibility(View.VISIBLE);
//                rcMyDownloadList.setVisibility(View.GONE);
//                llMyDownloadHotGame.setVisibility(View.VISIBLE);
//                getNewGame();
//            }
        }
    }


    /**
     * 获取下载的游戏数据
     *
     * @param entity
     * @return
     */
    public MyDownDateBean getDownData(DownloadEntity entity) {
        if (db == null) {
            db = DatabaseOpenHelper.getInstance();
        }
        try {
            List<DownDataBean> all = db.findAll(DownDataBean.class);
            for (int i = 0; i < all.size(); i++) {
                DownDataBean downDataBean = all.get(i);
                if (downDataBean.DownUrl.equals(entity.getKey())) {
                    MyDownDateBean bean = db.findById(MyDownDateBean.class, downDataBean.id);
                    return bean;
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 没有下载记录时 获取热门游戏进行显示
     */
    public void getNewGame() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("rec_status", "3");
        hotGameParams.put("sdk_version", "1");
        Type type = new TypeToken<HttpResult<ArrayList<SingleHotGameBean>>>() {
        }.getType();
        new HttpUtils<ArrayList<SingleHotGameBean>>(type, HttpConstant.API_PERSONAL_USER_RECOMMEND_HOT, hotGameParams,
                "下载中心页面获取最新游戏", true) {

            @Override
            protected void _onSuccess(ArrayList<SingleHotGameBean> bean, String msg) {
                singleHotGameBean = bean;
                if (singleHotGameBean.size() < 3) {
                    llMyDownloadHotGame.setVisibility(View.GONE);
                } else {
                    formatHotGameData();
                }
            }

            @Override
            protected void _onError(String message, int code) {
                llMyDownloadHotGame.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                llMyDownloadHotGame.setVisibility(View.GONE);
            }
        };
    }

    private void formatHotGameData() {
        for (int i = 0; i < singleHotGameBean.size(); i++) {
            GameInfo gameInfo = new GameInfo();
            gameInfo.sdk_version = Integer.valueOf(singleHotGameBean.get(i).getSdk_version());
            gameInfo.id = Integer.valueOf(singleHotGameBean.get(i).getId());
            gameInfo.name = singleHotGameBean.get(i).getGame_name();
            gameInfo.iconurl = singleHotGameBean.get(i).getIcon();
            gameInfo.features = singleHotGameBean.get(i).getFeatures();
            gameInfo.playNum = singleHotGameBean.get(i).getPlay_num();
            gameInfo.type = singleHotGameBean.get(i).getGame_type_name();
            gameInfo.GameUrl = singleHotGameBean.get(i).getPlay_url();
            gameInfo.canDownload = singleHotGameBean.get(i).getXia_status();
            gameInfo.size = singleHotGameBean.get(i).getGame_size();
            hotGameInfos.add(gameInfo);
        }
        hotGameAdapter.setList(hotGameInfos);
        rcMyDownloadHotGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (hotGameAdapter.getList().get(i).sdk_version == 3) {
                    Intent intent = new Intent(getActivity(), GameDetailH5Activity.class);
                    intent.putExtra("game_id", hotGameAdapter.getList().get(i).id);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                    intent.putExtra("game_id", hotGameAdapter.getList().get(i).id);
                    getActivity().startActivity(intent);
                }
            }
        });
    }


    @Download.onWait
    void taskWait(DownloadTask task) {
        hotRecyclerViewAdapter.taskWait(task, task.getKey());
        hotGameAdapter.taskWait(task, task.getKey());
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        hotRecyclerViewAdapter.taskStop(task, task.getKey());
        hotGameAdapter.taskStop(task, task.getKey());
    }

    @Download.onTaskRunning
    void taskRuning(DownloadTask task) {
        hotRecyclerViewAdapter.taskRuning(task, task.getKey());
        hotGameAdapter.taskRuning(task, task.getKey());
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        hotRecyclerViewAdapter.taskCancel(task, task.getKey());
        hotGameAdapter.taskCancel(task, task.getKey());
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        hotRecyclerViewAdapter.taskFail(task, task.getKey());
        hotGameAdapter.taskFail(task, task.getKey());
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        hotRecyclerViewAdapter.taskComplete(task, task.getKey());
        hotGameAdapter.taskComplete(task, task.getKey());
    }


    @Override
    public void onResume() {
        super.onResume();
        getDownRecord(1);
        //判断是否安装
        if (list.size() > 0) {
            hotRecyclerViewAdapter.ConfirmationState();
        }
        if (hotGameInfos.size() > 0) {
            hotGameAdapter.ConfirmationState();
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @OnClick(R.id.img_my_download_more_hot_game)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_my_download_more_hot_game:
                this.getActivity().finish();
                Intent intent = new Intent("com.yinu.change.viewpage.index");
                intent.putExtra("change_status", PersonalCenter.ACTION_GO_NEW_SY_GAME);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                break;
        }
    }


    public void getDown(List<MyDownDateBean> myDownDateBeans, List<MyDownDateBean> list) {
        ArrayList<MyDownDateBean> dateBeans = new ArrayList<>();  //本地+线上记录
        for (int i = 0; i < list.size(); i++) {
            MyDownDateBean myDownDateBean = list.get(i);
            boolean a = false;
            for (int j = 0; j < myDownDateBeans.size(); j++) {
                MyDownDateBean myDownDateBean1 = myDownDateBeans.get(j);
                if (myDownDateBean.id == myDownDateBean1.id) {
                    a = true;
                }
            }
            if (!a) {
                dateBeans.add(myDownDateBean);
            }
        }
        dateBeans.addAll(myDownDateBeans);
        if (dateBeans.size() == 0) {
            rlMyDownloadNone.setVisibility(View.VISIBLE);
            rcMyDownloadList.setVisibility(View.GONE);
            llMyDownloadHotGame.setVisibility(View.VISIBLE);
            springView.setVisibility(View.GONE);
            getNewGame();
        } else {
            rlMyDownloadNone.setVisibility(View.GONE);
            rcMyDownloadList.setVisibility(View.VISIBLE);
            llMyDownloadHotGame.setVisibility(View.GONE);
            springView.setVisibility(View.VISIBLE);
            hotRecyclerViewAdapter.setData(dateBeans);
        }
    }

}
