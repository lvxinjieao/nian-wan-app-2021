package com.nian.wan.app.fragment.main_home;

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
import com.nian.wan.app.adapter.PublishRecyclerViewAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.HomePublishBean;
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

/**
 * @Description: 首页活动-公告
 */
public class GongGaoFragment extends Fragment {

    @BindView(R.id.rc_activities_list)
    RecyclerView mRcPublishList;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_activities_no_data)
    LinearLayout llPublishServerNoData;

    private int pageNumber = 1;
    private List<HomePublishBean> gamePublishBeanList = new ArrayList<>();
    private PublishRecyclerViewAdapter publishRecyclerViewAdapter;
    private int barLayoutState;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_activities, null);
        ButterKnife.bind(this, view);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(getActivity()));

        getPublish();

        publishRecyclerViewAdapter = new PublishRecyclerViewAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRcPublishList.setLayoutManager(linearLayoutManager);
        mRcPublishList.setAdapter(publishRecyclerViewAdapter);
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
            getPublish();
        }
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getPublish();
        }

        @Override
        public void onLoadmore() {
            onLoadMore();
        }

    };

    private void onLoadMore() {
        HashMap<String, String> loadMoreParams = new HashMap<>();
        loadMoreParams.put("category", "2");
        loadMoreParams.put("p", String.valueOf(++pageNumber));
        Type type = new TypeToken<HttpResult<List<HomePublishBean>>>() {
        }.getType();
        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }
        new HttpUtils<List<HomePublishBean>>(type, HttpConstant.API_HOME_ACTIVITIES, loadMoreParams, "更多公告返回数据", false) {
            @Override
            protected void _onSuccess(List<HomePublishBean> listData, String msg) {
                if (listData.size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    if (listData.size() <= 0 && gamePublishBeanList.size() == 0) {
                        llPublishServerNoData.setVisibility(View.VISIBLE);
                        springview.setVisibility(View.GONE);
                    } else {
                        llPublishServerNoData.setVisibility(View.GONE);
                        springview.setVisibility(View.VISIBLE);
                        gamePublishBeanList.addAll(listData);
                        publishRecyclerViewAdapter.setData(gamePublishBeanList);
                    }
                }
            }

            @Override
            protected void _onError(String message, int code) {
                if (gamePublishBeanList.size() == 0) {
                    llPublishServerNoData.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.GONE);
                }
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };

    }


    /**
     * 获取公告
     */
    private void getPublish() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("category", "2");
        Type type = new TypeToken<HttpResult<List<HomePublishBean>>>() {
        }.getType();

        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }

        new HttpUtils<List<HomePublishBean>>(type, HttpConstant.API_HOME_ACTIVITIES, hotGameParams, "获取公告", false) {
            @Override
            protected void _onSuccess(List<HomePublishBean> listData, String msg) {
                if (listData.size() <= 0) {
                    llPublishServerNoData.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.GONE);
                } else {
                    llPublishServerNoData.setVisibility(View.GONE);
                    springview.setVisibility(View.VISIBLE);
                    gamePublishBeanList.clear();
                    gamePublishBeanList.addAll(listData);
                    publishRecyclerViewAdapter.setData(gamePublishBeanList);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
