package com.nian.wan.app.fragment.main_home;

import android.os.Bundle;
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
import com.nian.wan.app.adapter.ActivitiesRecyclerViewAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.HomeActivitiesBean;
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
 * 首页活动-活动
 */
public class HuoDongFragment extends Fragment {

    @BindView(R.id.rc_activities_list)
    RecyclerView mRcActivitiesList;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_activities_no_data)
    LinearLayout llActivitiesNoData;

    private int pageNumber = 1;
    private HomeActivitiesBean activitiesBean;
    private ActivitiesRecyclerViewAdapter activitiesRecyclerViewAdapter;
    private List<HomeActivitiesBean> activitiesDataBeanList = new ArrayList<>();
    private int barLayoutState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.home_activities, null);
        ButterKnife.bind(this, view);

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(getActivity()));

        getActivities();

        activitiesRecyclerViewAdapter = new ActivitiesRecyclerViewAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRcActivitiesList.setLayoutManager(linearLayoutManager);
        mRcActivitiesList.setAdapter(activitiesRecyclerViewAdapter);
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
            getActivities();
        }
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getActivities();
        }

        @Override
        public void onLoadmore() {
            onLoadMore();
        }

    };

    private void onLoadMore() {
        HashMap<String, String> loadMoreParams = new HashMap<>();
        loadMoreParams.put("category", "3");
        loadMoreParams.put("p", String.valueOf(++pageNumber));
        Type type = new TypeToken<HttpResult<List<HomeActivitiesBean>>>() {
        }.getType();
        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }
        new HttpUtils<List<HomeActivitiesBean>>(type, HttpConstant.API_HOME_ACTIVITIES, loadMoreParams, "更多活动", false) {
            @Override
            protected void _onSuccess(List<HomeActivitiesBean> listData, String msg) {
                if (listData.size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    if (listData.size() <= 0 && activitiesDataBeanList.size() == 0) {
                        llActivitiesNoData.setVisibility(View.VISIBLE);
                        springview.setVisibility(View.GONE);
                    } else {
                        llActivitiesNoData.setVisibility(View.GONE);
                        springview.setVisibility(View.VISIBLE);
                        activitiesDataBeanList.addAll(listData);
                        activitiesRecyclerViewAdapter.setData(activitiesDataBeanList);
                    }
                }
            }

            @Override
            protected void _onError(String message, int code) {
                if (activitiesDataBeanList.size() == 0) {
                    llActivitiesNoData.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.GONE);
                }
            }

            @Override
            protected void _onError() {
            }
        };

    }


    /**
     * 获取活动
     */
    private void getActivities() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("category", "3");
        Type type = new TypeToken<HttpResult<List<HomeActivitiesBean>>>() {
        }.getType();

        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }

        new HttpUtils<List<HomeActivitiesBean>>(type, HttpConstant.API_HOME_ACTIVITIES, hotGameParams, "获取活动", false) {

            @Override
            protected void _onSuccess(List<HomeActivitiesBean> listData, String msg) {
                if (listData.size() <= 0) {
                    llActivitiesNoData.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.GONE);
                } else {
                    llActivitiesNoData.setVisibility(View.GONE);
                    springview.setVisibility(View.VISIBLE);
                    activitiesDataBeanList.clear();
                    activitiesDataBeanList.addAll(listData);
                    activitiesRecyclerViewAdapter.setData(activitiesDataBeanList);
                }
            }

            @Override
            protected void _onError(String message, int code) {
            }

            @Override
            protected void _onError() {
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
