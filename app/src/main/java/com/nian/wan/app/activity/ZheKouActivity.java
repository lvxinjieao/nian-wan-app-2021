package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.ZheKouAdapter;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.mc.developmentkit.utils.ToastUtil;
import com.mc.developmentkit.views.SimpleFooter;
import com.mc.developmentkit.views.SimpleHeader;
import com.mc.developmentkit.views.SpringView;
import android.widget.LinearLayout;;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：折扣专区
 */
public class ZheKouActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.btn_back)
    LinearLayout btnBack;

    @BindView(R.id.btn_search)
    RelativeLayout btnSearch;

    @BindView(R.id.ll_none)
    LinearLayout llNone;

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.springView)
    SpringView springview;

    private ZheKouAdapter zheKouAdapter;
    private List<GameInfo> listData = new ArrayList<>();
    private int p = 1;
    private HomeBroadcast homeBroadcast;

    @Override
    public void initView() {
        setContentView(R.layout.activity_zhekou);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        regsiterHomeBrodcast();
        zheKouAdapter = new ZheKouAdapter(ZheKouActivity.this);

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(onFreshListener);
        springview.setFooter(new SimpleFooter(this));
        springview.setHeader(new SimpleHeader(this));
        listView.setAdapter(zheKouAdapter);
        Aria.download(this).register();
        getZheKouGame();
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            p = 1;
            getZheKouGame();
        }

        @Override
        public void onLoadmore() {
            p = p + 1;
            onLoadMore();
        }
    };


    /**
     * 获取折扣专区数据
     */
    private void getZheKouGame(){
        listData.clear();
        Map<String,String> map = new HashMap<>();
        map.put("sdk_version","1");
        map.put("p",p+"");
        if (null != Utils.getPersistentUserInfo()){
            map.put("token",Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(handler, HttpConstant.ZhekouGame,map,false);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            springview.onFinishFreshAndLoad();
            try {
                Log.e("折扣专区：",msg.obj.toString());
                JSONObject jsonObject = new JSONObject(msg.obj.toString());
                int code = jsonObject.getInt("code");
                JSONArray jsonData = jsonObject.getJSONArray("data");
                if (code == 200) {
                    if (jsonData != null && jsonData.length() > 0) {
                        springview.setVisibility(View.VISIBLE);
                        llNone.setVisibility(View.GONE);
                        for (int i = 0; i < jsonData.length(); i++) {
                            JSONObject jsonObject1 = jsonData.getJSONObject(i);
                            GameInfo appInfo = new GameInfo();
                            appInfo.iconurl = jsonObject1.getString("icon");
                            appInfo.name = jsonObject1.getString("game_name");
                            appInfo.id = jsonObject1.getInt("id");
                            appInfo.features = jsonObject1.getString("features");
                            appInfo.type = jsonObject1.getString("game_type_name");
                            appInfo.playNum = jsonObject1.getString("play_num");
                            appInfo.GameUrl = jsonObject1.getString("play_url");
                            appInfo.fanli = jsonObject1.getString("ratio");
                            appInfo.canDownload = jsonObject1.getInt("xia_status");
                            appInfo.sdk_version = jsonObject1.getInt("sdk_version");
                            appInfo.discount = jsonObject1.getString("discount");
                            appInfo.first_discount = jsonObject1.getString("first_discount");
                            appInfo.continue_discount = jsonObject1.getString("continue_discount");
                            appInfo.size = jsonObject1.getString("game_size");
                            listData.add(appInfo);
                        }
                        zheKouAdapter.setListData(listData);
                    } else {
                        springview.setVisibility(View.GONE);
                        llNone.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtil.showToast(jsonObject.getString("msg"));
                }

            } catch (JSONException e) {
                Log.e("折扣专区异常",e.toString());
            }
        }
    };



    /**
     * 加载更多数据
     */
    private void onLoadMore(){
        Map<String,String> map = new HashMap<>();
        map.put("sdk_version","1");
        map.put("p",p+"");
        if (null != Utils.getPersistentUserInfo()){
            map.put("token",Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(mhandler, HttpConstant.ZhekouGame,map,false);
    }

    @SuppressLint("HandlerLeak")
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            springview.onFinishFreshAndLoad();
            try {
                Log.e("折扣专区加载更多数据：",msg.obj.toString());
                JSONObject jsonObject = new JSONObject(msg.obj.toString());
                int code = jsonObject.getInt("code");
                JSONArray jsonData = jsonObject.getJSONArray("data");

                if (code == 200) {
                    if (jsonData != null && jsonData.length() > 0) {
                        for (int i = 0; i < jsonData.length(); i++) {
                            JSONObject jsonObject1 = jsonData.getJSONObject(i);
                            GameInfo appInfo = new GameInfo();
                            appInfo.iconurl = jsonObject1.getString("icon");
                            appInfo.name = jsonObject1.getString("game_name");
                            appInfo.id = jsonObject1.getInt("id");
                            appInfo.features = jsonObject1.getString("features");
                            appInfo.type = jsonObject1.getString("game_type_name");
                            appInfo.playNum = jsonObject1.getString("play_num");
                            appInfo.GameUrl = jsonObject1.getString("play_url");
                            appInfo.fanli = jsonObject1.getString("ratio");
                            appInfo.canDownload = jsonObject1.getInt("xia_status");
                            appInfo.sdk_version = jsonObject1.getInt("sdk_version");
                            appInfo.discount = jsonObject1.getString("discount");
                            appInfo.first_discount = jsonObject1.getString("first_discount");
                            appInfo.continue_discount = jsonObject1.getString("continue_discount");
                            appInfo.size = jsonObject1.getString("game_size");
                            listData.add(appInfo);
                        }

                        zheKouAdapter.setListData(listData);
                    }
                } else {
                    ToastUtil.showToast(jsonObject.getString("msg"));
                }

            } catch (JSONException e) {
                Log.e("折扣专区加载更多异常",e.toString());
            }
        }
    };



    @OnClick({R.id.btn_back, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }


    private void regsiterHomeBrodcast() {
        homeBroadcast = new HomeBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(ZheKouActivity.this).registerReceiver(homeBroadcast, intentFilter);
    }
    /**
     * 登录监听
     */
    private class HomeBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                //登录成功
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    getZheKouGame();
                    break;
                //登录失败
                case DialogLoginActivity.EVENT_LOGIN_FILED:

                    break;
                //注销登录
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    getZheKouGame();
                    break;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listData.size()!=0){
            zheKouAdapter.ConfirmationState();
        }
    }

    @Download.onWait void taskWait(DownloadTask task){
        zheKouAdapter.taskWait(task,task.getKey());
    }

    @Download.onTaskStop void taskStop(DownloadTask task) {
        zheKouAdapter.taskStop(task,task.getKey());
    }
    @Download.onTaskRunning void taskRuning(DownloadTask task) {
        zheKouAdapter.taskRuning(task,task.getKey());
    }

    @Download.onTaskCancel void taskCancel(DownloadTask task) {
        zheKouAdapter.taskCancel(task,task.getKey());
    }

    @Download.onTaskFail void taskFail(DownloadTask task) {
        zheKouAdapter.taskFail(task,task.getKey());
    }

    @Download.onTaskComplete void taskComplete(DownloadTask task) {
        zheKouAdapter.taskComplete(task, task.getKey());
    }

}
