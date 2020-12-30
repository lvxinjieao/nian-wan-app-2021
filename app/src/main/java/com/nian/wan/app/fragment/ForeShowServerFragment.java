package com.nian.wan.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.adapter.ForeShowServerRecyclerViewAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.ForeShowServerBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;

import java.lang.reflect.Type;
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
 * @Description: 预告开服
 */
public class ForeShowServerFragment extends Fragment {

    //预告开服列表
    @BindView(R.id.rc_fore_show_server_list)
    RecyclerView rcForeShowServerList;
    @BindView(R.id.ll_foreshow_server_no_data)
    LinearLayout llForeshowServerNoData;
    @BindView(R.id.springView)
    SpringView springview;
    private int pageNumber = 1;
    private ForeShowServerBean mForeShowServerBeenList;
    private List<ForeShowServerBean> foreShowServerBeanList = new ArrayList<>();
    private ForeShowServerRecyclerViewAdapter foreShowServerRecyclerViewAdapter;
    private ForeShowServerBroadcast foreShowServerBroadcast;
    private int barLayoutState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regsiterForeShowServerBrodcast();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fore_show_server, null);
        ButterKnife.bind(this, view);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
//        springview.setHeader(new SimpleHeader(getActivity()));
        springview.setFooter(new SimpleFooter(getActivity()));
//        rcForeShowServerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (isVisBottom(recyclerView)) {
//                    springview.setEnable(true);
//                } else {
//                    if (2 == barLayoutState) {
//                        springview.setEnable(true);
//                    } else if (1 == barLayoutState) {
//                        springview.setEnable(false);
//                    }
//                }
//            }
//
//        });
        getForeOpenServer();
        foreShowServerRecyclerViewAdapter =
                new ForeShowServerRecyclerViewAdapter(getActivity());
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());
        rcForeShowServerList.setLayoutManager(linearLayoutManager);
        rcForeShowServerList.setAdapter(foreShowServerRecyclerViewAdapter);
        return view;
    }


    @Subscribe
    public void eventMethod(EvenBean evenBean) {
        this.barLayoutState = evenBean.isExpandable;
        if (2 == evenBean.isExpandable) {
            springview.setFocusable(false);
            springview.setEnable(false);
        } else if (1 == evenBean.isExpandable) {
            springview.setFocusable(true);
            springview.setEnable(true);
        }

        if (evenBean.reFresh == 1){
            getForeOpenServer();
        }
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getForeOpenServer();
        }

        @Override
        public void onLoadmore() {
            onLoadMore();
        }

    };

    private void regsiterForeShowServerBrodcast() {
        foreShowServerBroadcast = new ForeShowServerBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(foreShowServerBroadcast, intentFilter);
    }

    private class ForeShowServerBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                //注销登录
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    for (int i = 0; i < foreShowServerBeanList.size(); i++) {
                        foreShowServerBeanList.get(i).setIsnotice(0);
                        foreShowServerRecyclerViewAdapter.setData(foreShowServerBeanList);
                    }
                    break;
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    getForeOpenServer();
                    break;
            }
        }

    }

    private void onLoadMore() {
        HashMap<String, String> loadMoreParams = new HashMap<>();
        loadMoreParams.put("type", "1");
        loadMoreParams.put("p", String.valueOf(++pageNumber));
        loadMoreParams.put("sdk_version", "1");
        Type type = new TypeToken<HttpResult<List<ForeShowServerBean>>>() {
        }.getType();
        if (springview!=null){
            springview.onFinishFreshAndLoad();
        }
        new HttpUtils<List<ForeShowServerBean>>(type, HttpConstant.API_HOME_ALREADY_OPEN_SERVER, loadMoreParams, "预告开服", true) {
            @Override
            protected void _onSuccess(List<ForeShowServerBean> listData, String msg) {
                if (listData.size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    foreShowServerBeanList.addAll(listData);
                    foreShowServerRecyclerViewAdapter.setData(foreShowServerBeanList);
                    llForeshowServerNoData.setVisibility(View.GONE);
                    rcForeShowServerList.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void _onError(String message, int code) {
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };

    }



    /**
     * 获取预告开服
     */
    private void getForeOpenServer() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("type", "1");
        hotGameParams.put("p", "1");
        hotGameParams.put("sdk_version", "1");
        Type type = new TypeToken<HttpResult<List<ForeShowServerBean>>>() {
        }.getType();
        if (springview != null){
            springview.onFinishFreshAndLoad();
        }
        new HttpUtils<List<ForeShowServerBean>>(type, HttpConstant.API_HOME_ALREADY_OPEN_SERVER, hotGameParams, "获取预告开服", true) {
            @Override
            protected void _onSuccess(List<ForeShowServerBean> listData, String msg) {
                if (listData.size() <= 0) {
                    llForeshowServerNoData.setVisibility(View.VISIBLE);
                    rcForeShowServerList.setVisibility(View.GONE);
                    springview.setVisibility(View.GONE);
                } else {
                    llForeshowServerNoData.setVisibility(View.GONE);
                    rcForeShowServerList.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.VISIBLE);
                    foreShowServerBeanList.clear();
                    foreShowServerBeanList.addAll(listData);
                    foreShowServerRecyclerViewAdapter.setData(foreShowServerBeanList);
                }
            }

            @Override
            protected void _onError(String message, int code) {
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }


}
