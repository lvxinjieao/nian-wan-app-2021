package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.HomeHotRecyclerAdapter;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;

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
 * 分类游戏列表
 * Created by 齐天大圣 on 2016/10/12.
 */

public class Type_Activity extends BaseActivity {

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.listview)
    RecyclerView listview;

    @BindView(R.id.springview)
    SpringView springview;

    private HomeHotRecyclerAdapter fenLeiListAdapter;
    private List<GameInfo> listDate;
    private String id;
    private int limit = 1;
    private String name;
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        listDate = new ArrayList<>();
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("type_id");
        num = getIntent().getStringExtra("num");
        //isH5Game = getIntent().getBooleanExtra("isH5Game",true);
        if ("".equals(num)) {
            title.setText(name);
        } else {
            title.setText(name + "(" + num + ")");
        }

        initSpringViewStyle();
        initAndReflsh();
    }

    private void initSpringViewStyle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        fenLeiListAdapter = new HomeHotRecyclerAdapter(this);
        //fenLeiListAdapter.setIsH5Game(isH5Game);
        listview.setLayoutManager(linearLayoutManager);
        listview.setAdapter(fenLeiListAdapter);
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
        listDate.clear();
        getGameClassficationList();
    }

    private void onLoadMord() {
        limit = ++limit;
        getGameClassficationList();
    }


    /**
     * 获取游戏分类列表
     */
    private void getGameClassficationList() {
        Map<String, String> map = new HashMap<>();
        map.put("p", limit + "");
        map.put("type_id", id + "");
//        if (isH5Game){
//            map.put("sdk_version", "3");
//        }else {
            map.put("sdk_version", "1");
//        }

        HttpConstant.POST(handler, HttpConstant.API_GAME_SUBJECT_LIST, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (springview!=null){
                springview.onFinishFreshAndLoad();
            }
            switch (msg.what) {
                case 1:
                    Log.e("分类列表",msg.obj.toString());
                    try {
                        List<GameInfo> infos = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int status = jsonObject.getInt("code");
                        if (status == 200) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                GameInfo appInfo = new GameInfo();
                                appInfo.iconurl = jsonObject1.getString("icon");
                                appInfo.name = jsonObject1.getString("game_name");
                                appInfo.id = jsonObject1.getInt("id");
                                appInfo.features = jsonObject1.getString("features");
                                appInfo.type = jsonObject1.getString("game_type_name");
                                appInfo.playNum = jsonObject1.getString("play_num");
                                appInfo.GameUrl = jsonObject1.getString("play_url");
                                appInfo.fanli = jsonObject1.getString("ratio");
                                appInfo.gift = jsonObject1.getInt("gift_id");
                                appInfo.canDownload = jsonObject1.getInt("xia_status");
                                appInfo.size = jsonObject1.getString("game_size");
                                infos.add(appInfo);
                            }
                            if (TextUtils.isEmpty(num)) {
                                title.setText(name + "(" + infos.size() + ")");
                            }
                            if (infos !=null && infos.size()>0){
                                listDate.addAll(infos);
                                fenLeiListAdapter.setData(listDate);
                                springview.setVisibility(View.VISIBLE);
                            }else {
                                if (listDate.size()>0){
                                    Utils.TS("已经到底了~");
                                }else {
                                    Utils.TS("该类别下暂时没有游戏~");
                                }
                            }
                        }else {
                            ToastUtil.showToast(jsonObject.getString("msg"));
                        }
                    } catch (Exception e) {
                        Log.e("分类列表json异常：", e.toString());
                    }

                    break;
                case 2:
                     //Utils.TS("网络异常");
                    break;
            }
        }
    };

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
