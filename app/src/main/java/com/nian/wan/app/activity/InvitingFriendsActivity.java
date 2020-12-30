package com.nian.wan.app.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.ShareInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;
import com.blankj.utilcode.util.SpannableStringUtils;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 邀请好友Activity
 */
public class InvitingFriendsActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.xx)
    TextView xx;
    @BindView(R.id.pingtaibi)
    TextView pingtaibi;
    @BindView(R.id.cc)
    TextView cc;
    @BindView(R.id.haoyou)
    TextView haoyou;
    @BindView(R.id.guize)
    TextView guize;
    @BindView(R.id.yaoqing)
    TextView yaoqing;

    private SpannableStringBuilder spannableStringBuilder;

    @Override
    public void initView() {
        setContentView(R.layout.activity_inviting_friends);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("邀请好友");
        textStyle();
    }


    private void textStyle() {
        int bstart= HttpConstant.fenxiang.indexOf("5%");
        int bend=bstart+"5%".length();
        SpannableStringBuilder style=new SpannableStringBuilder(HttpConstant.fenxiang);
        style.setSpan(new ForegroundColorSpan(Color.RED),bstart,bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new RelativeSizeSpan(1.3f),bstart,bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guize.setText(style);

        pingtaibi.setText("0");
        haoyou.setMovementMethod(LinkMovementMethod.getInstance());
        spannableStringBuilder = SpannableStringUtils.getBuilder("0").setUnderline().create();
        haoyou.setText(spannableStringBuilder);

        if (Utils.getPersistentUserInfo()!=null){
            initdata();
        }
    }



    @OnClick({R.id.back, R.id.yaoqing,R.id.haoyou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yaoqing:
                if (Utils.getPersistentUserInfo()!=null){
                    Share();
                }else {
                    new DialogGoLogin(InvitingFriendsActivity.this, R.style.MyDialogStyle, "暂时无法邀请好友~T_T~").show();
                }
                break;
            case R.id.haoyou:
                if (Utils.getPersistentUserInfo()!=null){
                    startActivity(new Intent(this, MyInvitationActivity.class));
                }else {
                    new DialogGoLogin(InvitingFriendsActivity.this, R.style.MyDialogStyle, "暂时无法查看邀请信息~T_T~").show();
                }
                break;
        }
    }


    /**
     * 邀请好友
     */
    private void Share() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token",Utils.getLoginUser().token);
        HttpConstant.POST(bhandler, HttpConstant.ShareURL,map,false);
    }
    @SuppressLint("HandlerLeak")
    Handler bhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try{
                        Log.e("获取邀请好友邀请参数",msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        int code = jsonObject.getInt("code");
                        if (code == 200){
                            ShareInfo shareInfo = new ShareInfo();
                            shareInfo.logoUrl = jsonData.getString("icon");
                            shareInfo.shareUrl = jsonData.getString("url");
                            shareInfo.text = jsonData.getString("contend");
                            shareInfo.title = jsonData.getString("title");

                            Intent mIntent = new Intent(InvitingFriendsActivity.this, ShareQRCodeActivity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable("shareInfo", shareInfo);
                            mIntent.putExtras(mBundle);
                            mIntent.putExtra("name",1);
                            startActivity(mIntent);
                        }
                    }catch (Exception e){

                    }
                    break;
                case 2:

                    break;
                default:
                    break;
            }
        }
    };




    /**
     * 邀请用户记录信息
     */
    private void initdata() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token",Utils.getLoginUser().token);
        HttpConstant.POST(handler, HttpConstant.YaoURL,map,false);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
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
                            pingtaibi.setText(award_coin);
                            spannableStringBuilder = SpannableStringUtils.getBuilder(invite_num).setUnderline().create();
                            haoyou.setText(spannableStringBuilder);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("解析用户分享出错",e.toString());
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

}
