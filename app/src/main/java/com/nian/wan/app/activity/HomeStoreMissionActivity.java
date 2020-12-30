package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.mc.developmentkit.utils.ToastUtil;
import android.widget.LinearLayout;;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * @Author: XiaYuShi
 * @Date: 2017/11/24
 * @Description: 首页商城积分任务
 * @Modify By:
 * @ModifyDate:
 */
public class HomeStoreMissionActivity extends BaseFragmentActivity {
    @BindView(R.id.tv_home_store_mission_back)
    ImageView tvHomeStoreMissionBack;
    @BindView(R.id.ll_home_store_score_mission_back)
    LinearLayout llHomeStoreScoreMissionBack;
    @BindView(R.id.img_home_store_score_mission_record)
    ImageView imgHomeStoreScoreMissionRecord;
    @BindView(R.id.img_home_store_score_mission_help)
    ImageView imgHomeStoreScoreMissionHelp;
    @BindView(R.id.tv_home_store_score_mission_mine_score_tip)
    TextView tvHomeStoreScoreMissionMineScoreTip;
    @BindView(R.id.tv_home_store_score_mission_mine_score)
    TextView tvHomeStoreScoreMissionMineScore;
    @BindView(R.id.img_home_store_score_mission)
    ImageView imgHomeStoreScoreMission;
    @BindView(R.id.tv_home_store_score_mission_go_sign)
    TextView tvHomeStoreScoreMissionGoSign;
    @BindView(R.id.ll_home_store_sign_layout)
    RelativeLayout llHomeStoreSignLayout;
    @BindView(R.id.img_home_store_score_mission_bind_phone)
    ImageView imgHomeStoreScoreMissionBindPhone;
    @BindView(R.id.tv_home_store_score_mission_go_bind_phone)
    TextView tvHomeStoreScoreMissionGoBindPhone;
    @BindView(R.id.ll_home_store_bind_phone_layout)
    RelativeLayout llHomeStoreBindPhoneLayout;
    @BindView(R.id.img_home_store_score_mission_first_pay)
    ImageView imgHomeStoreScoreMissionFirstPay;
    @BindView(R.id.tv_home_store_score_mission_start_first_pay)
    TextView tvHomeStoreScoreMissionStartFirstPay;
    @BindView(R.id.ll_home_store_first_pay_layout)
    RelativeLayout llHomeStoreFirstPayLayout;
    @BindView(R.id.img_home_store_score_mission_game_pay)
    ImageView imgHomeStoreScoreMissionGamePay;
    @BindView(R.id.tv_home_store_score_mission_start_game_pay)
    TextView tvHomeStoreScoreMissionStartGamePay;
    @BindView(R.id.ll_home_store_game_pay_layout)
    RelativeLayout llHomeStoreGamePayLayout;
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.tv_sign_title)
    TextView tvSignTitle;
    @BindView(R.id.tv_sign_message)
    TextView tvSignMessage;
    @BindView(R.id.tv_sign_point)
    TextView tvSignPoint;
    @BindView(R.id.tv_bind_title)
    TextView tvBindTitle;
    @BindView(R.id.tv_bind_message)
    TextView tvBindMessage;
    @BindView(R.id.tv_bind_point)
    TextView tvBindPoint;
    @BindView(R.id.tv_rechargegame_title)
    TextView tvRechargegameTitle;
    @BindView(R.id.tv_rechargegame_message)
    TextView tvRechargegameMessage;
    @BindView(R.id.tv_rechargegame_point)
    TextView tvRechargegamePoint;
    @BindView(R.id.tv_recharge_title)
    TextView tvRechargeTitle;
    @BindView(R.id.tv_recharge_message)
    TextView tvRechargeMessage;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.tv_yaoqing_message)
    TextView tvYaoqingMsg;
    @BindView(R.id.tv_home_store_score_mission_start_yaoqing)
    TextView btnYaoqing;
    @BindView(R.id.tv_yaoqing_point)
    TextView tvYaoqingPoint;

    private HomeBroadcast homeBroadcast;
    private boolean canFinish;

    @Override
    public void initView() {
        setContentView(R.layout.home_store_score_mission);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        int key = getIntent().getIntExtra("key", 0);
        if (key == 2) {
            tvShop.setVisibility(View.INVISIBLE);
        }
        regsiterHomeBrodcast(); //注册本地登录广播
    }


    @OnClick({R.id.ll_home_store_score_mission_back, R.id.img_home_store_score_mission_record,
            R.id.img_home_store_score_mission_help, R.id.tv_home_store_score_mission_start_yaoqing,
            R.id.tv_home_store_score_mission_go_sign, R.id.tv_home_store_score_mission_go_bind_phone,
            R.id.tv_home_store_score_mission_start_first_pay,
            R.id.tv_home_store_score_mission_start_game_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home_store_score_mission_back:
                this.finish();
                break;
            case R.id.img_home_store_score_mission_record:
                if (null != Utils.getPersistentUserInfo()) {
                    Intent storeScoreRecordIntent = new Intent(this, HomeStoreScoreRecordActivity.class);
                    this.startActivity(storeScoreRecordIntent);
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(this, "LGOIN");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.img_home_store_score_mission_help:
                Intent storeRegularIntent = new Intent(this, HomeStoreRegularActivity.class);
                this.startActivity(storeRegularIntent);
                break;
            case R.id.tv_home_store_score_mission_go_sign:  //去签到
                if (Utils.getPersistentUserInfo() != null) {
                    getSign();
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(this, "LGOIN");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tv_home_store_score_mission_go_bind_phone:  //去绑定
                if (Utils.getPersistentUserInfo() != null) {
                    startActivity(new Intent(this, BindPhoneActivity.class));
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(this, "LGOIN");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tv_home_store_score_mission_start_first_pay: //首充
                if (Utils.getPersistentUserInfo() != null) {
                    jumpToGameList();
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(this, "LGOIN");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tv_home_store_score_mission_start_game_pay:  //充值
                if (Utils.getPersistentUserInfo() != null) {
                    jumpToGameList();
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(this, "LGOIN");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tv_home_store_score_mission_start_yaoqing:  //去邀请
                if (Utils.getPersistentUserInfo() != null) {
                    startActivity(new Intent(HomeStoreMissionActivity.this, InvitingFriendsActivity.class));
                } else {
                    DialogLoginActivity loginActivity = new DialogLoginActivity(this, "LGOIN");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }
                break;
        }
    }

    /**
     * 跳转到游戏列表
     */
    public void jumpToGameList() {
        EvenBean evenBean = new EvenBean();
        evenBean.b = 6;
        EventBus.getDefault().post(evenBean);
        Intent recommednGameIntent = new Intent("com.yinu.change.viewpage.index");
        recommednGameIntent.putExtra("change_status", PersonalCenter.ACTION_GO_RECOMMEND_SY_GAME);
        LocalBroadcastManager.getInstance(this).sendBroadcast(recommednGameIntent);
        finish();
    }


    /**
     * 获取积分任务
     */
    private void getTask() {
        Map<String, String> map = new HashMap<>();
        if (Utils.getPersistentUserInfo() != null) {
            map.put("token", Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(taskHandler, HttpConstant.API_SHOP_TASK, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler taskHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("获取积分任务返回数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            JSONObject jsonSign = jsonData.getJSONObject("sign_in");
                            JSONObject jsonBindPhone = jsonData.getJSONObject("bind_phone");
                            JSONObject jsonRechargeGame = jsonData.getJSONObject("share_game");
                            JSONObject jsonRecharge = jsonData.getJSONObject("share_article");
                            JSONObject jsonInviteFriend = jsonData.getJSONObject("invite_friend");
                            JSONObject jsonUserPoint = jsonData.getJSONObject("user_point");

                            tvHomeStoreScoreMissionMineScore.setText(jsonUserPoint.getString("shop_point"));
                            tvSignPoint.setText(jsonSign.getString("addpoint"));
                            tvBindPoint.setText(jsonBindPhone.getString("point"));
                            tvRechargegamePoint.setText(jsonRechargeGame.getString("point"));
                            tvYaoqingPoint.setText(jsonInviteFriend.getString("point"));
                            tvRechargeMessage.setText("每充值1元即可获得" + jsonRecharge.getString("point") + "积分");
                            tvYaoqingMsg.setText("每邀请成功一个玩家即可获得" + jsonInviteFriend.getString("point") + "积分");


                            if (jsonSign.getInt("is_complete") == 1) {
                                tvSignPoint.setText(jsonSign.getString("topoint"));
                                tvHomeStoreScoreMissionGoSign.setText("已签到");
                                tvSignMessage.setText("明日签到可获得积分");
                                tvHomeStoreScoreMissionGoSign.setTextColor(getResources().getColor(R.color.zi_hui2));
                                tvHomeStoreScoreMissionGoSign.setBackgroundResource(R.drawable.zhi_jiao_gray);
                            }
                            if (jsonBindPhone.getInt("is_complete") == 1) {
                                tvHomeStoreScoreMissionGoBindPhone.setEnabled(false);
                                tvHomeStoreScoreMissionGoBindPhone.setText("已绑定");
                                tvHomeStoreScoreMissionGoBindPhone.setTextColor(getResources().getColor(R.color.zi_hui2));
                                tvHomeStoreScoreMissionGoBindPhone.setBackgroundResource(R.drawable.zhi_jiao_gray);
                            }

                            if (jsonRechargeGame.getInt("is_complete") == 1) {
                                tvHomeStoreScoreMissionStartFirstPay.setEnabled(false);
                                tvHomeStoreScoreMissionStartFirstPay.setText("已首充");
                                tvHomeStoreScoreMissionStartFirstPay.setTextColor(getResources().getColor(R.color.zi_hui2));
                                tvHomeStoreScoreMissionStartFirstPay.setBackgroundResource(R.drawable.zhi_jiao_gray);
                            }

                        }
                    } catch (Exception e) {
                        Log.e("获取积分任务数据异常", e.toString());
                    }
                    break;
                case 2:
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        getTask();
    }

    /**
     * 获取签到页面
     */
    private void getSign() {
        if (Utils.getPersistentUserInfo() != null) {
            Map<String, String> map = new HashMap<>();
            map.put("token", Utils.getPersistentUserInfo().token);
            HttpConstant.POST(singinHandler, HttpConstant.API_SHOP_SIGN, map, false);
        } else {
            ToastUtil.showToast("请先登录~");
        }
    }

    @SuppressLint("HandlerLeak")
    Handler singinHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("获取签到页面返回数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            String signURL = jsonObject.getString("data") + "/token/" + Utils.getPersistentUserInfo().token;
                            Intent intent = new Intent(HomeStoreMissionActivity.this, SignWebActivity.class);
                            intent.putExtra("name", "签到送积分");
                            intent.putExtra("url", signURL);
                            intent.putExtra("share", true);
                            startActivityForResult(intent, 1);
                            canFinish = true;
                        }
                    } catch (Exception e) {
                        Log.e("获取签到页面数据异常", e.toString());
                        ToastUtil.showToast("获取签到页面数据异常，请稍候重试");
                    }
                    break;
                case 2:
                    ToastUtil.showToast("网络缓慢，请稍候重试");
                    break;
            }
        }
    };


    /**
     * 注册登录广播
     */
    private void regsiterHomeBrodcast() {
        homeBroadcast = new HomeBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(this).registerReceiver(homeBroadcast, intentFilter);
    }

    /**
     * 用户登录回调
     */
    private class HomeBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                //登录成功
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    getTask();
                    break;
                //登录失败
                case DialogLoginActivity.EVENT_LOGIN_FILED:
                    break;
                //注销登录
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    break;
            }
        }

    }


    //Activityb传回来的数据在这个方法中获取
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            int result = data.getIntExtra("result", 0);
            if (result == 1) {
                finish();
            }
        }
    }

}
