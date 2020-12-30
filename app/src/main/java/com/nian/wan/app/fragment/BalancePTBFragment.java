package com.nian.wan.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.PayActivity;
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
 * @Description: 余额-平台币
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/2 11:12
 * @Modified By:
 * @Modified Date:
 */
public class BalancePTBFragment extends Fragment {

    //平台币
    @BindView(R.id.tv_balance_ptb)
    TextView mTvBalanceScore;
    //充值平台币
    @BindView(R.id.btn_balance_ptb_pay)
    Button mBtnBalancePTBPay;
    @BindView(R.id.springView)
    SpringView springview;

    private UserBalanceBean userBalanceBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScore();
    }


    public void initView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_balance_platform_coin, null);
        ButterKnife.bind(this, view);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(getActivity()));
        initView();
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

    @OnClick({R.id.btn_balance_ptb_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            //充值平台币
            case R.id.btn_balance_ptb_pay:
                this.startActivity(new Intent(getContext(), PayActivity.class));
                break;
        }
    }

    /**
     * 获取余额
     */
    public void getScore() {
        if (springview!=null){
            springview.onFinishFreshAndLoad();
        }
        Type type = new TypeToken<HttpResult<UserBalanceBean>>() {
        }.getType();
        new HttpUtils<UserBalanceBean>(type, HttpConstant.API_PERSONAL_USER_BALANCE, null, "获取余额", true) {

            @Override
            protected void _onSuccess(UserBalanceBean bean, String msg) {
                userBalanceBean = bean;
                mTvBalanceScore.setText(userBalanceBean.getBalance());
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
