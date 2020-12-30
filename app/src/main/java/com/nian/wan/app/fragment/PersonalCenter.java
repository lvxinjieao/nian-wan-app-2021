package com.nian.wan.app.fragment;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.AboutUsActivity;
import com.nian.wan.app.activity.AddressActivity;
import com.nian.wan.app.activity.AlreadyBindPhoneActivity;
import com.nian.wan.app.activity.AlreadyRealNameActivity;
import com.nian.wan.app.activity.BalanceActivity;
import com.nian.wan.app.activity.BindPhoneActivity;
import com.nian.wan.app.activity.ChangePasswordActivity;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.activity.MyGameActivity;
import com.nian.wan.app.activity.MyGiftActivity;
import com.nian.wan.app.activity.MyInvitationActivity;
import com.nian.wan.app.activity.MyOrderActivity;
import com.nian.wan.app.activity.PayActivity;
import com.nian.wan.app.activity.RealNameActivity;
import com.nian.wan.app.activity.UserMessageCenterActivity;
import com.nian.wan.app.bean.IsShowBean;
import com.nian.wan.app.bean.UserIsBindPhoneBean;
import com.nian.wan.app.bean.UserIsRealNameBean;
import com.nian.wan.app.bean.UserMessageBean;
import com.nian.wan.app.bean.UserSignBean;
import com.nian.wan.app.db.UserInfoDB;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogChangePwdSuccess;
import com.nian.wan.app.view.DialogExcitConfirm;
import com.nian.wan.app.view.DialogLoginPopTipBind;
import com.nian.wan.app.view.ShapeImageView;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ezy.ui.view.BadgeButton;


/**
 * @Description: 个人中心
 */
public class PersonalCenter extends Fragment {

    /////////////////////////////////////////////////////////////////////////
    //未登录布局
    @BindView(R.id.personal_center_unlogin_layout)
    LinearLayout personal_center_unlogin_layout;
    //登录
    @BindView(R.id.personal_center_login)
    TextView mTvPersonalCenterLogin;
    //注册
    @BindView(R.id.personal_center_register)
    TextView mTvPersonalCenterRegister;
    /////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    //已登录布局
    @BindView(R.id.personal_center_already_login_layout)
    LinearLayout personal_center_already_login_layout;

    //已登录头像
    @BindView(R.id.personal_center_already_user_icon)
    ShapeImageView already_user_icon;

    //已登录昵称
    @BindView(R.id.personal_center_already_nickname)
    TextView mTvAlreadyNickname;

    //已登录Uid
    @BindView(R.id.personal_center_uid)
    TextView uid;

    //已登录签到
    @BindView(R.id.personal_already_sign)
    TextView sign;


    @BindView(R.id.tv_hint_point)
    TextView tvHintPoint;

    @BindView(R.id.img_red_hint)
    ImageView imgRedHint;
    /////////////////////////////////////////////////////////////////

    //充值
    @BindView(R.id.rl_personal_pay)
    RelativeLayout mRlPersonalPay;

    //余额
    @BindView(R.id.rl_personal_balance)
    RelativeLayout mRlPersonalBalance;

    //实名认证
    @BindView(R.id.rl_personal_center_real_name)
    RelativeLayout mRlPersonalRealName;

    //绑定手机号
    @BindView(R.id.rl_personal_center_bind_phone)
    RelativeLayout mRlPersonalCenterBindPhone;

    //已绑定手机号
    @BindView(R.id.tv_personal_center_already_bind_phone)
    TextView tvPersonalCenterAlreadyBindPhone;

    //绑定手机号送积分提示
    @BindView(R.id.tv_personal_center_already_bind_phone_tips)
    TextView tvPersonalCenterBindPhoneTips;

    //未实名认证
    @BindView(R.id.tv_home_persoanl_center_un_real_name)
    TextView tvPersonalCenterUnRealName;

    //已实名认证
    @BindView(R.id.ll_personal_center_already_real_name)
    LinearLayout llPersonalCenterAlreadyRealName;

    //修改密码
    @BindView(R.id.rl_personal_center_change_password)
    RelativeLayout mRlPersonalCneterChangerPassword;

    //添加地址
    @BindView(R.id.rl_personal_center_address)
    RelativeLayout mRlPersonalCenterAddress;

    //我的礼包
    @BindView(R.id.rl_personal_center_package)
    RelativeLayout mRlPersonalCenterPackage;

    //我的订单
    @BindView(R.id.rl_personal_center_order)
    RelativeLayout mRlPersonalCenterOrder;

    //联系我们
    @BindView(R.id.rl_personal_center_contact_us)
    RelativeLayout mRlPersonalCenterConcatUs;

    //我的游戏
    @BindView(R.id.rl_personal_center_mygame)
    RelativeLayout mRlPersonalMyGame;

    //我的邀请
    @BindView(R.id.rl_personal_center_myyaoqing)
    RelativeLayout mRlPersonalMyYaoqing;




    //客服
    @BindView(R.id.btn_kefu)
    ImageView btnKefu;

    //已登录消息中心
    @BindView(R.id.img_personal_center_message)
    BadgeButton mImgAlreadyMessage;

    //已登录注销
    @BindView(R.id.img_personal_center_login_out)
    ImageView mImgAlreadyLoginOut;


    //用户是否绑定手机号
    private UserIsBindPhoneBean isBindPhoneBean;
    //用户是否实名认证
    private UserIsRealNameBean isRealNameBean;
    //是否开启实名认证
    private Integer isOpendRealNameBean;
    private PersonalBroadcast personalBroadcast;
    //用户消息
    private ArrayList<UserMessageBean> userMessageBean;
    private UserSignBean userSignBean;

    //登录
    private static final int ACTION_LOGIN = 0;
    //注册
    private static final int ACTION_REGISTER = 1;
    //充值
    private static final int ACTION_PAY = 2;
    //余额
    private static final int ACTION_BALANCE = 3;

    //实名认证
    public static final int ACTION_REAL_NAME = 6;
    //绑定手机号
    public static final int ACTION_BIND_PHONE = 7;
    //修改密码
    public static final int ACTION_CHANGE_PASSWORD = 8;
    //添加地址
    public static final int ACTION_ADD_ADDRESS = 9;
    //我的游戏
    public static final int ACTION_MYGAME = 10;
    //我的礼包
    public static final int ACTION_PACKAGE = 11;
    //联系我们
    public static final int ACTION_CONTACT_US = 12;
    //注销
    public static final int ACTION_LOGIN_OUT = 13;
    //消息中心
    public static final int ACTION_MESSAGE_CENTER = 14;
    //实名认证成功返回码
    public static final int AUTH_REAL_NAME_SUCCESS = 15;
    //实名认证失败返回码
    public static final int AUTH_REAL_NAME_FAILED = 16;
    //修改密码成功
    public static final int CHANGE_PASSWORD_SUCCESS = 17;
    //修改密码失败
    public static final int CHANGE_PASSWORD_FAILED = 18;
    //签到
    public static final int ACTION_SIGN = 19;
    //余额去兑换
    public static final int ACTION_GO_EXCHANGE = 20;
    //显示个人中心红点提示
    public static final int ACTION_NEED_SHOW_RED_POINT = 21;
    //定位到热门游戏
    public static final int ACTION_GO_HOT_GAME = 22;
    //绑定手机号成功
    public static final int BIND_PHONE_SUCCESS = 23;
    //定位到推荐游戏 H5游戏
    public static final int ACTION_GO_RECOMMEND_H5_GAME = 24;
    //未读消息
    public static final int ACTION_NEED_SHOW_RED_POINT2 = 25;
    //已读消息
    public static final int ACTION_UN_SHOW_RED_POINT2 = 26;
    //实名认证返回详情页
    public static final int ACTION_REAL_NAME_DETAIL = 27;
    //定位到推荐礼包
    public static final int ACTION_GO_RECOMMEND_GIFT = 28;
    //我的订单
    public static final int ACTION_MY_ORDER = 29;
    //我的邀请
    public static final int ACTION_MYYAOQING = 30;

    //定位到推荐游戏 手游游戏
    public static final int ACTION_GO_RECOMMEND_SY_GAME = 31;
    //定位到热门游戏 手游游戏
    public static final int ACTION_GO_HOT_SY_GAME = 32;
    //定位到新上架游戏 手游游戏
    public static final int ACTION_GO_NEW_SY_GAME = 33;

    private String TAG = "PersonalCenter";
    private TranslateAnimation translateAnimation;
    private boolean ShowedAnimation;
    private String signPoint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_center, null);
        ButterKnife.bind(this, view);
        //是否绑定手机号放在这里请求是为了避免多次弹出绑定手机号弹窗
        isTipsShow();
        initView();
        regsiterPersonalBroadcast();
        return view;
    }


    private class PersonalBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("login_status", -1)) {
                //登录成功
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS:
                    isTipsShow();
                    initView();
                    break;
                //登录失败
                case DialogLoginActivity.EVENT_LOGIN_FILED:
                    break;
                //注销登录
                case DialogLoginActivity.EVENT_LOGIN_EXCIT:
                    personal_center_unlogin_layout.setVisibility(View.VISIBLE);
                    personal_center_already_login_layout.setVisibility(View.GONE);
                    mImgAlreadyMessage.setVisibility(View.GONE);
                    mImgAlreadyLoginOut.setVisibility(View.GONE);

                    tvPersonalCenterBindPhoneTips.setVisibility(View.GONE);
                    llPersonalCenterAlreadyRealName.setVisibility(View.GONE);
                    tvPersonalCenterUnRealName.setVisibility(View.GONE);

                    sign.setText("签到");
                    sign.setEnabled(true);
                    sign.setAlpha(1f);
                    sign.setVisibility(View.VISIBLE);


                    tvPersonalCenterAlreadyBindPhone.setVisibility(View.GONE);
                    tvPersonalCenterBindPhoneTips.setVisibility(View.VISIBLE);
                    if (isBindPhoneBean != null) {
                        tvPersonalCenterBindPhoneTips.setText("首次绑定送" + isBindPhoneBean.getBind_point() + "积分");
                    } else {
                        Utils.TS("isBindPhoneBean等于空");
                    }
                    break;
                //已签到
                case DialogLoginActivity.ACTION_ALREADY_SIGN:
                    if (!ShowedAnimation) {
                        sign.setText("已签到");
                        sign.setTextColor(getResources().getColor(R.color.ffffff));
                        sign.setAlpha(0.6f);
                    } else {
                        sign.setVisibility(View.GONE);
                    }
                    imgRedHint.setVisibility(View.INVISIBLE);
                    break;
            }
        }

    }

    /**
     * 注册广播和各个模块进行联动
     */
    private void regsiterPersonalBroadcast() {
        personalBroadcast = new PersonalBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(personalBroadcast, intentFilter);
    }

    /**
     * 初始化用户信息
     */
    private void initView() {
        //数据库已存在登录用户才可以进行用户数据加载
        if (null != Utils.getPersistentUserInfo()) {
            //用户uid
            uid.setText(Utils.getPersistentUserInfo().uid);
            //用户账户名
            mTvAlreadyNickname.setText(Utils.getPersistentUserInfo().account);
            //隐藏未登录布局
            personal_center_unlogin_layout.setVisibility(View.GONE);
            //显示已登录布局
            personal_center_already_login_layout.setVisibility(View.VISIBLE);
            //消息中心按钮显示
            mImgAlreadyMessage.setVisibility(View.VISIBLE);
            //注销按钮显示
            mImgAlreadyLoginOut.setVisibility(View.VISIBLE);

            String user_icon = Utils.getPersistentUserInfo().portrait;
            if (!TextUtils.isEmpty(user_icon)) Glide.with(getActivity()).load(user_icon).into(already_user_icon);
            //是否绑定手机号
            isUSerBindPhone();
            //是否实名认证
            isUserRealName();
            //是否开启实名认证
            isOpendRealName();
            //是否有新的消息
            getUserMessage();
        } else {
            //如果未登录则签到按钮显示红点
            imgRedHint.setVisibility(View.VISIBLE);
            //绑定手机号送积分隐藏
            tvPersonalCenterBindPhoneTips.setVisibility(View.GONE);
            //未实名认证隐藏
            tvPersonalCenterUnRealName.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.personal_center_login, R.id.personal_center_register,

            R.id.rl_personal_center_mygame,
            R.id.rl_personal_pay, R.id.rl_personal_balance, R.id.rl_personal_center_real_name,
            R.id.rl_personal_center_bind_phone, R.id.rl_personal_center_change_password,
            R.id.rl_personal_center_address, R.id.rl_personal_center_package, R.id.rl_personal_center_order, R.id.rl_personal_center_contact_us,
            R.id.img_personal_center_login_out, R.id.img_personal_center_message, R.id.rl_personal_center_myyaoqing,
            R.id.personal_already_sign, R.id.btn_kefu})
    public void onClick(View view) {

        switch (view.getId()) {
            //登录
            case R.id.personal_center_login:
                doClick(ACTION_LOGIN);
                break;
            //注册
            case R.id.personal_center_register:
                doClick(ACTION_REGISTER);
                break;

            //签到
            case R.id.personal_already_sign:
                doClick(ACTION_SIGN);
                break;

            //注销
            case R.id.img_personal_center_login_out:
                doClick(ACTION_LOGIN_OUT);
                break;
            //消息
            case R.id.img_personal_center_message:
                doClick(ACTION_MESSAGE_CENTER);
                break;
            //客服
            case R.id.btn_kefu:
                Utils.talkWithQQCustom(getActivity());
                break;







            //充值
            case R.id.rl_personal_pay:
                doClick(ACTION_PAY);
                break;
            //余额
            case R.id.rl_personal_balance:
                doClick(ACTION_BALANCE);
                break;
            //实名认证
            case R.id.rl_personal_center_real_name:
                doClick(ACTION_REAL_NAME);
                break;
            //绑定手机号
            case R.id.rl_personal_center_bind_phone:
                doClick(ACTION_BIND_PHONE);
                break;
            //修改密码
            case R.id.rl_personal_center_change_password:
                doClick(ACTION_CHANGE_PASSWORD);
                break;
            //添加地址
            case R.id.rl_personal_center_address:
                doClick(ACTION_ADD_ADDRESS);
                break;
            //我的礼包
            case R.id.rl_personal_center_package:
                doClick(ACTION_PACKAGE);
                break;
            //我的游戏
            case R.id.rl_personal_center_mygame:
                doClick(ACTION_MYGAME);
                break;
            //我的邀请
            case R.id.rl_personal_center_myyaoqing:
                doClick(ACTION_MYYAOQING);
                break;
            case R.id.rl_personal_center_order:
                doClick(ACTION_MY_ORDER);
                break;
            //联系我们
            case R.id.rl_personal_center_contact_us:
                doClick(ACTION_CONTACT_US);
                break;


        }
    }

    /**
     * 响应点击时间并调用相关视图
     *
     * @param action
     */
    public void doClick(int action) {
        //未登录则拉起登录框
        UserInfoDB loginUser = Utils.getPersistentUserInfo();
        if (loginUser == null && action != ACTION_CONTACT_US && action != ACTION_REGISTER) {
            DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LOGIN");
            FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
            transaction.add(loginActivity, "WoDe");
            transaction.show(loginActivity);
            transaction.commitAllowingStateLoss();
        } else {
            switch (action) {
                //登录
                case ACTION_LOGIN:
                    showLoginDoalog();
                    break;
                //签到
                case ACTION_SIGN:
                    if (!"已签到".equals(sign.getText().toString())) {
                        //未签到直接进行签到
                        doSign();
                    }
                    break;
                //注册
                case ACTION_REGISTER:
                    DialogLoginActivity registerActivity =
                            new DialogLoginActivity(getContext(), "REGISTER");
                    FragmentTransaction registerActivityTransaction = getActivity()
                            .getFragmentManager()
                            .beginTransaction();
                    registerActivityTransaction.add(registerActivity, "WoDe");
                    registerActivityTransaction.show(registerActivity);
                    registerActivityTransaction.commitAllowingStateLoss();
                    break;
                //充值
                case ACTION_PAY:
                    if (Utils.getPersistentUserInfo() != null) {
                        this.startActivityForResult(new Intent(getContext(), PayActivity.class), 0);
                    } else {
                        DialogLoginActivity dialogLogin = new DialogLoginActivity(getContext(), "LGOIN");
                        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                        ft.add(dialogLogin, "WoDe");
                        ft.show(dialogLogin);
                        ft.commitAllowingStateLoss();
                    }
                    break;
                //余额
                case ACTION_BALANCE:
                    if (Utils.getPersistentUserInfo() != null) {
                        this.startActivityForResult(new Intent(getContext(), BalanceActivity.class), 0);
                    } else {
                        DialogLoginActivity dialogLogin = new DialogLoginActivity(getContext(), "LGOIN");
                        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                        ft.add(dialogLogin, "WoDe");
                        ft.show(dialogLogin);
                        ft.commitAllowingStateLoss();
                    }
                    break;
                //实名认证
                case ACTION_REAL_NAME:
                    if (0 == Integer.parseInt(isRealNameBean.getAge_status())) {
                        Intent intentRealName = new Intent(getContext(), RealNameActivity.class);
                        intentRealName.putExtra("is_opend_real_name", String.valueOf(isOpendRealNameBean));
                        this.startActivityForResult(intentRealName, 0);
                    } else {
                        Intent intent = new Intent(getContext(), AlreadyRealNameActivity.class);
                        intent.putExtra("real_name", isRealNameBean
                                .getReal_name());
                        intent.putExtra("id_card", isRealNameBean
                                .getIdcard());
                        this.startActivityForResult(intent, 0);
                    }
                    break;
                //绑定手机号
                case ACTION_BIND_PHONE:
                    if (isBindPhoneBean != null) {
                        if (1 == isBindPhoneBean.getUserbind()) {
                            Intent intent = new Intent(getContext(), AlreadyBindPhoneActivity.class);
                            intent.putExtra("mobile", isBindPhoneBean.getPhone());
                            this.startActivityForResult(intent, 0);
                        } else {
                            this.startActivityForResult(new Intent(getContext(),
                                    BindPhoneActivity.class), 0);
                        }
                    } else {
                        Utils.TS("isBindPhoneBean等于空");
                    }
                    break;
                //修改密码
                case ACTION_CHANGE_PASSWORD:
                    this.startActivityForResult(new Intent(getContext(),
                            ChangePasswordActivity.class), 0);
                    break;
                //添加地址
                case ACTION_ADD_ADDRESS:
                    this.startActivityForResult(new Intent(getContext(), AddressActivity.class),
                            0);
                    break;
                //我的礼包
                case ACTION_PACKAGE:
                    this.startActivityForResult(new Intent(getContext(), MyGiftActivity.class), 0);
//                this.startActivity(new Intent(getContext(), MyGiftNoneGiftActivity.class));
                    break;
                //我的游戏
                case ACTION_MYGAME:
                    this.startActivityForResult(new Intent(getContext(), MyGameActivity.class), 0);
                    break;
                //我的邀请
                case ACTION_MYYAOQING:
                    this.startActivityForResult(new Intent(getContext(), MyInvitationActivity.class), 0);
                    break;
                //我的订单
                case ACTION_MY_ORDER:
                    this.startActivity(new Intent(getContext(), MyOrderActivity.class));
                    break;
                //注销
                case ACTION_LOGIN_OUT:
//                    exictLogin();
                    new DialogExcitConfirm(getContext(), R.style.MyDialogStyle).show();
                    break;
                //消息中心
                case ACTION_MESSAGE_CENTER:
                    mImgAlreadyMessage.setBadgeVisible(false);
                    Intent redPointIntent = new Intent("com.yinu.change.viewpage.index");
                    redPointIntent.putExtra("change_status", ACTION_UN_SHOW_RED_POINT2);
                    LocalBroadcastManager.getInstance(getActivity())
                            .sendBroadcast(redPointIntent);
                    Intent intent = new Intent(getActivity(), UserMessageCenterActivity.class);
                    this.startActivityForResult(intent, 0);
                    break;
                //联系我们
                case ACTION_CONTACT_US:
                    this.startActivityForResult(new Intent(getContext(), AboutUsActivity.class), 0);
                    break;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        //getUserInfo();
    }


    /**
     * 显示登录弹窗
     */
    private void showLoginDoalog() {
        DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LGOIN");
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        transaction.add(loginActivity, "WoDe");
        transaction.show(loginActivity);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 直接进行签到
     */
    private void doSign() {
        Map<String, String> signParams = new HashMap<>();
        if (null != Utils.getPersistentUserInfo()) {
            signParams.put("token", Utils.getPersistentUserInfo().token);
        }
        HttpConstant.POST(singnHandler, HttpConstant.API_PERSONAL_USER_SIGN, signParams, true);
    }

    @SuppressLint("HandlerLeak")
    Handler singnHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        Log.e("个人中心签到数据", msg.obj.toString());
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.optInt("code", -1);
                        if (200 == code) {
                            modifyUiWithSign(true);
                        } else {
                            ToastUtil.showToast(jsonObject.optString("msg"));
                            //getUserInfo();
                            initView();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "签到异常:" + e.toString());
                    }
                    break;
                case 2:
                    ToastUtil.showToast("签到网络缓慢");
                    break;
            }
        }
    };


    /**
     * 更新为已签到的UI
     */
    private void modifyUiWithSign(boolean needShowAnimation) {
        if (needShowAnimation) {
            ShowedAnimation = true;
            sign.setVisibility(View.GONE);
            translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.translate);
            if (signPoint != null) {
                tvHintPoint.setText("+" + signPoint);
            }
            tvHintPoint.startAnimation(translateAnimation);
            tvHintPoint.setTextColor(getResources().getColor(R.color.ffffff));
            tvHintPoint.setAlpha(0.6f);
            UIHander.sendEmptyMessageDelayed(1, 900);
        } else {
            ShowedAnimation = false;
            sign.setVisibility(View.VISIBLE);
            sign.setText("已签到");
            sign.setTextColor(getResources().getColor(R.color.ffffff));
            sign.setAlpha(0.6f);
        }
        imgRedHint.setVisibility(View.INVISIBLE);

        Intent intent = new Intent("com.yinu.login");
        intent.putExtra("login_status", DialogLoginActivity.ACTION_ALREADY_SIGN);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

        //通知首页底部导航栏
        Intent intentSign = new Intent("com.yinu.change.viewpage.index");
        intentSign.putExtra("change_status", DialogLoginActivity.ACTION_ALREADY_SIGN);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intentSign);
    }

    @SuppressLint("HandlerLeak")
    private Handler UIHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvHintPoint.startAnimation(translateAnimation);
                    sign.setVisibility(View.VISIBLE);
                    sign.setText("已签到");
                    sign.setTextColor(getResources().getColor(R.color.ffffff));
                    sign.setAlpha(0.6f);
                    break;
            }
        }
    };


    private void getUserMessage() {
        Map<String, String> messageParmas = new HashMap<>();
        Type type = new TypeToken<HttpResult<ArrayList<UserMessageBean>>>() {}.getType();
        messageParmas.put("p", "1");

        new HttpUtils<ArrayList<UserMessageBean>>(type, HttpConstant.API_PERSONAL_USER_MESSAGE, messageParmas, "获取用户消息", true) {

            @Override
            protected void _onSuccess(ArrayList<UserMessageBean> bean, String msg) {
                userMessageBean = bean;
                for (int i = 0; i < userMessageBean.size(); i++) {
                    if (("2").equals(userMessageBean.get(i).getStatus())) {
                        mImgAlreadyMessage.setBadgeVisible(true);
                        Intent intent = new Intent("com.yinu.change.viewpage.index");
                        intent.putExtra("change_status", ACTION_NEED_SHOW_RED_POINT2);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }
                }
            }

            @Override
            protected void _onError(String message, int code) {

            }

            @Override
            protected void _onError() {

            }
        };
    }


    /**
     * 注销登录
     */
    public void exictLogin() {
        Intent intent = new Intent("com.yinu.login");
        intent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_EXCIT);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

        Intent intentExcit = new Intent("com.yinu.change.viewpage.index");
        intentExcit.putExtra("change_status", DialogLoginActivity.EVENT_LOGIN_EXCIT);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intentExcit);


        personal_center_unlogin_layout.setVisibility(View.VISIBLE);
        personal_center_already_login_layout.setVisibility(View.GONE);
        Utils.deletePersistentUserInfo();
    }


    /**
     * 是否开启实名认证
     */
    public void isOpendRealName() {
        Type type = new TypeToken<HttpResult<Integer>>() {
        }.getType();
        new HttpUtils<Integer>(type, HttpConstant.API_PERSONAL_CNTER_REAL_NAME_SWITCH, null, "是否开启实名认证", false) {
            @Override
            protected void _onSuccess(Integer bean, String msg) {
                isOpendRealNameBean = bean;
            }

            @Override
            protected void _onError(String message, int code) {

            }

            @Override
            protected void _onError() {
            }
        };
    }


    /**
     * 是否实名认证
     */
    private void isUserRealName() {
        if (null != Utils.getPersistentUserInfo()) {
            Type type = new TypeToken<HttpResult<UserIsRealNameBean>>() {
            }.getType();
            new HttpUtils<UserIsRealNameBean>(type, HttpConstant.API_PERSONAL_CENTER_USER_AUTH_DATA, null, "是否实名认证", true) {

                @Override
                protected void _onSuccess(UserIsRealNameBean bean, String msg) {
                    isRealNameBean = bean;
                    if ("2".equals(isRealNameBean.getAge_status()) || "3".equals(isRealNameBean.getAge_status())) {
                        llPersonalCenterAlreadyRealName.setVisibility(View.VISIBLE);
                        tvPersonalCenterUnRealName.setVisibility(View.GONE);
                    } else {
                        llPersonalCenterAlreadyRealName.setVisibility(View.GONE);
                        tvPersonalCenterUnRealName.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                protected void _onError(String message, int code) {

                }

                @Override
                protected void _onError() {

                }
            };
        }
    }


    /**
     * 是否需要显示绑定手机号送积分弹窗
     */
    private void isTipsShow() {
        if (Utils.getPersistentUserInfo() != null) {
            Type type = new TypeToken<HttpResult<UserIsBindPhoneBean>>() {
            }.getType();
            new HttpUtils<UserIsBindPhoneBean>(type, HttpConstant.API_PERSONAL_CENTER_USER, null, TAG + "用户综合信息", true) {

                @Override
                protected void _onSuccess(UserIsBindPhoneBean bean, String msg) {
                    signPoint = String.valueOf(bean.getSign_point());
                    isBindPhoneBean = bean;
                    //未绑定手机号
                    if (1 != isBindPhoneBean.getUserbind() && DialogLoginActivity.EVENT_YOU_LOGIN_SUCCESS == 0) {
                        if (IsShow(Utils.getPersistentUserInfo().account)) {
                            new DialogLoginPopTipBind(getActivity(), R.style.MyDialogStyle,
                                    isBindPhoneBean.getBind_point()).show();
                        }
                    }
                }

                @Override
                protected void _onError(String message, int code) {
                    personal_center_unlogin_layout.setVisibility(View.VISIBLE);
                    personal_center_already_login_layout.setVisibility(View.GONE);
                    Utils.deletePersistentUserInfo();
                    showLoginDoalog();
                }

                @Override
                protected void _onError() {
                    personal_center_unlogin_layout.setVisibility(View.VISIBLE);
                    personal_center_already_login_layout.setVisibility(View.GONE);
                    Utils.deletePersistentUserInfo();
                    Utils.TS("网络缓慢");
                }
            };
        }
    }


    /**
     * 检测用户信息（用户过期清除帐号，弹出登录框）
     */
    private void getUserInfo() {
        if (Utils.getPersistentUserInfo() != null) {
            Type type = new TypeToken<HttpResult<UserIsBindPhoneBean>>() {
            }.getType();
            new HttpUtils<UserIsBindPhoneBean>(type, HttpConstant.API_PERSONAL_CENTER_USER, null, TAG + "用户综合信息", true) {

                @Override
                protected void _onSuccess(UserIsBindPhoneBean bean, String msg) {

                }

                @Override
                protected void _onError(String message, int code) {
                    personal_center_unlogin_layout.setVisibility(View.VISIBLE);
                    personal_center_already_login_layout.setVisibility(View.GONE);
                    Utils.deletePersistentUserInfo();
                    showLoginDoalog();
                    ToastUtil.showToast(message);
                }

                @Override
                protected void _onError() {
                    personal_center_unlogin_layout.setVisibility(View.VISIBLE);
                    personal_center_already_login_layout.setVisibility(View.GONE);
                    Utils.deletePersistentUserInfo();
                    Utils.TS("网络缓慢");
                }
            };
        }
    }


    /**
     * 判断是否需要弹出
     *
     * @param account
     * @return
     */
    private boolean IsShow(String account) {
        IsShowBean bean = Hawk.get(account);
        if (bean == null) {
            IsShowBean isShowBean = new IsShowBean();
            isShowBean.account = account;
            isShowBean.time = System.currentTimeMillis();
            Hawk.put(account, isShowBean);
            return true;
        } else {
            long l = System.currentTimeMillis() - bean.time;
            if (l > 604800000) {
                bean.time = System.currentTimeMillis();
                Hawk.put(account, bean);
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * 用户签到、绑定送多少积分、余额、是否签到、是否绑定手机号、等综合信息
     */
    private void isUSerBindPhone() {

        Type type = new TypeToken<HttpResult<UserIsBindPhoneBean>>() {
        }.getType();
        new HttpUtils<UserIsBindPhoneBean>(type, HttpConstant.API_PERSONAL_CENTER_USER, null, "是否绑定手机号返回数据", true) {

            @Override
            protected void _onSuccess(UserIsBindPhoneBean userIsBindPhoneBean, String msg) {
                isBindPhoneBean = userIsBindPhoneBean;
                //已绑定手机号
                if (1 == isBindPhoneBean.getUserbind()) {
                    tvPersonalCenterAlreadyBindPhone.setVisibility(View.VISIBLE);
                    tvPersonalCenterAlreadyBindPhone.setText(Utils
                            .YinCang(isBindPhoneBean.getPhone()));
                    tvPersonalCenterBindPhoneTips.setVisibility(View.GONE);
                    //未绑定手机号
                } else {
                    tvPersonalCenterAlreadyBindPhone.setVisibility(View.GONE);
                    tvPersonalCenterBindPhoneTips.setVisibility(View.VISIBLE);
                    tvPersonalCenterBindPhoneTips.setText("绑定送" + isBindPhoneBean.getBind_point() + "积分");
                }

                //已签到
                if (1 == isBindPhoneBean.getIssignin()) {
                    modifyUiWithSign(false);
                } else {
                    Intent intent = new Intent("com.yinu.change.viewpage.index");
                    intent.putExtra("change_status", ACTION_NEED_SHOW_RED_POINT);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    imgRedHint.setVisibility(View.VISIBLE);
                }

            }

            @Override
            protected void _onError(String message, int code) {

            }

            @Override
            protected void _onError() {
            }
        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initView();
        switch (resultCode) {
            //回传值:实名认证成功
            case AUTH_REAL_NAME_SUCCESS:
                tvPersonalCenterUnRealName.setVisibility(View.GONE);
                llPersonalCenterAlreadyRealName.setVisibility(View.VISIBLE);
                break;
            //回传值:实名认证失败
            case AUTH_REAL_NAME_FAILED:
                break;
            case CHANGE_PASSWORD_SUCCESS:
                new DialogChangePwdSuccess(getActivity(), R.style.MyDialogStyle).show();
                //注销登录
                exictLogin();
                break;
            case CHANGE_PASSWORD_FAILED:
                ToastUtil.showToast(data.getStringExtra("change_pwd_msg"));
                break;
            //回传值:绑定手机号成功
            case BIND_PHONE_SUCCESS:
                isUSerBindPhone();
                break;
            //回传值:需要显示实名认证详情页
            case ACTION_REAL_NAME_DETAIL:
                Intent intent = new Intent(getContext(), AlreadyRealNameActivity.class);
                intent.putExtra("real_name", data.getStringExtra("personName"));
                intent.putExtra("id_card", data.getStringExtra("idCard"));
                this.startActivityForResult(intent, 0);
                break;

        }
    }

}
