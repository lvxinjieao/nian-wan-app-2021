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

/*
 * 描述：攻略
 */
public class GongLueFragment extends Fragment {

    @BindView(R.id.rc_activities_list)
    RecyclerView mRcPublishList;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_activities_no_data)
    LinearLayout llPublishServer;

    private int barLayoutState;
    private int pageNumber = 1;
    private List<HomePublishBean> gongLueBeanList = new ArrayList<>();

    private PublishRecyclerViewAdapter gonglueRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.home_activities, null);
        ButterKnife.bind(this, view);

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);

        springview.setFooter(new SimpleFooter(getActivity()));

        getPublish();

        gonglueRecyclerViewAdapter = new PublishRecyclerViewAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRcPublishList.setLayoutManager(linearLayoutManager);
        mRcPublishList.setAdapter(gonglueRecyclerViewAdapter);

        return view;
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


    private void onLoadMore() {
        HashMap<String, String> loadMoreParams = new HashMap<>();
        loadMoreParams.put("category", "4");
        loadMoreParams.put("p", String.valueOf(++pageNumber));
        Type type = new TypeToken<HttpResult<List<HomePublishBean>>>() {
        }.getType();
        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }

        new HttpUtils<List<HomePublishBean>>(type, HttpConstant.API_HOME_ACTIVITIES, loadMoreParams, "更多攻略返回数据", false) {

            @Override
            protected void _onSuccess(List<HomePublishBean> listData, String msg) {
                if (listData.size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    if (listData.size() <= 0 && gongLueBeanList.size() == 0) {
                        llPublishServer.setVisibility(View.VISIBLE);
                        springview.setVisibility(View.GONE);
                    } else {
                        llPublishServer.setVisibility(View.GONE);
                        springview.setVisibility(View.VISIBLE);
                        gongLueBeanList.addAll(listData);
                        gonglueRecyclerViewAdapter.setData(gongLueBeanList);
                    }
                }
            }

            @Override
            protected void _onError(String message, int code) {
                if (gongLueBeanList.size() == 0) {
                    llPublishServer.setVisibility(View.VISIBLE);
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
     * 获取攻略
     */
    private void getPublish() {
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("category", "4");
        Type type = new TypeToken<HttpResult<List<HomePublishBean>>>() {
        }.getType();

        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }

        new HttpUtils<List<HomePublishBean>>(type, HttpConstant.API_HOME_ACTIVITIES, hotGameParams, "获取攻略", false) {

            @Override
            protected void _onSuccess(List<HomePublishBean> listData, String msg) {
                if (listData.size() <= 0) {
                    llPublishServer.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.GONE);
                } else {
                    llPublishServer.setVisibility(View.GONE);
                    springview.setVisibility(View.VISIBLE);
                    gongLueBeanList.clear();
                    gongLueBeanList.addAll(listData);
                    gonglueRecyclerViewAdapter.setData(gongLueBeanList);
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
