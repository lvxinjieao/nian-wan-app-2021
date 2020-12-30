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
import com.nian.wan.app.adapter.HomeMiniProgramRecyclerViewAdapter;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.bean.HomeMiniBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;

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
 * 描述：首页小程序页面
 * 作者：闫冰
 * 时间: 2018-06-04 9:16
 */
public class HomeMiniProgramFragment extends Fragment {

    @BindView(R.id.rc_home_mini_list)
    RecyclerView mRcHomeMiniList;

    private List<HomeMiniBean> homeMiniBeanList = new ArrayList<>();

    //首页小程序列表适配器
    private HomeMiniProgramRecyclerViewAdapter miniProgramRecyclerViewAdapter;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    private int pageNumber = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_mini_program_list, null);
        ButterKnife.bind(this, view);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(getActivity()));
        getMiniProgram();
        miniProgramRecyclerViewAdapter = new HomeMiniProgramRecyclerViewAdapter(getActivity());
        mRcHomeMiniList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getMiniProgram();
        }

        @Override
        public void onLoadmore() {
            onLoadMore();
        }

    };


    @Subscribe
    public void eventMethod(EvenBean evenBean) {
        if (2 == evenBean.isExpandable) {
            springview.setFocusable(false);
            springview.setEnable(false);
        } else if (1 == evenBean.isExpandable) {
            springview.setFocusable(true);
            springview.setEnable(true);
        }

        if (evenBean.reFresh == 1){
            getMiniProgram();
        }
    }


    /**
     * 加载更多
     */
    private void onLoadMore() {
        pageNumber++;
        HashMap<String, String> loadMoreParams = new HashMap<>();
        loadMoreParams.put("rec_status", "2");
        loadMoreParams.put("limit", String.valueOf(10));
        loadMoreParams.put("p", pageNumber + "");
        Type type = new TypeToken<HttpResult<List<HomeMiniBean>>>() {
        }.getType();

        if (springview!=null){
            springview.onFinishFreshAndLoad();
        }

        new HttpUtils<List<HomeMiniBean>>(type, HttpConstant.API_SMALLGAME_LIST, loadMoreParams, "首页小程序加载更多", false) {
            @Override
            protected void _onSuccess(List<HomeMiniBean> listBean, String msg) {
                if (listBean.size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    homeMiniBeanList.addAll(listBean);
                    llNoData.setVisibility(View.GONE);
                    miniProgramRecyclerViewAdapter.setData(homeMiniBeanList);
                    mRcHomeMiniList.setAdapter(miniProgramRecyclerViewAdapter);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };

    }


    /**
     * 获取小程序列表
     */
    private void getMiniProgram() {
        pageNumber = 1;
        Map<String, String> hotGameParams = new HashMap<>();
        hotGameParams.put("rec_status", "2");
        hotGameParams.put("limit", "10");
        hotGameParams.put("p", "1");
        Type type = new TypeToken<HttpResult<List<HomeMiniBean>>>() {
        }.getType();

        if (springview!=null){
            springview.onFinishFreshAndLoad();
        }

        new HttpUtils<List<HomeMiniBean>>(type, HttpConstant.API_SMALLGAME_LIST, hotGameParams, "首页小程序返回数据", false) {

            @Override
            protected void _onSuccess(List<HomeMiniBean> listBean, String msg) {
                if (listBean.size() <= 0) {
                    llNoData.setVisibility(View.VISIBLE);
                    springview.setVisibility(View.GONE);
                } else {
                    homeMiniBeanList.clear();
                    homeMiniBeanList.addAll(listBean);
                    llNoData.setVisibility(View.GONE);
                    springview.setVisibility(View.VISIBLE);
                    miniProgramRecyclerViewAdapter.setData(homeMiniBeanList);
                    mRcHomeMiniList.setAdapter(miniProgramRecyclerViewAdapter);
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
