package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.MyOrderAdapter;
import com.nian.wan.app.bean.MyOrderBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：我的订单Activity
 */
public class MyOrderActivity extends BaseFragmentActivity {

    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_home_store_record_help)
    ImageView imgHomeStoreRecordHelp;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.rl_null)
    RelativeLayout rlNull;
    @BindView(R.id.mtou)
    ImageView mtou;

    private int page = 1;
    private List<MyOrderBean> listPoint;
    private MyOrderAdapter adapter;
    private static final String TAG = "MyOrderActivity";

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, mtou);
        title.setText("我的订单");
        imgHomeStoreRecordHelp.setVisibility(View.GONE);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(onFreshListener);
        springView.setHeader(new SimpleHeader(this));
        springView.setFooter(new SimpleFooter(this));
        listPoint = new ArrayList<>();
        adapter = new MyOrderAdapter(MyOrderActivity.this);
        listview.setAdapter(adapter);
        getAll();

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
            if (springView != null) {
                springView.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    try {
                        Log.e(TAG, "我的订单：" + msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            if (jsonData != null && jsonData.length() > 0) {
                                springView.setVisibility(View.VISIBLE);
                                rlNull.setVisibility(View.GONE);
                                if (page == 1) {
                                    listPoint.clear();
                                }
                                for (int i = 0; i < jsonData.length(); i++) {
                                    JSONObject jsonPoint = jsonData.getJSONObject(i);
                                    MyOrderBean bean = new MyOrderBean();
                                    bean.setGood_name(jsonPoint.getString("good_name"));
                                    bean.setCover(jsonPoint.getString("cover"));
                                    bean.setCreate_time(jsonPoint.getString("create_time"));
                                    bean.setGood_type(jsonPoint.getString("type"));
                                    bean.setGoodmark(jsonPoint.getString("goodmark"));
                                    bean.setAddress(jsonPoint.getString("address"));
                                    bean.setGood_type(jsonPoint.getString("good_type"));
                                    bean.setNumber(jsonPoint.getString("number"));
                                    listPoint.add(bean);
                                }
                                adapter.setListData(listPoint);
                            } else {
                                if (listPoint.size() == 0) {
                                    springView.setVisibility(View.GONE);
                                    rlNull.setVisibility(View.VISIBLE);
                                } else {
                                    ToastUtil.showToast("已经到底了~");
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("我的订单异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            reFresh();
        }

        @Override
        public void onLoadmore() {

            loadMore();
        }
    };

    private void loadMore() {
        page = page + 1;
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", Utils.getPersistentUserInfo().token);
        map.put("p", page + "");
        map.put("type", "3");
        map.put("row", 10 + "");
        HttpConstant.POST(mHandler, HttpConstant.API_SHOP_INTEGRAL, map, false);

    }

    private void reFresh() {
        page = 1;
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", Utils.getPersistentUserInfo().token);
        map.put("p", page + "");
        map.put("type", "3");
        map.put("row", 10 + "");
        HttpConstant.POST(mHandler, HttpConstant.API_SHOP_INTEGRAL, map, false);
    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
        }
    }
}
