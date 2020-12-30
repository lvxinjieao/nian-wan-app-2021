package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.nian.wan.app.R;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogBindPhoneFailed;
import com.nian.wan.app.view.DialogUnbindPhoneSuccess;
import com.nian.wan.app.view.LoadDialog;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-绑定手机号
 */
public class BindPhoneActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    //手机号标题
    @BindView(R.id.tv_bind_phone_tip_mobile)
    TextView mTvBindPhoneTipMobile;
    //手机号
    @BindView(R.id.edt_bind_phone_mobile)
    EditText mEdtBindPhoneMobile;
    //验证码框
    @BindView(R.id.edt_bind_phone_verification_code)
    EditText mEdtBindPhoneVerificationCode;
    //获取验证码
    @BindView(R.id.tv_bind_phone_get_verification)
    TextView mTvBindPhoneGetVerificationCode;
    //验证码框布局
    @BindView(R.id.rlVerificationCode)
    RelativeLayout rlVerificationCode;
    //提交
    @BindView(R.id.tv_bind_phone_submit)
    TextView mTvBindPhoneSubmit;

    @BindView(R.id.rl_bind_phone_get_verification_already_send)
    RelativeLayout rlBindPhoneGetVerificationAlreadySend;

    @BindView(R.id.tv_bind_phone_get_verification_already_second)
    TextView tvBindPhoneGetVerificationAlreadySecond;

    private LoadDialog loadDialog;

    //获取验证码
    private static final int ACTION_GET_VERIFICATION_CODE = 0;
    //提交
    private static final int ACTION_BIND_PHONE_SUBMIT = 1;


    @Override
    public void initView() {
        setContentView(R.layout.activity_bangphone);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);

        Intent intent = getIntent();
        String recivingAction = intent.getAction();
        modifySumbitButton(false);
        loadDialog = new LoadDialog(BindPhoneActivity.this, R.style.MyDialogStyle);
        mEdtBindPhoneMobile.addTextChangedListener(mobileWatcher);
        mEdtBindPhoneVerificationCode.addTextChangedListener(verificationCodeWatcher);
        title.setText("验证手机号");

        //如果是重新绑定手机号,则局部更新UI
        if (recivingAction == CheckOriginalPhoneActivity.ACTION_INTENT_REBIND_PHONE) {
            title.setText("验证手机号");
            new DialogUnbindPhoneSuccess(this, R.style.MyDialogStyle).show();
//            mTvBindPhoneTipMobile.setText("手机号");
            //如果是解绑手机号
        } else if (recivingAction == AlreadyBindPhoneActivity.ACTION_INTENT_UNBIND_PHONE_SUCCESS) {
            title.setText("验证手机号");
            new DialogUnbindPhoneSuccess(this, R.style.MyDialogStyle).show();
        }
    }


    TextWatcher mobileWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0 && mEdtBindPhoneVerificationCode.getText().toString()
                    .length() > 0) {
                modifySumbitButton(true);
            } else {
                modifySumbitButton(false);
            }
        }
    };

    TextWatcher verificationCodeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0 && mEdtBindPhoneMobile.getText().toString()
                    .length() > 0) {
                modifySumbitButton(true);
            } else {
                modifySumbitButton(false);
            }
        }
    };

    private void modifySumbitButton(boolean isEnabled) {
        if (isEnabled) {
            mTvBindPhoneSubmit.setEnabled(true);
            mTvBindPhoneSubmit.setBackground(ContextCompat.getDrawable(this, R.drawable.zhi_jiao_black));
            mTvBindPhoneSubmit.setTextColor(getResources().getColor(R.color.zi_hei));
        } else {
            mTvBindPhoneSubmit.setEnabled(false);
            mTvBindPhoneSubmit.setBackground(ContextCompat.getDrawable(this, R.drawable.zhi_jiao_gray));
            mTvBindPhoneSubmit.setTextColor(getResources().getColor(R.color.icon_gray));
        }

    }


    @OnClick({R.id.back, R.id.tv_bind_phone_get_verification, R.id.tv_bind_phone_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
            //获取验证码
            case R.id.tv_bind_phone_get_verification:
                doClick(ACTION_GET_VERIFICATION_CODE);
                break;
            //提交
            case R.id.tv_bind_phone_submit:
                doClick(ACTION_BIND_PHONE_SUBMIT);
                break;
        }
    }


    /**
     * 响应点击事件
     *
     * @param action
     */
    public void doClick(int action) {
        switch (action) {
            case ACTION_GET_VERIFICATION_CODE:
                if (checkIsMobile()) {
                    showDialog();
                    //获取验证码
                    getVerificationCode();
                }
                break;
            case ACTION_BIND_PHONE_SUBMIT:
                if (checkIsMobile() && checkInuptFormat()) {
                    //绑定手机号
                    bindPhone();
                }
                break;
        }
    }


    /**
     * 绑定手机号
     */
    private void bindPhone() {
        if (null != Utils.getPersistentUserInfo()) {
            Map<String, String> bindPhoneParams = new HashMap<>();
            bindPhoneParams.put("phone", mEdtBindPhoneMobile.getText().toString());
            bindPhoneParams.put("code", mEdtBindPhoneVerificationCode.getText().toString());
            Type type = new TypeToken<HttpResult<String>>() {
            }.getType();
            new HttpUtils<String>(type, HttpConstant.API_PERSONAL_USER_BIND_PHONE, bindPhoneParams, "绑定手机号", true) {

                @Override
                protected void _onSuccess(String data, String msg) {
                    if (!TextUtils.isEmpty(data)) {
                        Intent bindIntent = new Intent(BindPhoneActivity.this, AlreadyBindPhoneActivity.class);
                        bindIntent.putExtra("bindType", data);
                        bindIntent.putExtra("mobile", mEdtBindPhoneMobile.getText().toString());
                        startActivity(bindIntent);
                        finish();
                    }
                }


                @Override
                protected void _onError(String message, int code) {
                    new DialogBindPhoneFailed(BindPhoneActivity.this, R.style.MyDialogStyle).show();
                    Utils.TS(message);
                }

                @Override
                protected void _onError() {
                    new DialogBindPhoneFailed(BindPhoneActivity.this, R.style.MyDialogStyle).show();
                }
            };
        }
    }


    /**
     * 获取绑定短信验证码
     */
    @SuppressLint("HandlerLeak")
    private void getVerificationCode() {
        Map<String, String> verificationCodeParams = new HashMap<>();
        verificationCodeParams.put("phone", mEdtBindPhoneMobile.getText().toString());
        verificationCodeParams.put("type", "2");
        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_PERSONAL_USER_SEND_SMS, verificationCodeParams, "获取绑定短信验证码", false) {

            @Override
            protected void _onSuccess(String s, String msg) {
                disDialog();
                //倒计时60秒，一次1秒
                CountDownTimer timer = new CountDownTimer(10 * 6000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        mTvBindPhoneGetVerificationCode.setEnabled(false);
                        mTvBindPhoneGetVerificationCode.setVisibility(View.GONE);
                        rlBindPhoneGetVerificationAlreadySend.setVisibility(View.VISIBLE);
                        tvBindPhoneGetVerificationAlreadySecond.setText(String.valueOf(millisUntilFinished / 1000));
                           /* mTvBindPhoneGetVerificationCode
                                    .setText(String.valueOf(millisUntilFinished / 1000));*/
                    }

                    //倒计时结束按钮归位
                    @Override
                    public void onFinish() {
                        mTvBindPhoneGetVerificationCode.setEnabled(true);
                        mTvBindPhoneGetVerificationCode.setText("获取验证码");
                        mTvBindPhoneGetVerificationCode.setVisibility(View.VISIBLE);
                        rlBindPhoneGetVerificationAlreadySend.setVisibility(View.GONE);
                    }
                }.start();
            }

            @Override
            protected void _onError(String message, int code) {
                disDialog();
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                disDialog();
            }
        };
    }


    /**
     * 非空验证
     *
     * @return
     */
    public boolean checkInuptFormat() {
        if (TextUtils.isEmpty(mEdtBindPhoneMobile.getText().toString())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtBindPhoneVerificationCode.getText().toString())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public boolean checkIsMobile() {
        //匹配手机号
        Pattern pattern = Pattern
                .compile("0?(13|14|15|18)[0-9]{9}");
        Matcher matcher = pattern.matcher(mEdtBindPhoneMobile.getText().toString());
        if (!matcher.matches()) {
            Toast.makeText(this, "请正确输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private void showDialog() {
        if (loadDialog != null && !loadDialog.isShowing()) {
            loadDialog.show();
        }
    }

    private void disDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

}
