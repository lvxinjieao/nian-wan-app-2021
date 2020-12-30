package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.adapter.MyYaoListAdapter;
import com.nian.wan.app.bean.MyYaoqingBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的邀请记录Activity
 * Created by Administrator on 2017/3/21.
 */

public class MyInvitationActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ptb)
    TextView ptb;
    @BindView(R.id.ren)
    TextView ren;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;

    private MyYaoListAdapter myYaoListAdapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_myinvitation);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        back.setVisibility(View.VISIBLE);
        title.setText("我的邀请");
        myYaoListAdapter = new MyYaoListAdapter(this);
        listview.setAdapter(myYaoListAdapter);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Utils.getPersistentUserInfo().token);
        HttpConstant.POST(bhandler, HttpConstant.YaoURL,map,false);
        initdata();
    }

    private void initdata() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token",Utils.getPersistentUserInfo().token);
        HttpConstant.POST(handler, HttpConstant.MyYaoqingURL,map,false);
    }

    @OnClick(R.id.back)
    public void onClick() {
        this.finish();
    }

    /**
     *我的邀请
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    List<MyYaoqingBean> myYaoqingBeen = HttpUtils.DNSMyYaoqing(msg.obj.toString());
                    if(myYaoqingBeen!=null && myYaoqingBeen.size()>0){
                        myYaoListAdapter.setList(myYaoqingBeen);
                        listview.setVisibility(View.VISIBLE);
                        errorLayout.setVisibility(View.GONE);
                    }else {
                        listview.setVisibility(View.GONE);
                        errorLayout.setVisibility(View.VISIBLE);
                        errorText.setText("暂无记录");
                    }
                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler bhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("用户分享json",msg.obj.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int status = jsonObject.getInt("code");
                        if(status==200){
                            JSONObject data = jsonObject.getJSONObject("data");
                            String invite_num = data.getString("invite_num");
                            String award_coin = data.getString("award_coin");
                            ptb.setText(award_coin);
                            ren.setText(invite_num);
                        }else{
                            ptb.setText("0");
                            ren.setText("0");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("解析用户分享出错",e.toString());
                        ptb.setText("0");
                        ren.setText("0");
                    }
                    break;
                case 2:
                    ptb.setText("0");
                    ren.setText("0");
                    break;
                default:
                    break;
            }
        }
    };
}
