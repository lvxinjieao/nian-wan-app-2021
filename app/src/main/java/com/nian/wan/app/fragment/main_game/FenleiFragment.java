package com.nian.wan.app.fragment.main_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.Type_Activity;
import com.nian.wan.app.adapter.FenAdapter;
import com.nian.wan.app.bean.FenBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import android.widget.LinearLayout;;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分类
 */
public class FenleiFragment extends Fragment {

    @BindView(R.id.springview)
    SpringView springview;

    @BindView(R.id.listView)
    ListView listview;

    @BindView(R.id.no_data_layout)
    LinearLayout no_data_layout;

    private FenAdapter fenAdapter;
    private Activity activity;

    private ArrayList<FenBean.GroupBean> fenBeens = new ArrayList<FenBean.GroupBean>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_fen, null);
        ButterKnife.bind(this, view);

        activity = getActivity();

        fenAdapter = new FenAdapter(getActivity());
        listview.setAdapter(fenAdapter);

        onLoad();

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setHeader(new SimpleHeader(getActivity()));
        return view;
    }


    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            onLoad();
        }

        @Override
        public void onLoadmore() {
        }
    };


    /**
     * 获取游戏分类
     */
    private void onLoad() {
        Type type = new TypeToken<HttpResult<FenBean>>() {
        }.getType();
        Map<String, String> map = new HashMap<>();
        map.put("sdk_version", "1");

        new HttpUtils<FenBean>(type, HttpConstant.API_GAME_SUBJECT, map, "游戏分类", true) {

            @Override
            protected void _onSuccess(FenBean bean, String msg) {
                if (bean.getGroup() != null) {
                    springview.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);

                    fenBeens.addAll(bean.getGroup());
                    fenAdapter.setList(bean.getGroup());

                    if (springview != null) {
                        springview.onFinishFreshAndLoad();
                    }

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            fenAdapter.ShuaXin(i);
                            Intent intent = new Intent();
                            FenBean.GroupBean fenBean = fenBeens.get(i);
                            intent.putExtra("name", fenBean.getType_name());
                            intent.putExtra("type_id", fenBean.getType_id());
                            intent.putExtra("num", fenBean.getCounts());
                            intent.setClass(activity, Type_Activity.class);
                            activity.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            protected void _onError(String message, int code) {
                no_data_layout.setVisibility(View.VISIBLE);
                springview.setVisibility(View.GONE);
            }

            @Override
            protected void _onError() {
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
        onLoad();
    }
}
