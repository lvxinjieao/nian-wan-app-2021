package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.IsShowBean;
import com.nian.wan.app.bean.UserIsBindPhoneBean;
import com.nian.wan.app.bean.UserLoginBean;
import com.nian.wan.app.bean.UserRegisterBean;
import com.nian.wan.app.bean.VisitorLoginBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.LoginCallBack;
import com.nian.wan.app.utils.PromoteUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.ValidationCode;
import com.nian.wan.app.view.VisitorLoginSuccess;
import com.orhanobut.hawk.Hawk;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @Description: 登录注册弹出框
 */
public class DialogLoginActivity extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private String TAG = "DialogLoginActivity";
    //登录ViewPager
    private ViewPager mViewPager;
    //登录TabLayout
    private TabLayout mTabLayout;
    //关闭登录框
    private RelativeLayout mImgCloseLoginDialog;
    //登录用户名输入框
    private EditText mEdtLoginAccount;
    //登录密码框
    private EditText mEdtLoginPassword;
    //登录按钮
    private Button mBtnLoginUserLogin;
    //登录显示密码
    private CheckBox mCbLoginShowPassword;
    //清空登录账户
    private ImageView mImgLoginClearAccount;
    //清空登录密码
    private ImageView mImgLoginClearPassword;
    //游客登录
    private TextView tvLoginVisitorLogin;

    //注册账户输入框
    private EditText mEdtRegAccount;
    //注册密码框
    private EditText mEdtRegPassword;
    //注册确认密码框
    private EditText mEdtRegConfirmPassword;
    //注册获取验证码按钮
    private TextView mBtnGetVerificationCode;
    //注册图片验证码
    private ValidationCode mImgRegisterVerificationCode;
    //验证码输入框
    private EditText mEdtRegVerificationCode;
    //注册按钮
    private Button mBtnRegister;
    //注册显示密码
    private CheckBox mCbRegShowPassword;
    //清空注册账户
    private ImageView mImgClearRegAccount;
    //清空注册密码
    private ImageView mImgClearRegPassword;
    //注册确认密码显示密码
    private CheckBox mCbRegShowConfirmPawword;
    //清空注册确认密码
    private ImageView mImgClearRegConfirmPwd;
    //注册协议
    private TextView mTvRegisterAgreement;
    //注册同意协议
    private CheckBox mCbAgreeProtocol;
    //注册密码不一致提示
    private LinearLayout mLlRegisterTip;
    //注册密码不一致提示
    private LinearLayout mLlLoginTip;
    //已发送布局
    private RelativeLayout mRlRegisterVerificationSecond;
    //已发送秒数
    private TextView mTvRegisterVerificationSecond;


    //ViewPager包含的登录注册View
    private List<View> mViewList = new ArrayList<>();
    //TabLayout包含的Item标题
    private List<String> mTitleList = new ArrayList<>();
    //弱引用上下文
    private WeakReference<Context> mWeakReference;
    //初始化时显示注册还是登录
    private String mShowWhere = "LOGIN";

    //注册获取验证码
    private static final int ACTION_GET_VERIFICATION_CODE = 0;
    //关闭窗口
    private static final int ACTION_CLOSE_DIALOG_LOGIN = 1;
    //忘记密码
    private static final int ACTION_FORGET_PASSWORD = 2;
    //微信登录
    private static final int ACTION_LOGIN_WX = 3;
    //QQ登录
    private static final int ACTION_LOGIN_QQ = 4;
    //新浪微博登录
    private static final int ACTION_LOGIN_SINA = 5;
    //腾讯微博登录
    private static final int ACTION_LOGIN_QWEIBO = 6;
    //普通登录
    private static final int ACTION_LOGIN_NORMAL = 7;
    //注册
    private static final int ACTION_REGISTER = 8;
    //清空登录账户
    private static final int ACTION_LOGIN_CLEAR_ACCOUNT = 9;
    //清空登录密码
    private static final int ACTION_LOGIN_CLEAR_PASSWORD = 10;
    //更换图片验证码
    private static final int ACTION_REGISTER_CHANGE_VERIFICATIONCODE = 11;
    //注册成功
    private static final int ACTION_REGISTER_SUCCESS = 12;
    //注册失败
    private static final int ACTION_REGISTER_FAILED = 13;
    //登录成功
    public static final int EVENT_LOGIN_SUCCESS = 14;
    //登录失败
    public static final int EVENT_LOGIN_FILED = 15;
    //第三方登录成功
    public static final int EVENT_THIRD_LOGIN_SUCCESS = 20;
    //第三方登录失败
    public static final int EVENT_THIRD_LOGIN_FAILED = 21;
    //注销登录
    public static final int EVENT_LOGIN_EXCIT = 16;
    //游客登录
    public static final int ACTION_VISITOR_LOGIN = 17;
    //已签到
    public static final int ACTION_ALREADY_SIGN = 18;
    //注册协议
    public static final int ACTION_USER_AGREEMENT = 19;
    //游客登录成功
    public static int EVENT_YOU_LOGIN_SUCCESS = 0;
    private UserLoginBean userLoginBean;
    private VisitorLoginBean visitorLoginBean;
    //true 手机号注册 flase 帐号注册
    private boolean registerType;

    private PromoteUtils promoteUtils;
    private boolean canYouKeLogin = true;

    public DialogLoginActivity() {

    }


    @SuppressLint("ValidFragment")
    public DialogLoginActivity(Context context, String showWhere) {
        this.setCancelable(false);
        this.mWeakReference = new WeakReference<>(context);
        this.mShowWhere = showWhere;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置弹出框的样式
        setStyle(STYLE_NO_FRAME, R.style.MyDialogStyleBottom);
        promoteUtils = new PromoteUtils();
    }


    @Override
    public void onStart() {
        // 1, 设置对话框的大小
        Window window = getDialog().getWindow();
        WindowManager wm = window.getWindowManager();
        Point windowSize = new Point();
        wm.getDefaultDisplay().getSize(windowSize);
        float size_x = 0;
        float size_y = 0;
        int width = windowSize.x;
        int height = windowSize.y;
        if (width >= height) {// 横屏
            size_x = 0.7f;
            size_y = 0.8f;
            window.getAttributes().width = (int) (windowSize.y * size_y * 1.1);
            window.getAttributes().height = (int) (windowSize.y * size_y);
        } else {// 竖屏
            size_x = 0.8f;
            size_y = 0.95f;
            window.getAttributes().width = (int) (windowSize.x * size_x);
            window.getAttributes().height = (int) (windowSize.x * size_y);
        }
        window.setGravity(Gravity.CENTER);
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_login, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_login_content);
        mTabLayout = (TabLayout) view.findViewById(R.id.tb_login_register);
        mImgCloseLoginDialog = (RelativeLayout) view.findViewById(R.id.img_close_login);
        mImgCloseLoginDialog.setOnClickListener(this);
        initView(inflater);
        return view;
    }

    private DialogLoginBroadcast dialogLoginBroadcast;


    private class DialogLoginBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getIntExtra("login_status", -1)) {
                case DialogLoginActivity.EVENT_LOGIN_SUCCESS: //登录成功
                    //dismissDialog();
                    break;

                case DialogLoginActivity.EVENT_THIRD_LOGIN_SUCCESS: //第三方登录成功
                    dismissDialog();
                    break;
            }
        }

    }


    /**
     * 初始化控件
     *
     * @param inflater
     */
    private void initView(LayoutInflater inflater) {
        dialogLoginBroadcast = new DialogLoginBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.login");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(dialogLoginBroadcast, intentFilter);
        /*登录布局start*/
        View dialogTabLogin = inflater.inflate(R.layout.dialog_tab_login, null);

        //忘记密码
        dialogTabLogin.findViewById(R.id.tv_login_forget_password).setOnClickListener(this);
        //用户名输入框
        mEdtLoginAccount = (EditText) dialogTabLogin.findViewById(R.id.edt_login_account);
        mEdtLoginAccount.setOnClickListener(this);
        //密码框
        mEdtLoginPassword = (EditText) dialogTabLogin.findViewById(R.id.edt_login_password);
        mEdtLoginPassword.setOnClickListener(this);
        //登录按钮
        mBtnLoginUserLogin = (Button) dialogTabLogin.findViewById(R.id.btn_login_user_login);
        mBtnLoginUserLogin.setOnClickListener(this);
        //游客登录
        tvLoginVisitorLogin = (TextView) dialogTabLogin.findViewById(R.id.tv_login_visitor_login);
        tvLoginVisitorLogin.setOnClickListener(this);
        //登录提示
        mLlLoginTip = (LinearLayout) dialogTabLogin.findViewById(R.id.ll_login_tip);
        //登录显示或隐藏密码
        mCbLoginShowPassword = (CheckBox) dialogTabLogin.findViewById(R.id.cb_login_show_password);
        mCbLoginShowPassword.setOnCheckedChangeListener(this);
        //清空登录密码
        mImgLoginClearPassword = (ImageView) dialogTabLogin.findViewById(R.id.img_login_clear_pwd);
        addInputClearListener(mEdtLoginPassword, mImgLoginClearPassword, mCbLoginShowPassword);
        //清空登录账户
        mImgLoginClearAccount = (ImageView) dialogTabLogin.findViewById(R.id.img_login_clear_account);
        addInputClearListener(mEdtLoginAccount, mImgLoginClearAccount, null);
        //第三方登录微信
        dialogTabLogin.findViewById(R.id.img_login_wx_login).setOnClickListener(this);
        //第三方登录QQ
        dialogTabLogin.findViewById(R.id.img_login_qq_login).setOnClickListener(this);

        mViewList.add(dialogTabLogin);
        /*登录布局end*/

        /*注册布局start*/
        View dialogTabRegister = inflater.inflate(R.layout.dialog_tab_register, null);
        //获取验证码按钮
        mBtnGetVerificationCode = (TextView) dialogTabRegister.findViewById(R.id.tv_register_verification_code);
        mBtnGetVerificationCode.setOnClickListener(this);
        mTvRegisterAgreement = (TextView) dialogTabRegister.findViewById(R.id.tv_register_agree_two);
        mTvRegisterAgreement.setOnClickListener(this);
        //已发送验证码布局
        mRlRegisterVerificationSecond = (RelativeLayout) dialogTabRegister
                .findViewById(R.id.ll_register_verification_second);
        //已发送验证码秒
        mTvRegisterVerificationSecond = (TextView) dialogTabRegister
                .findViewById(R.id.tv_register_verification_second);

        //注册图片验证码
        mImgRegisterVerificationCode = (ValidationCode) dialogTabRegister
                .findViewById(R.id.img_register_verification_code);
        mImgRegisterVerificationCode.setOnClickListener(this);
        //验证码输入框
        mEdtRegVerificationCode = (EditText) dialogTabRegister
                .findViewById(R.id.edt_register_verification_code);
        //验证码输入框失去获取焦点监听
//        mEdtRegVerificationCode.setOnFocusChangeListener(onFocusChangeListener);
        //注册账户输入框
        mEdtRegAccount = (EditText) dialogTabRegister.findViewById(R.id.edt_regsiter_account);
        mEdtRegAccount.addTextChangedListener(accountTextWatcher);
        //注册密码框
        mEdtRegPassword = (EditText) dialogTabRegister.findViewById(R.id.edt_register_password);
//        mEdtRegPassword.setOnFocusChangeListener(pwdFocusChangeListener);
        //注册密码确认密码框
        mEdtRegConfirmPassword = (EditText) dialogTabRegister
                .findViewById(R.id.et_register_confirm_password);
        //注册按钮
        mBtnRegister = (Button) dialogTabRegister.findViewById(R.id.btn_register_register);
        mBtnRegister.setOnClickListener(this);
        //注册密码不一致提示
        mLlRegisterTip = (LinearLayout) dialogTabRegister.findViewById(R.id.ll_register_tip);
        //注册显示密码
        mCbRegShowPassword = (CheckBox) dialogTabRegister
                .findViewById(R.id.cb_register_show_password);
        mCbRegShowPassword.setOnCheckedChangeListener(this);
        //注册清除密码
        mImgClearRegPassword = (ImageView) dialogTabRegister
                .findViewById(R.id.img_register_clear_pwd);
        addInputClearListener(mEdtRegPassword, mImgClearRegPassword, mCbRegShowPassword);
        //注册清除账户
        mImgClearRegAccount = (ImageView) dialogTabRegister
                .findViewById(R.id.img_register_clear_account);
        addInputClearListener(mEdtRegAccount, mImgClearRegAccount, null);
        //注册确认密码显示
        mCbRegShowConfirmPawword = (CheckBox) dialogTabRegister
                .findViewById(R.id.cb_register_confirm_show_pwd);
        mCbRegShowConfirmPawword.setOnCheckedChangeListener(this);
        //注册确认密码清除
        mImgClearRegConfirmPwd = (ImageView) dialogTabRegister
                .findViewById(R.id.img_register_confirm_clear_pwd);
        addInputClearListener(mEdtRegConfirmPassword, mImgClearRegConfirmPwd,
                mCbRegShowConfirmPawword);
        //同意协议复选框
        mCbAgreeProtocol = (CheckBox) dialogTabRegister
                .findViewById(R.id.cb_register_agree_protocol);
        mViewList.add(dialogTabRegister);
        /*注册布局end*/

        //添加页卡标题
        mTitleList.add("登录");
        mTitleList.add("注册");
        //设置tab模式，当前为系统默认模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        LoginPagerAdapter pagerAdapter = new LoginPagerAdapter(mViewList);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        switch (mShowWhere.toUpperCase()) {
            case "LOGIN":
                mViewPager.setCurrentItem(0);
                break;
            case "REGISTER":
                mViewPager.setCurrentItem(1);
                break;
            default:
                Log.e(TAG, "You input parameter is not right" + " and it  should be 'LOGIN' or 'REGISTER'");
                break;
        }
    }


    /**
     * 账户输入监听
     */
    TextWatcher accountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //如果以字母开头显示图形验证码
            if (checkIsLetterFirst()) {
                mImgRegisterVerificationCode.setVisibility(View.VISIBLE);
                mBtnGetVerificationCode.setVisibility(View.GONE);
                registerType = false;
                //否则显示获取验证码
            } else {
                mImgRegisterVerificationCode.setVisibility(View.GONE);
                mBtnGetVerificationCode.setVisibility(View.VISIBLE);
                registerType = true;

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    /**
     * 正则匹配是否是手机号
     */
    private boolean checkIsMobile() {
        //匹配手机号
        Pattern pattern = Pattern.compile("^[1][345678][0-9]{9}");
        Matcher matcher = pattern.matcher(mEdtRegAccount.getText().toString().trim());
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是字母开头
     *
     * @return
     */
    private boolean checkIsLetterFirst() {
        //英文字母和数字组成的4-16位字符,以字母开头 [a-zA-Z][a-zA-Z0-9]{6,15}、
        //字母开头 ^[A-Za-z]
        //数字开头 ^[0-9]
        Pattern pattern = Pattern
                .compile("^[a-zA-Z][a-zA-Z0-9]*$");
        Matcher matcher = pattern.matcher(mEdtRegAccount.getText().toString().trim());
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是数字开头
     *
     * @return
     */
    private boolean checkIsNumberFirst() {
        if (TextUtils.isEmpty(mEdtRegAccount.getText().toString().trim())) {
            return false;
        }
        for (int i = 0; i < mEdtRegAccount.getText().toString().trim().length(); i++) {
            if (!Character.isDigit(mEdtRegAccount.getText().toString().trim().charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据checked事件来显示或者隐藏密码
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            //登录显示隐藏密码
            case R.id.cb_login_show_password:
                if (isChecked) {
                    mEdtLoginPassword.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {
                    mEdtLoginPassword
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            //注册显示隐藏密码
            case R.id.cb_register_show_password:
                if (isChecked) {
                    mEdtRegPassword
                            .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtRegPassword
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            //注册确认密码显示隐藏
            case R.id.cb_register_confirm_show_pwd:
                if (isChecked) {
                    mEdtRegConfirmPassword
                            .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtRegConfirmPassword
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //普通登录按钮
            case R.id.btn_login_user_login:
                doClick(ACTION_LOGIN_NORMAL);
                break;
            //微信登录
            case R.id.img_login_wx_login:
                doClick(ACTION_LOGIN_WX);
                dismissDialog();
                break;
            //QQ登录
            case R.id.img_login_qq_login:
                doClick(ACTION_LOGIN_QQ);
                dismissDialog();
                break;
            /*//新浪微博登录
            case R.id.img_login_sina_login:
                doClick(ACTION_LOGIN_SINA);
                break;
            //腾讯微博登录
            case R.id.img_login_qweibo_login:
                doClick(ACTION_LOGIN_QWEIBO);
                break;*/
            //忘记密码
            case R.id.tv_login_forget_password:
                doClick(ACTION_FORGET_PASSWORD);
                break;
            //关闭登录对话框
            case R.id.img_close_login:
                doClick(ACTION_CLOSE_DIALOG_LOGIN);
                break;
            //注册获取验证码
            case R.id.tv_register_verification_code:
                doClick(ACTION_GET_VERIFICATION_CODE);
                break;
            //更换图片验证码
            case R.id.img_register_verification_code:
                doClick(ACTION_REGISTER_CHANGE_VERIFICATIONCODE);
                break;
            //注册按钮
            case R.id.btn_register_register:
                doClick(ACTION_REGISTER);
                break;
            //清空登录账户
            case R.id.img_login_clear_account:
                doClick(ACTION_LOGIN_CLEAR_ACCOUNT);
                break;
            //清空登录密码
            case R.id.img_login_clear_pwd:
                doClick(ACTION_LOGIN_CLEAR_PASSWORD);
                break;
            //游客登录
            case R.id.tv_login_visitor_login:
                tvLoginVisitorLogin.setEnabled(false);
                doClick(ACTION_VISITOR_LOGIN);
                break;
            case R.id.tv_register_agree_two:
                doClick(ACTION_USER_AGREEMENT);
                break;
        }
    }

    /**
     * 响应点击时间并调用相关视图
     *
     * @param action
     */
    public void doClick(int action) {

        switch (action) {
            //响应普通登录
            case ACTION_LOGIN_NORMAL:
                if (checkLoginInputFormat()) {
                    login(mEdtLoginAccount.getText().toString(), mEdtLoginPassword.getText().toString());
                }
                break;
            //响应微信登录
            case ACTION_LOGIN_WX:
                LoginCallBack.loginType = "2";
                ShareSDK.initSDK(getActivity());
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                wx.removeAccount(true);
                ShareSDK.removeCookieOnAuthorize(true);
                wx.setPlatformActionListener(new LoginCallBack(getActivity()));
                wx.SSOSetting(false);
                wx.showUser(null);
                break;
            //响应QQ登录
            case ACTION_LOGIN_QQ:

                LoginCallBack.loginType = "4";
                ShareSDK.initSDK(getActivity());
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.removeAccount(true);
                ShareSDK.removeCookieOnAuthorize(true);
                qq.setPlatformActionListener(new LoginCallBack(getActivity()));
                qq.SSOSetting(false);
                qq.showUser(null);
                break;
            //响应关闭登录窗口
            case ACTION_CLOSE_DIALOG_LOGIN:
                dismissDialog();
                break;
            //响应忘记密码
            case ACTION_FORGET_PASSWORD:
                dismissDialog();
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                DialogResetPasswordActivity resetPasswordDialog = new DialogResetPasswordActivity(mWeakReference.get());
                transaction.add(resetPasswordDialog, "MainActivity");
                transaction.show(resetPasswordDialog);
                transaction.commitAllowingStateLoss();
                break;
            //响应获取验证码
            case ACTION_GET_VERIFICATION_CODE:
                getVerificationCode();
                break;
            //响应注册
            case ACTION_REGISTER:
                if (checkRegisterFormat()) {
                    if (registerType) {
                        registerMobile();
                    } else {
                        registerNormal();
                    }
                }
                break;
            //响应更换图片验证码
            case ACTION_REGISTER_CHANGE_VERIFICATIONCODE:
                mImgRegisterVerificationCode.refresh();
                break;
            //响应清空账户
            case ACTION_LOGIN_CLEAR_ACCOUNT:
                mEdtLoginAccount.setText("");
                break;
            //响应清空密码
            case ACTION_LOGIN_CLEAR_PASSWORD:
                mEdtLoginPassword.setText("");
                break;
            //游客登录
            case ACTION_VISITOR_LOGIN:
                visitorLogin();
                break;
            //注册协议
            case ACTION_USER_AGREEMENT:
                Intent intent1 = new Intent();
                intent1.setClass(mWeakReference.get(), SignWebActivity.class);
                intent1.putExtra("url", Utils.getPersistentAboutUsData().getUSER_AGREEMENT());
                intent1.putExtra("name", "使用条款和隐私政策");
                mWeakReference.get().startActivity(intent1);
                break;
        }
    }

    /**
     * 用户签到、绑定送多少积分、余额、是否签到、是否绑定手机号、等综合信息
     */
    private void isUSerBindPhone() {
        Type type = new TypeToken<HttpResult<UserIsBindPhoneBean>>() {
        }.getType();
        new HttpUtils<UserIsBindPhoneBean>(type, HttpConstant.API_PERSONAL_CENTER_USER, null, TAG + "用户综合信息", true) {

            @Override
            protected void _onSuccess(UserIsBindPhoneBean userIsBindPhoneBean, String msg) {
                tvLoginVisitorLogin.setEnabled(true);
                new VisitorLoginSuccess(getActivity(), R.style.MyDialogStyle, visitorLoginBean.getAccount(), visitorLoginBean.getPassword(), userIsBindPhoneBean.getBind_point()).show();
                dismissDialog();
            }

            @Override
            protected void _onError(String message, int code) {
            }

            @Override
            protected void _onError() {
                dismissDialog();
            }
        };
    }

    /**
     * 游客登录
     */
    private void visitorLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("pid", promoteUtils.getPromoteId());
        Type type = new TypeToken<HttpResult<VisitorLoginBean>>() {
        }.getType();

        new HttpUtils<VisitorLoginBean>(type, HttpConstant.API_VISITOR_LOGIN, map, "游客登录", false) {
            @Override
            protected void _onSuccess(VisitorLoginBean s, String msg) {
                visitorLoginBean = s;
                IsShowBean isShowBean = new IsShowBean();
                isShowBean.account = visitorLoginBean.getAccount();
                isShowBean.time = System.currentTimeMillis();
                Hawk.put(visitorLoginBean.getAccount(), isShowBean);
                visitorTransferLogin(visitorLoginBean.getAccount(), visitorLoginBean.getPassword());
            }

            @Override
            protected void _onError(String message, int code) {
                tvLoginVisitorLogin.setEnabled(true);
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                tvLoginVisitorLogin.setEnabled(true);
            }
        };
    }


    /**
     * 请求登录接口
     */
    private void visitorTransferLogin(String account, String password) {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("account", account);
        loginParams.put("password", password);
        Type type = new TypeToken<HttpResult<UserLoginBean>>() {
        }.getType();
        new HttpUtils<UserLoginBean>(type, HttpConstant.API_USER_LOGIN, loginParams, "登录", false) {
            Intent intent = new Intent("com.yinu.login");

            @Override
            protected void _onSuccess(UserLoginBean userLoginBean, String msg) {
                //将登录的用户信息保存到数据库中
                EVENT_YOU_LOGIN_SUCCESS = 20;
                Utils.persistentUserInfo(userLoginBean);
                intent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_SUCCESS);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                isUSerBindPhone();
                //检查token
                checkTokenIsValid(userLoginBean.getToken());
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };

    }


    /**
     * 请求登录接口
     */
    private void login(String account, String password) {

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("account", account);
        loginParams.put("password", password);
        Type type = new TypeToken<HttpResult<UserLoginBean>>() {
        }.getType();

        new HttpUtils<UserLoginBean>(type, HttpConstant.API_USER_LOGIN, loginParams, "登录", false) {

            Intent intent = new Intent("com.yinu.login");

            @Override
            protected void _onSuccess(UserLoginBean userLoginBean, String msg) {
                //将登录的用户信息保存到数据库中
                EVENT_YOU_LOGIN_SUCCESS = 0;
                Utils.persistentUserInfo(userLoginBean);
                intent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_SUCCESS);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                //检查token
                checkTokenIsValid(userLoginBean.getToken());
                dismissDialog();
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
                intent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_FILED);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };

    }


    /**
     * 请求手机号注册接口
     */
    String Phone;
    String Phone_PassWord;

    private void registerMobile() {

        Map<String, String> registerParams = new HashMap<>();
        Phone = mEdtRegAccount.getText().toString();
        Phone_PassWord = mEdtRegPassword.getText().toString();
        registerParams.put("account", Phone);
        registerParams.put("password", Phone_PassWord);
        registerParams.put("vcode", mEdtRegVerificationCode.getText().toString());
        registerParams.put("promote_id", promoteUtils.getPromoteId());
        Type type = new TypeToken<HttpResult<UserRegisterBean>>() {
        }.getType();
        new HttpUtils<UserRegisterBean>(type, HttpConstant.API_USER_REGISTER_MOBILE, registerParams, "手机号注册", false) {

            @Override
            protected void _onSuccess(UserRegisterBean shareDateBean, String msg) {
                Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                login(Phone, Phone_PassWord);
                //检查token
                checkTokenIsValid(shareDateBean.getToken());
                //mViewPager.setCurrentItem(0);
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };


    }


    /**
     * 检查token
     */
    private void checkTokenIsValid(String checkToken) {
        Map<String, String> checkTokenParams = new HashMap<>();
        checkTokenParams.put("token", checkToken);
        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_TOKEN_IS_VALID, checkTokenParams, "检查token", false) {

            @Override
            protected void _onSuccess(String bean, String msg) {
                Log.i("", "");
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
                //如果Token已过期
                if (1032 == code) {
                    //删除已持久化用户信息
                    Utils.deletePersistentUserInfo();
                    //发送注销广播
                    Intent intent = new Intent("com.yinu.login");
                    intent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_EXCIT);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    //弹出登录框
                    DialogLoginActivity loginActivity = new DialogLoginActivity(getActivity(), "LGOIN");
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.add(loginActivity, "WoDe");
                    transaction.show(loginActivity);
                    transaction.commitAllowingStateLoss();
                }

            }

            @Override
            protected void _onError() {

            }
        };
    }


    /**
     * 帐号注册
     */
    String Account;
    String Account_PassWord;

    private void registerNormal() {
        Map<String, String> registerParams = new HashMap<>();
        Account = mEdtRegAccount.getText().toString();
        Account_PassWord = mEdtRegPassword.getText().toString();
        registerParams.put(" account", Account);
        registerParams.put(" password", Account_PassWord);
        registerParams.put(" promote_id", new PromoteUtils().getPromoteId());
        Type type = new TypeToken<HttpResult<UserRegisterBean>>() {
        }.getType();
        new HttpUtils<UserRegisterBean>(type, HttpConstant.API_USER_REGISTER, registerParams, "帐号注册", false) {

            @Override
            protected void _onSuccess(UserRegisterBean shareDateBean, String msg) {
                Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                //mViewPager.setCurrentItem(0);
                login(Account, Account_PassWord);
                //检查token
                checkTokenIsValid(shareDateBean.getToken());
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络似乎有点问题");
            }
        };

    }


    /**
     * 获取绑定短信验证码
     */
    @SuppressLint("HandlerLeak")
    private void getVerificationCode() {
        if (checkIsMobile()) {
            Map<String, String> verificationCodeParams = new HashMap<>();
            verificationCodeParams.put("phone", mEdtRegAccount.getText().toString());
            verificationCodeParams.put("type", "1");
            Type type = new TypeToken<HttpResult<String>>() {
            }.getType();
            new HttpUtils<String>(type, HttpConstant.API_PERSONAL_USER_SEND_SMS, verificationCodeParams, "获取绑定短信验证码", false) {

                @Override
                protected void _onSuccess(String s, String msg) {
                    startTimeCount();
                }

                @Override
                protected void _onError(String message, int code) {
                    Utils.TS(message);
                }

                @Override
                protected void _onError() {
                }
            };
        } else {
            Utils.TS("手机号码格式不正确");
        }
    }

    private void startTimeCount() {
        //倒计时60秒，一次1秒
        CountDownTimer timer = new CountDownTimer(10 * 6000, 1000) {
            @SuppressLint("ResourceType")
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                mBtnGetVerificationCode.setVisibility(View.GONE);
                mRlRegisterVerificationSecond.setVisibility(View.VISIBLE);
                mTvRegisterVerificationSecond.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            //倒计时结束按钮归位
            @Override
            public void onFinish() {
                mBtnGetVerificationCode.setVisibility(View.VISIBLE);
                mRlRegisterVerificationSecond.setVisibility(View.GONE);
                if (isAdded()) {
                    mBtnGetVerificationCode.setTextColor(getResources().getColor(R.color.font_white));
                    mBtnGetVerificationCode.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.yuan_jiao_green_bg));
                    mBtnGetVerificationCode.setEnabled(true);
                    mBtnGetVerificationCode.setText("获取验证码");
                }
            }
        }.start();
    }


    /**
     * 关闭登录弹出窗
     */
    private void dismissDialog() {
        if (this.getShowsDialog()) {
            this.dismiss();
        }
    }

    /**
     * 登录输入框非空验证、输入格式验证
     *
     * @return
     */
    private boolean checkLoginInputFormat() {

        if (TextUtils.isEmpty(mEdtLoginAccount.getText())) {
            Toast.makeText(mWeakReference.get(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtLoginPassword.getText())) {
            Toast.makeText(mWeakReference.get(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 用户名格式验证
     */
    private boolean checkRegisterUsername() {
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]{6,15}");
        Matcher matcher = pattern.matcher(mEdtRegAccount.getText().toString());
        if (!matcher.matches()) {
            Toast.makeText(mWeakReference.get(), "请输入正确的手机号或用户名", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    /**
     * 注册格式检查
     *
     * @return
     */
    private boolean checkRegisterFormat() {

        if (TextUtils.isEmpty(mEdtRegAccount.getText().toString())) {
            Toast.makeText(mWeakReference.get(), "请输入手机号或用户名", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        //数字开头验证是不是手机号
        if (checkIsNumberFirst()) {
            if (!checkIsMobile()) {
                ToastUtil.showToast("请输入正确的手机号");
                return false;
            }
        }

        //字母开头验证是不是符合用户名规则
        if (checkIsLetterFirst()) {
            if (!(mEdtRegAccount.getText().toString()
                    .length() >= 6 && mEdtRegAccount.getText().toString().length() <= 15)) {
                ToastUtil.showToast("请输入正确的用户名");
                return false;
            }
        }


        if (TextUtils.isEmpty(mEdtRegVerificationCode.getText().toString())) {
            Toast.makeText(mWeakReference.get(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        //如果图片验证码可见则去验证正确性
        if (mImgRegisterVerificationCode.getVisibility() == View.VISIBLE) {
            if (!mImgRegisterVerificationCode.isEquals(mEdtRegVerificationCode.getText().toString())) {
                Toast.makeText(mWeakReference.get(), "验证码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                mImgRegisterVerificationCode.refresh(); //更换图片验证码
                return false;
            }
        }


        if (TextUtils.isEmpty(mEdtRegPassword.getText().toString())) {
            Toast.makeText(mWeakReference.get(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z\\d]{6,15}$");
        Matcher matcher = pattern.matcher(mEdtRegPassword.getText().toString());
        if (!matcher.matches()) {
            Toast.makeText(mWeakReference.get(), "密码格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtRegConfirmPassword.getText().toString())) {
            mEdtRegConfirmPassword.setVisibility(View.VISIBLE);
            Toast.makeText(mWeakReference.get(), "请输入确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mEdtRegPassword.getText().toString().equals(mEdtRegConfirmPassword.getText()
                .toString())) {
            //密码不一致提示可见
            mLlRegisterTip.setVisibility(View.VISIBLE);
            return false;
        } else {
            //密码不一致提示不可见
            mLlRegisterTip.setVisibility(View.GONE);
        }

        if (!mCbAgreeProtocol.isChecked()) {
            Toast.makeText(mWeakReference.get(), "请阅读并同意用户注册协议", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 添加帐号、密码输入框的输入监听以此判断清空按钮的显示或隐藏
     *
     * @param editText
     * @param imgDelete
     */
    private void addInputClearListener(final EditText editText, final ImageView imgDelete, final CheckBox cbPassword) {
        //输入框内有字符的前提下根据输入框获取焦点或失去焦点来显示隐藏
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //获取焦点
                if (b && editText.getText().length() > 0) {
                    //清空图片显示
                    imgDelete.setVisibility(View.VISIBLE);
                    if (null != cbPassword) {
                        //显示密码图片显示
                        cbPassword.setVisibility(View.VISIBLE);
                    }
                    //失去焦点
                }
                if (!b || editText.getText().length() <= 0) {
                    imgDelete.setVisibility(View.INVISIBLE);
                    if (null != cbPassword) {
                        cbPassword.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        //输入框内字符长度改变也会触发清除图片的显示隐藏
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //输入框字符长度大于零且已经获取了焦点
                if (editable.length() > 0 && editText.isFocused()) {
                    imgDelete.setVisibility(View.VISIBLE);
                    if (null != cbPassword) {
                        cbPassword.setVisibility(View.VISIBLE);
                    }
                }
                if (!editText.isFocused() || editable.length() <= 0) {
                    imgDelete.setVisibility(View.INVISIBLE);
                    if (null != cbPassword) {
                        cbPassword.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

    }

    //ViewPager适配器
    class LoginPagerAdapter extends PagerAdapter {

        private List<View> mViewList;

        public LoginPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

    }
}
