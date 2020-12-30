package com.nian.wan.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.UserMessageRecyclerViewAdapter;
import com.nian.wan.app.bean.UserMessageBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import android.widget.LinearLayout;;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author: XiaYuShi
 * @Date: 2018/1/2
 * @Description: 用户消息中心
 * @Modify By:
 * @ModifyDate:
 */

public class UserMessageCenterActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.rc_user_message_list)
    RecyclerView rcUserMessageList;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.ll_user_message_no_data)
    LinearLayout llUserMessageNoData;
    private ArrayList<UserMessageBean> userMessageBean;
    private UserMessageRecyclerViewAdapter userMessageRecyclerViewAdapter;
    private List<UserMessageBean> messageBean = new ArrayList<>();
    private int pageNumber = 1;

    @Override
    public void initView() {
        setContentView(R.layout.activity_user_message);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(onFreshListener);
        springView.setFooter(new SimpleFooter(this));
        springView.setHeader(new SimpleHeader(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcUserMessageList.setLayoutManager(linearLayoutManager);
        userMessageRecyclerViewAdapter = new UserMessageRecyclerViewAdapter(this);
        rcUserMessageList.setAdapter(userMessageRecyclerViewAdapter);
        getUserMessage();
        title.setText("消息");
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getUserMessage();
        }

        @Override
        public void onLoadmore() {
            onLoadMore();
        }

    };

    private void onLoadMore() {
        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
        Type type = new TypeToken<HttpResult<ArrayList<UserMessageBean>>>() {
        }.getType();
        Map<String, String> messageParmas = new HashMap<>();
        messageParmas.put("p", String.valueOf(++pageNumber));
        messageParmas.put("type", "1");

        new HttpUtils<ArrayList<UserMessageBean>>(type, HttpConstant.API_PERSONAL_USER_MESSAGE,
                messageParmas,
                "用户消息中心更多消息", true) {

            @Override
            protected void _onSuccess(ArrayList<UserMessageBean> bean, String msg) {
                userMessageBean = bean;
                if (userMessageBean.size() <= 0) {
                    ToastUtil.showToast("已经到底了~");
                } else {
                    messageBean.addAll(userMessageBean);
                    userMessageRecyclerViewAdapter.setData(messageBean);
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


    private void getUserMessage() {
        messageBean.clear();
        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
        Type type = new TypeToken<HttpResult<ArrayList<UserMessageBean>>>() {
        }.getType();
        Map<String, String> messageParmas = new HashMap<>();
        messageParmas.put("p", "1");
        messageParmas.put("type", "1");
        new HttpUtils<ArrayList<UserMessageBean>>(type, HttpConstant.API_PERSONAL_USER_MESSAGE,
                messageParmas,
                "获取用户消息", true) {

            @Override
            protected void _onSuccess(ArrayList<UserMessageBean> bean, String msg) {
                if(bean.size()>0){
                    userMessageBean = bean;
                    messageBean.addAll(userMessageBean);
                    userMessageRecyclerViewAdapter.setData(messageBean);
                    springView.setVisibility(View.VISIBLE);
                    llUserMessageNoData.setVisibility(View.GONE);
                }else {
                    springView.setVisibility(View.GONE);
                    llUserMessageNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
                springView.setVisibility(View.GONE);
                llUserMessageNoData.setVisibility(View.VISIBLE);
            }

            @Override
            protected void _onError() {
                springView.setVisibility(View.GONE);
                llUserMessageNoData.setVisibility(View.VISIBLE);
            }
        };
    }


    @OnClick({R.id.back, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.share:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
