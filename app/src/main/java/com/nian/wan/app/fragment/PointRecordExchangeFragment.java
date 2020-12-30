package com.nian.wan.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SpringView;
import com.nian.wan.app.R;
import com.nian.wan.app.adapter.PointRecordListAdapter;
import com.nian.wan.app.bean.PointRecordBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

;

/**
 * Created by Administrator on 2017/12/15.
 * 积分记录-兑换
 */

public class PointRecordExchangeFragment extends Fragment {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.ll_null)
    LinearLayout llNull;

    private PointRecordListAdapter adapter;
    private List<PointRecordBean> listPoint;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LinearLayout.inflate(getActivity(), R.layout.fragment_point_record, null);
        ButterKnife.bind(this, view);
        listPoint = new ArrayList<>();
        adapter = new PointRecordListAdapter(getActivity(), 2);
        listview.setAdapter(adapter);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(onFreshListener);
        springView.setFooter(new SimpleFooter(getActivity()));
        getAll();
        return view;
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
        }

        @Override
        public void onLoadmore() {
            onLoadMord();
        }
    };

    /**
     * 加载更多
     */
    private void onLoadMord() {
        page = page + 1;
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", Utils.getPersistentUserInfo().token);
        map.put("p", page + "");
        map.put("type", "3");
        map.put("row", 10 + "");
        HttpConstant.POST(mHandler, HttpConstant.API_SHOP_INTEGRAL, map, false);
    }

    /**
     * 获得兑换记录
     */
    private void getAll() {
        page = 1;
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", Utils.getPersistentUserInfo().token);
        map.put("p", page + "");
        map.put("type", "3");
        map.put("row", 10 + "");
        HttpConstant.POST(mHandler, HttpConstant.API_SHOP_INTEGRAL, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {
            if (springView!=null){
                springView.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("PointRecordExchangeFragment", "积分兑换记录返回数据：" + msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            if (jsonData != null && jsonData.length() > 0) {
                                springView.setVisibility(View.VISIBLE);
                                llNull.setVisibility(View.GONE);
                                for (int i = 0; i < jsonData.length(); i++) {
                                    JSONObject jsonPoint = jsonData.getJSONObject(i);
                                    PointRecordBean bean = new PointRecordBean();
                                    bean.name = jsonPoint.getString("good_name");
                                    bean.cover = jsonPoint.getString("cover");
                                    bean.create_time = jsonPoint.getString("create_time");
                                    bean.point = jsonPoint.getString("pay_amount");
                                    bean.type = jsonPoint.getInt("type");
                                    bean.goodmark = jsonPoint.optString("goodmark", "");
                                    bean.goodType = jsonPoint.optString("good_type", "");
                                    listPoint.add(bean);
                                }
                                adapter.setListData(listPoint);
                            } else {
                                if (listPoint.size() == 0) {
                                    springView.setVisibility(View.GONE);
                                    llNull.setVisibility(View.VISIBLE);
                                } else {
                                    ToastUtil.showToast("已经到底了~");
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("查询积分兑换记录异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };
}
