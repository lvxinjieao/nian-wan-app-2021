package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.nian.wan.app.R;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogChangePwdSuccess;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 重置密码对话框-第一步获取验证码
 */
public class DialogResetPasswordActivity extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    //关闭重置密码框
    private RelativeLayout close_reset_password;
    private TextView mTvResetPasswordTip;
    //找回密码验证码输入框
    private EditText mEdtResetPwdVerificationCode;
    //找回密码获取验证码
    private Button mBtnResetPwdVerificationCode;
    //找回密码手机号输入框
    private EditText mEdtResetPwdAccount;
    //找回密码下一步按钮
    private Button mBtnResetPasswordNext;
    //提交密码输入框
    private EditText mEdtSubmitPwdNewPassWord;
    //提交密码确认输入框
    private EditText mEdtSubmitPwdConfirmPwd;
    //提交密码提交按钮
    private Button mBtnSubmitPassword;
    //提交密码显示隐藏
    private CheckBox mCbSubmitShowPassword;
    //提交确认密码显示隐藏
    private CheckBox mCbSubmitConfirmPwdShowPwd;
    //提交密码错误tip
    private LinearLayout mllResetPasswordErrorTip;

    //ViewPager包含的登录注册View
    private List<View> mViewList = new ArrayList<>();
    //TabLayout包含的Item标题
    private List<String> mTitleList = new ArrayList<>();
    //弱引用上下文
    private WeakReference<Context> mWeakReference;

    //关闭重置密码对话框
    private static final int ACTION_CLOSE_RESET_PWD = 0;
    //重置密码下一步
    private static final int ACTION_RESET_PASSWORD_NEXT = 1;
    //提交密码
    private static final int ACTION_SUMBIT_PASSWORD = 3;
    //获取验证码
    private static final int ACTION_RESET_PWD_GET_VERIFICATION_CODE = 4;
    private TextView Tv_error;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置弹出框的样式
        setStyle(STYLE_NO_FRAME, R.style.MyDialogStyle);
    }

    public DialogResetPasswordActivity() {

    }

    @SuppressLint("ValidFragment")
    public DialogResetPasswordActivity(Context context) {
        this.mWeakReference = new WeakReference<>(context);
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
            size_y = 0.995f;
            window.getAttributes().width = (int) (windowSize.x * size_x);
            window.getAttributes().height = (int) (windowSize.x * size_y);
        }
        window.setGravity(Gravity.CENTER);
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_reset_password, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_reset_password_content);
        mTabLayout = (TabLayout) view.findViewById(R.id.tb_reset_password);
        close_reset_password = view.findViewById(R.id.close_reset_password);
        close_reset_password.setOnClickListener(this);
        initView(inflater);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView(LayoutInflater inflater) {
        /*找回密码布局start*/
        View resetPassword = inflater.inflate(R.layout.viewpager_reset_password_verify, null);
        //找回密码下一步按钮
        mBtnResetPasswordNext = (Button) resetPassword.findViewById(R.id.btn_reset_password_next);
        mBtnResetPasswordNext.setOnClickListener(this);
        //找回密码底部提示TextView
        mTvResetPasswordTip = (TextView) resetPassword.findViewById(R.id.tv_reset_pwd_tip);
        /*String tips = "通过手机号找回密码只适用于已绑定手机号的账户" +
                ",未绑定手机号账户可<font color='#23bbf3'>联系客服\t〉</font>";*/
        mTvResetPasswordTip.setOnClickListener(this);
        //找回密码获取验证码
        mBtnResetPwdVerificationCode = (Button) resetPassword
                .findViewById(R.id.btn_reset_pwd_verification_code);
        mBtnResetPwdVerificationCode.setOnClickListener(this);
        //找回密码账户输入框
        mEdtResetPwdAccount = (EditText) resetPassword.findViewById(R.id.edt_reset_password_account);
        //找回密码验证码输入框
        mEdtResetPwdVerificationCode = (EditText) resetPassword
                .findViewById(R.id.edt_reset_pwd_verification_code);
        mViewList.add(resetPassword);
        /*找回密码布局end*/

        /*提交密码布局start*/
        View submitPassword = inflater.inflate(R.layout.viewpager_reset_password_submit, null);
        //提交密码错误tip
        mllResetPasswordErrorTip = (LinearLayout) submitPassword
                .findViewById(R.id.ll_reset_password_error_tip);

        Tv_error = (TextView) submitPassword
                .findViewById(R.id.tv_error);
        //提交密码按钮
        mBtnSubmitPassword = (Button) submitPassword
                .findViewById(R.id.btn_submit_pwd_submit_password);
        mBtnSubmitPassword.setOnClickListener(this);
        //提交密码输入框
        mEdtSubmitPwdNewPassWord = (EditText) submitPassword
                .findViewById(R.id.edt_submit_pwd_new_password);
        //提交密码显示隐藏
        mCbSubmitShowPassword = (CheckBox) submitPassword
                .findViewById(R.id.cb_resett_pwd_show_password);
        mCbSubmitShowPassword.setOnCheckedChangeListener(this);
        addInputClearListener(mEdtSubmitPwdNewPassWord, null, mCbSubmitShowPassword);
        //提交密码确认密码输入框
        mEdtSubmitPwdConfirmPwd = (EditText) submitPassword
                .findViewById(R.id.edt_submit_pwd_confirm_password);
        //提交确认密码显示隐藏
        mCbSubmitConfirmPwdShowPwd = (CheckBox) submitPassword
                .findViewById(R.id.cb_reset_pwd_confirm_show_pwd);
        mCbSubmitConfirmPwdShowPwd.setOnCheckedChangeListener(this);
        addInputClearListener(mEdtSubmitPwdConfirmPwd, null, mCbSubmitConfirmPwdShowPwd);
        mViewList.add(submitPassword);
        /*提交密码布局end*/

        //添加页卡标题
        mTitleList.add("找回密码");
        //设置tab模式，当前为系统默认模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        ResetPasswordPagerAdapter pagerAdapter = new ResetPasswordPagerAdapter(mViewList);
        mViewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //关闭重置密码对话框
            case R.id.close_reset_password:
                doClick(ACTION_CLOSE_RESET_PWD);
                break;
            //重置密码下一步按钮
            case R.id.btn_reset_password_next:
                if (checkIsMobile()) {
                    doClick(ACTION_RESET_PASSWORD_NEXT);
                }
                break;
            //提交密码按钮
            case R.id.btn_submit_pwd_submit_password:
                doClick(ACTION_SUMBIT_PASSWORD);
                break;
            //重置密码获取验证码
            case R.id.btn_reset_pwd_verification_code:
                if (checkIsMobile()) {
                    doClick(ACTION_RESET_PWD_GET_VERIFICATION_CODE);
                }
                break;
            //客服
            case R.id.tv_reset_pwd_tip:
                Utils.talkWithQQCustom(getActivity());
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
            //响应关闭重置密码对话框
            case ACTION_CLOSE_RESET_PWD:
                dismissDialog();
                //响应重置密码下一步
            case ACTION_RESET_PASSWORD_NEXT:
                if (checkResetPwdFormat()) {
                    getVerification();
                }
                break;
            //响应提交密码
            case ACTION_SUMBIT_PASSWORD:
                if (checkSubmitPwdFormat()) {
                    dismissDialog();
                    new DialogChangePwdSuccess(mWeakReference.get(), R.style.MyDialogStyle)
                            .show();
                }
                break;
            //响应获取验证码
            case ACTION_RESET_PWD_GET_VERIFICATION_CODE:
                if (checkIsMobile()) {
                    getVerificationCode();
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerificationCode() {
        Map<String, String> verificationCodeParams = new HashMap<>();
        verificationCodeParams.put("phone", mEdtResetPwdAccount.getText().toString());
        verificationCodeParams.put("type", "4");
        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_PERSONAL_USER_SEND_SMS, verificationCodeParams, "获取验证码", false) {

            @Override
            protected void _onSuccess(String s, String msg) {
                //倒计时60秒，一次1秒
                CountDownTimer timer = new CountDownTimer(10 * 6000,
                        1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        mBtnResetPwdVerificationCode.setEnabled(false);
                        mBtnResetPwdVerificationCode.setAlpha(0.6f);
                        mBtnResetPwdVerificationCode.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    //倒计时结束按钮归位
                    @Override
                    public void onFinish() {
                        mBtnResetPwdVerificationCode.setEnabled(true);
                        mBtnResetPwdVerificationCode.setText("获取验证码");
                    }
                }.start();
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
            }
        };
    }

    /**
     * 验证验证码
     */
    private void getVerification() {
        Map<String, String> verificationCodeParams = new HashMap<>();
        verificationCodeParams.put("phone", mEdtResetPwdAccount.getText().toString());
        verificationCodeParams.put("v_code", mEdtResetPwdVerificationCode.getText().toString());
        verificationCodeParams.put("status", "2");

        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.CHECK_CODE, verificationCodeParams, "验证验证码", false) {

            @Override
            protected void _onSuccess(String s, String msg) {
                mViewPager.setCurrentItem(1);
                mTabLayout.getTabAt(0).setText("重置密码");
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
            }
        };
    }


    /**
     * 关闭登录弹出窗
     */
    private void dismissDialog() {
        if (this.getDialog().isShowing()) {
            this.dismiss();
        }
    }

    /**
     * 找回密码输入框非空验证、输入格式验证
     *
     * @return
     */
    public boolean checkResetPwdFormat() {
        if (TextUtils.isEmpty(mEdtResetPwdAccount.getText())) {
            Toast.makeText(mWeakReference.get(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtResetPwdVerificationCode.getText())) {
            Toast.makeText(mWeakReference.get(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean checkIsMobile() {
        if (TextUtils.isEmpty(mEdtResetPwdAccount.getText().toString())) {
            Toast.makeText(mWeakReference.get(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        //匹配手机号
        if (!Utils.isMobileNO(mEdtResetPwdAccount.getText().toString())) {
            Toast.makeText(mWeakReference.get(), "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 提交密码输入框非空验证、输入格式验证
     *
     * @return
     */
    public boolean checkSubmitPwdFormat() {
//        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
//        Matcher matcher = pattern.matcher();
        String s = mEdtSubmitPwdNewPassWord.getText().toString();
        if (TextUtils.isEmpty(mEdtSubmitPwdNewPassWord.getText().toString())) {
            mllResetPasswordErrorTip.setVisibility(View.VISIBLE);
            Tv_error.setText("请输入密码");
            return false;
        }
        if (!(s.length() >= 6 && s.length() <= 20)) {
            mllResetPasswordErrorTip.setVisibility(View.VISIBLE);
            Tv_error.setText("密码格式不正确");
            return false;
        }
        if (TextUtils.isEmpty(mEdtSubmitPwdConfirmPwd.getText().toString())) {
            mllResetPasswordErrorTip.setVisibility(View.VISIBLE);
            Tv_error.setText("请输入确认密码");
            return false;
        }
        if (!mEdtSubmitPwdNewPassWord.getText().toString()
                .equals(mEdtSubmitPwdConfirmPwd.getText().toString())) {
            mllResetPasswordErrorTip.setVisibility(View.VISIBLE);
            Tv_error.setText("密码和确认密码不一致");
            return false;
        } else {
            mllResetPasswordErrorTip.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            //提交密码显示
            case R.id.cb_resett_pwd_show_password:
                if (isChecked) {
                    mEdtSubmitPwdNewPassWord
                            .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtSubmitPwdNewPassWord
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            //提交确认密码显示
            case R.id.cb_reset_pwd_confirm_show_pwd:
                if (isChecked) {
                    mEdtSubmitPwdConfirmPwd
                            .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtSubmitPwdConfirmPwd
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }

    /**
     * 添加帐号、密码输入框的输入监听以此判断清空按钮的显示或隐藏
     *
     * @param editText
     * @param imgDelete
     */
    public void addInputClearListener(final EditText editText, final ImageView imgDelete,
                                      final CheckBox cbPassword) {
        //输入框内有字符的前提下根据输入框获取焦点或失去焦点来显示隐藏
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //获取焦点
                if (b && editText.getText().length() > 0) {
                    if (null != imgDelete) {
                        //清空图片显示
                        imgDelete.setVisibility(View.VISIBLE);
                    }
                    if (null != cbPassword) {
                        //显示密码图片显示
                        cbPassword.setVisibility(View.VISIBLE);
                    }
                    //失去焦点
                }
                if (!b || editText.getText().length() <= 0) {
                    if (null != imgDelete) {
                        //清空图片显示
                        imgDelete.setVisibility(View.INVISIBLE);
                    }
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
                    if (null != imgDelete) {
                        imgDelete.setVisibility(View.VISIBLE);
                    }
                    if (null != cbPassword) {
                        cbPassword.setVisibility(View.VISIBLE);
                    }
                }
                if (!editText.isFocused() || editable.length() <= 0) {
                    if (null != imgDelete) {
                        imgDelete.setVisibility(View.INVISIBLE);
                    }
                    if (null != cbPassword) {
                        cbPassword.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

        if (null != imgDelete) {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("");
                }
            });
        }
    }


    //ViewPager适配器
    class ResetPasswordPagerAdapter extends PagerAdapter {

        private List<View> mViewList;

        public ResetPasswordPagerAdapter(List<View> mViewList) {
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
