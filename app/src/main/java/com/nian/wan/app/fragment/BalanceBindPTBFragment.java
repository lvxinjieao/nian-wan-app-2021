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
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.adapter.BalanceBindPtbRecyclerViewAdapter;
import com.nian.wan.app.bean.BalanceBindPtbBean;
import com.nian.wan.app.bean.UserBalanceBean;
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

/**
 * @Description: 余额-绑定平台币
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/2 11:12
 * @Modified By:
 * @Modified Date:
 */
public class BalanceBindPTBFragment extends Fragment {

    //绑定平台币列表
    @BindView(R.id.lv_balance_bind_ptb_list)
    RecyclerView mRcBalanceBindPtbList;
    //未绑定平台币
    @BindView(R.id.ll_balance_bind_ptb_none)
    LinearLayout mLlBalanceBindPtbNone;
    //绑定平台币的游戏集合
    private List<BalanceBindPtbBean> bindPtbBeanList;
    //绑定平台币列表适配器
    private BalanceBindPtbRecyclerViewAdapter ptbRecyclerViewAdapter;
    private List<UserBalanceBean.BinddataBean> dataBeanList;
    @BindView(R.id.springView)
    SpringView springview;
    private int pageNumber = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_balance_bind_platform_coin, null);
        ButterKnife.bind(this, view);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(getActivity()));
        springview.setFooter(new SimpleFooter(getActivity()));
        dataBeanList = new ArrayList<>();
        ptbRecyclerViewAdapter = new BalanceBindPtbRecyclerViewAdapter(getContext());
        getScore();
        return view;
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getScore();
        }

        @Override
        public void onLoadmore() {
            loadMoreScore();
        }

    };


    /**
     * 获取余额
     */
    public void getScore() {
        if (springview!=null){
            springview.onFinishFreshAndLoad();
        }
        dataBeanList.clear();
        Map<String, String> getScoreParams = new HashMap<>();
        getScoreParams.put("p", "1");
        Type type = new TypeToken<HttpResult<UserBalanceBean>>() {
        }.getType();
        new HttpUtils<UserBalanceBean>(type, HttpConstant.API_PERSONAL_USER_BALANCE, null, "获取余额", true) {

            @Override
            protected void _onSuccess(UserBalanceBean bean, String msg) {
                if (bean.getBinddata().size() > 0) {
                    mLlBalanceBindPtbNone.setVisibility(View.GONE);
                }
                dataBeanList.addAll(bean.getBinddata());
                ptbRecyclerViewAdapter.setData(dataBeanList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRcBalanceBindPtbList.setLayoutManager(layoutManager);
                mRcBalanceBindPtbList.setAdapter(ptbRecyclerViewAdapter);
                if (bean.getBinddata().size() <= 0) {
                    mLlBalanceBindPtbNone.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
            }
        };
    }


    /**
     * 获取余额
     */
    public void loadMoreScore() {
        if (springview!=null){
            springview.onFinishFreshAndLoad();
        }
        Map<String, String> getScoreParams = new HashMap<>();
        getScoreParams.put("p", String.valueOf(++pageNumber));
        Type type = new TypeToken<HttpResult<UserBalanceBean>>() {
        }.getType();
        new HttpUtils<UserBalanceBean>(type, HttpConstant.API_PERSONAL_USER_BALANCE, getScoreParams, "获取绑定平台币", true) {

            @Override
            protected void _onSuccess(UserBalanceBean bean, String msg) {
                dataBeanList.addAll(bean.getBinddata());
                ptbRecyclerViewAdapter.setData(dataBeanList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRcBalanceBindPtbList.setLayoutManager(layoutManager);
                mRcBalanceBindPtbList.setAdapter(ptbRecyclerViewAdapter);
                if (bean.getBinddata().size() == 0 && dataBeanList.size() >0) {
                    ToastUtil.showToast("已经到底了~");
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
