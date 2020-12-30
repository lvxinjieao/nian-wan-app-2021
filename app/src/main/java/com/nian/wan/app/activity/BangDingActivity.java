package com.nian.wan.app.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.BangDingListAdapter;
import com.nian.wan.app.bean.BangBiBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.google.gson.Gson;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绑定平台币
 */

public class BangDingActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.springview)
    SpringView springview;

    private int limit = 1;
    private BangDingListAdapter bangDingListAdapter;
    private List<BangBiBean.MsgBean> list = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_bangding);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        initSpringViewStyle();
        title.setText("绑定平台币");
        initAndReflsh();
    }

    private void initSpringViewStyle() {
        bangDingListAdapter = new BangDingListAdapter(this);
        listview.setAdapter(bangDingListAdapter);
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(this));
        springview.setFooter(new SimpleFooter(this));
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            initAndReflsh();
        }

        @Override
        public void onLoadmore() {
            onLoadMord();
        }
    };


    private void initAndReflsh() {
        limit = 1;
        Map<String, String> map = new HashMap<String, String>();
        map.put("p", limit + "");
        map.put("token", Utils.getLoginUser().token);
        HttpConstant.POST(handler, HttpConstant.BangBiUrl, map, false);
    }

    private void onLoadMord() {
        limit = ++limit;
        Map<String, String> map = new HashMap<String, String>();
        map.put("p", limit + "");
        map.put("token", Utils.getLoginUser().token);
        HttpConstant.POST(mHandler, HttpConstant.BangBiUrl, map, false);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    /**
     * 刚进来
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springview != null) {
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    Log.e("获取绑币json", msg.obj.toString());
                    Gson gson = new Gson();
                    BangBiBean bangBiBean = gson.fromJson(msg.obj.toString(), BangBiBean.class);
                    if (bangBiBean.getStatus() == 1) {
                        List<BangBiBean.MsgBean> msg1 = bangBiBean.getMsg();
                        if (msg1 != null && msg1.size() != 0) {
                            list.clear();
                            list.addAll(msg1);
                            bangDingListAdapter.setList(list);
                        }
                    }
                    break;
                case 2:
                    // ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };

    /**
     * 加载更多
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springview != null) {
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    Log.e("获取绑币json", msg.obj.toString());
                    Gson gson = new Gson();
                    BangBiBean bangBiBean = gson.fromJson(msg.obj.toString(), BangBiBean.class);
                    if (bangBiBean.getStatus() == 1) {
                        List<BangBiBean.MsgBean> msg1 = bangBiBean.getMsg();
                        if (msg1 != null && msg1.size() != 0) {
                            list.addAll(msg1);
                            bangDingListAdapter.setList(list);
                        }
                    }
                    break;
                case 2:

                    break;
            }
        }
    };
}
