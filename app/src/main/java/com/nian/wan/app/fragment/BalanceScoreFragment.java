package com.nian.wan.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.HomeStoreMissionActivity;
import com.nian.wan.app.bean.UserBalanceBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 余额-积分
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/2 11:12
 * @Modified By:
 * @Modified Date:
 */
public class BalanceScoreFragment extends Fragment {

    //积分
    @BindView(R.id.tv_balance_score)
    TextView mTvBalanceScore;
    //获取积分
    @BindView(R.id.btn_balance_score_get)
    Button mBtnBalanceScorePay;
    //积分兑换
    @BindView(R.id.btn_balance_score_exchange)
    Button mBtnBalanceScoreExchange;
    @BindView(R.id.springView)
    SpringView springview;

    private UserBalanceBean userBalanceBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_balance_score, null);
        ButterKnife.bind(this, view);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(getActivity()));
        return view;
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getScore();
        }

        @Override
        public void onLoadmore() {
        }

    };


    @OnClick({R.id.btn_balance_score_get, R.id.btn_balance_score_exchange})
    public void onClick(View view) {
        switch (view.getId()) {
            //获取积分
            case R.id.btn_balance_score_get:
                startActivity(new Intent(getContext(), HomeStoreMissionActivity.class));
                break;
            //兑换积分
            case R.id.btn_balance_score_exchange:
                getActivity().finish();
                Intent intent = new Intent("com.yinu.change.viewpage.index");
                intent.putExtra("change_status",
                        PersonalCenter.ACTION_GO_EXCHANGE);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                break;
        }
    }


    /**
     * 获取余额
     */
    public void getScore() {
        if (springview != null) {
            springview.onFinishFreshAndLoad();
        }
        Type type = new TypeToken<HttpResult<UserBalanceBean>>() {
        }.getType();
        new HttpUtils<UserBalanceBean>(type, HttpConstant.API_PERSONAL_USER_BALANCE, null, "获取余额", true) {

            @Override
            protected void _onSuccess(UserBalanceBean bean, String msg) {
                userBalanceBean = bean;
                mTvBalanceScore.setText(userBalanceBean.getShop_point());
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
        getScore();
    }
}
