package com.nian.wan.app.fragment.main_gift;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.adapter.HomeGiftGroupAdapter;
import com.nian.wan.app.bean.HomeGiftBean;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.view.LoadDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：手游礼包
 * 作者：闫冰
 * 时间: 2018-07-26 19:47
 */
public class GiftShouyouFragment extends Fragment {

    @BindView(R.id.gift_List)
    ListView giftList;

    @BindView(R.id.springView)
    SpringView springview;

    @BindView(R.id.ll_home_store_no_data)
    LinearLayout llHomeStoreNoData;

    //礼包组集合
    private List<HomeGiftBean> homeGiftBeanList = new ArrayList<>();
    private List<HomeGiftBean> listBean = new ArrayList<>();
    private int page = 1;
    private H5GiftBroadcast h5GiftBroadcast;
    private RegsiterBrodcast regsiterBrodcast;
    private LoadDialog loadDialog;
    //礼包列表父布局Adapter
    private HomeGiftGroupAdapter groupAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift_child, null);
        ButterKnife.bind(this, view);
        regsiterPersonalBroadcast();
        regsiterBrodcast();
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(getActivity()));
        springview.setHeader(new SimpleHeader(getActivity()));
        groupAdapter = new HomeGiftGroupAdapter(getActivity());
        giftList.setAdapter(groupAdapter);
        return view;
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            getGift(page);
        }

        @Override
        public void onLoadmore() {
            page = page+1;
            getGift(page);
        }

    };


    private class H5GiftBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                //登录成功
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    getGift(1);
                    break;
                //注销登录
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    getGift(1);
                    break;

            }
        }
    }

    /**
     * 注册广播和各个模块进行联动
     */
    private void regsiterPersonalBroadcast() {
        h5GiftBroadcast = new H5GiftBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(h5GiftBroadcast, intentFilter);
    }

    private void regsiterBrodcast() {
        regsiterBrodcast = new RegsiterBrodcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.change.viewpage.index");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(regsiterBrodcast, intentFilter);
    }

    private class RegsiterBrodcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("change_status", -1)) {
                //个人中心我的礼包跳转到推荐礼包
                case PersonalCenter.ACTION_GO_RECOMMEND_GIFT:

                    break;
            }
        }
    }


    /**
     * 获取H5礼包
     */
    private void getGift(int p) {
        if (springview!=null){
            springview.onFinishFreshAndLoad();
        }
        if (p == 1){
            homeGiftBeanList.clear();
            listBean.clear();
        }
        Map<String, String> giftParams = new HashMap<>();
        giftParams.put("sdk_version","1");
        giftParams.put("p",p+"");

        Type type = new TypeToken<HttpResult<List<HomeGiftBean>>>() {}.getType();

        new HttpUtils<List<HomeGiftBean>>(type, HttpConstant.API_GIFT_LISTS, giftParams, "获取手游礼包", true) {

            @Override
            protected void _onSuccess(List<HomeGiftBean> listData, String msg) {
                if (listData.size()>0){
                    llHomeStoreNoData.setVisibility(View.GONE);
                    springview.setVisibility(View.VISIBLE);
                    for (int i = 0; i < listData.size(); i++) {
                        homeGiftBeanList.add(listData.get(i));
                    }
                    listBean.addAll(homeGiftBeanList);
                    groupAdapter.setData(listBean);
                }else {
                    if (listBean.size()>0){
                        ToastUtil.showToast("已经到底了~");
                    }else {
                        llHomeStoreNoData.setVisibility(View.VISIBLE);
                        springview.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            protected void _onError(String message, int code) {
                llHomeStoreNoData.setVisibility(View.VISIBLE);
                springview.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
                llHomeStoreNoData.setVisibility(View.VISIBLE);
                springview.setVisibility(View.GONE);
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();
        getGift(1);
    }


}
