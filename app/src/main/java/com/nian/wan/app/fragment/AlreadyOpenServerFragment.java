package com.nian.wan.app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.nian.wan.app.adapter.AlreadyOpenServerRecyclerViewAdapter;
import com.nian.wan.app.bean.AlreadyOpenServerBean;
import com.nian.wan.app.bean.EvenBean;
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
 * @Description: 新开服-已开新服
 */
public class


AlreadyOpenServerFragment extends Fragment {

    //新开服列表
    @BindView(R.id.rc_already_open_server_list)
    RecyclerView rcAlreadyOpenServerList;
    @BindView(R.id.ll_foreshow_server_no_data)
    LinearLayout llForeshowServerNoData;
    @BindView(R.id.springView)
    SpringView springview;
    private int pageNumber = 1;
    private AlreadyOpenServerBean mOpenServerBeanList;
    private List<AlreadyOpenServerBean> openServerBeanList = new ArrayList<>();
    private AlreadyOpenServerRecyclerViewAdapter recyclerViewAdapter;
    private int barLayoutState;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.already_open_server, null);
        ButterKnife.bind(this, view);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(getActivity()));
        getAlreadyOpenServer();
        recyclerViewAdapter = new AlreadyOpenServerRecyclerViewAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcAlreadyOpenServerList.setLayoutManager(linearLayoutManager);
        rcAlreadyOpenServerList.setAdapter(recyclerViewAdapter);
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

        if (evenBean.reFresh == 1) {
            getAlreadyOpenServer();
        }
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getAlreadyOpenServer();
        }

        @Override
        public void onLoadmore() {
            onLoadMore();
        }

    };

    private void onLoadMore() {
        HashMap<String, String> loadMoreParams = new HashMap<>();
        loadMoreParams.put("type", "0");
        loadMoreParams.put("p", String.valueOf(++pageNumber));
        loadMoreParams.put("sdk_version", "1");
        Type type = new TypeToken<HttpResult<List<AlreadyOpenServerBean>>>() {
        }.getType();
        springview.onFinishFreshAndLoad();
        new HttpUtils<List<AlreadyOpenServerBean>>(type, HttpConstant.API_HOME_ALREADY_OPEN_SERVER, loadMoreParams, "新开服-已开新服", true) {
            @Override
            protected void _onSuccess(List<AlreadyOpenServerBean> listData, String msg) {
                if (listData.size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    openServerBeanList.addAll(listData);
                    recyclerViewAdapter.setData(openServerBeanList);
                    llForeshowServerNoData.setVisibility(View.GONE);
                    rcAlreadyOpenServerList.setVisibility(View.VISIBLE);
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
     * 获取已开服
     */
    private void getAlreadyOpenServer() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("type", "0");
        hotGameParams.put("p", "1");
        hotGameParams.put("sdk_version", "1");
        Type type = new TypeToken<HttpResult<List<AlreadyOpenServerBean>>>() {
        }.getType();
        springview.onFinishFreshAndLoad();
        new HttpUtils<List<AlreadyOpenServerBean>>(type, HttpConstant.API_HOME_ALREADY_OPEN_SERVER, hotGameParams, "已开服返回数据", true) {
            @Override
            protected void _onSuccess(List<AlreadyOpenServerBean> listData, String msg) {
                if (listData.size() <= 0) {
                    llForeshowServerNoData.setVisibility(View.VISIBLE);
                    rcAlreadyOpenServerList.setVisibility(View.GONE);
                    springview.setVisibility(View.GONE);
                } else {
                    llForeshowServerNoData.setVisibility(View.GONE);
                    rcAlreadyOpenServerList.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.VISIBLE);
                    openServerBeanList.clear();
                    openServerBeanList.addAll(listData);
                    recyclerViewAdapter.setData(openServerBeanList);
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
}
