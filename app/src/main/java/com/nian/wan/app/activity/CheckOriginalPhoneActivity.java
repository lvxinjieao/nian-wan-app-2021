package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.UserBindPhoneBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.LoadDialog;
import com.google.gson.reflect.TypeToken;
;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-绑定手机号-更换手机号验证原手机
 */
public class CheckOriginalPhoneActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    //原手机号
    @BindView(R.id.tv_replace_phone_original_phone)
    TextView mTvReplacePhoneOriginalPhone;
    //验证码框
    @BindView(R.id.edt_replace_phone_verification_code)
    EditText mEdtReplacePhoneVerificationCode;
    //获取验证码
    @BindView(R.id.tv_replace_phone_get_verification_code)
    TextView mTvReplacePhoneGetVerificationCode;
    //联系客服
    @BindView(R.id.ll_replace_phone_custom_service)
    LinearLayout mLlContactCustom;
    //提交
    @BindView(R.id.tv_replace_phone_next)
    TextView mTvReplacePhoneSubmit;
    @BindView(R.id.ll_check_code_error_tip)
    LinearLayout llCheckCodeErrorTip;
    @BindView(R.id.tv_error)
    TextView tvError;

    private LoadDialog loadDialog;
    private UserBindPhoneBean userBindPhoneBean;

    //获取验证码
    private static final int ACTION_GET_VERIFICATION_CODE = 0;
    //提交
    private static final int ACTION_REPLACE_PHONE_SUBMIT = 1;
    //联系客服
    private static final int ACTION_CUSTOM_SERVICE = 2;
    //重新绑定手机号意图
    public static final String ACTION_INTENT_REBIND_PHONE = "ACTION_INTENT_REBIND_PHONE";

    @Override
    public void initView() {
        setContentView(R.layout.activity_yanzhengphone);
        ButterKnife.bind(this);
        loadDialog = new LoadDialog(CheckOriginalPhoneActivity.this,R.style.MyDialogStyle);
        Utils.initActionBarPosition(this, tou);
        title.setText("验证原手机号");
        mTvReplacePhoneOriginalPhone.setText(Utils.YinCang(getIntent()
                .getStringExtra("mobile")));
    }


    @OnClick({R.id.back, R.id.tv_replace_phone_next, R.id.tv_replace_phone_get_verification_code,
            R.id.ll_replace_phone_custom_service})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
            //获取验证码
            case R.id.tv_replace_phone_get_verification_code:
                doClick(ACTION_GET_VERIFICATION_CODE);
                break;
            //提交
            case R.id.tv_replace_phone_next:
                doClick(ACTION_REPLACE_PHONE_SUBMIT);
                break;
            //联系客服
            case R.id.ll_replace_phone_custom_service:
                doClick(ACTION_CUSTOM_SERVICE);
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
            //获取验证码
            case ACTION_GET_VERIFICATION_CODE:
                showDialog();
                getVerificationCode();
                break;
            //更换手机提交
            case ACTION_REPLACE_PHONE_SUBMIT:
                if (checkInuptFormat()) {
                    showDialog();
                    unBindPhone();
                }
                break;
            //客服
            case ACTION_CUSTOM_SERVICE:

                break;
        }
    }


    /**
     * 解绑手机号
     */
    private void unBindPhone() {
        Map<String, String> bindPhoneParams = new HashMap<>();
        if (null != Utils.getPersistentUserInfo()) {
            bindPhoneParams.put("phone", getIntent()
                    .getStringExtra("mobile"));
            bindPhoneParams.put("code", mEdtReplacePhoneVerificationCode.getText().toString());
            Type type = new TypeToken<HttpResult<String>>() {
            }.getType();
            new HttpUtils<String>(type, HttpConstant.API_PERSONAL_USER_UNBIND_PHONE, bindPhoneParams, "解绑手机号", true) {

                @Override
                protected void _onSuccess(String s, String msg) {
                    disDialog();
                    CheckOriginalPhoneActivity.this.finish();
                    //跳转绑定手机号Activity
                    Intent intent = new Intent();
                    intent.setAction(ACTION_INTENT_REBIND_PHONE);
                    intent.setClass(CheckOriginalPhoneActivity.this, BindPhoneActivity.class);
                    CheckOriginalPhoneActivity.this.startActivity(intent);
                }

                @Override
                protected void _onError(String message, int code) {
                    llCheckCodeErrorTip.setVisibility(View.VISIBLE);
                    tvError.setText(message);
                    disDialog();
                }

                @Override
                protected void _onError() {
                    disDialog();
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
        verificationCodeParams.put("phone", getIntent().getStringExtra("mobile"));
        verificationCodeParams.put("type", "3");

        Type type = new TypeToken<HttpResult<String>>() {
        }.getType();
        new HttpUtils<String>(type, HttpConstant.API_PERSONAL_USER_SEND_SMS, verificationCodeParams, "验证验证码", false) {

            @Override
            protected void _onSuccess(String s, String msg) {
                //倒计时60秒，一次1秒
                CountDownTimer timer = new CountDownTimer(10 * 6000,
                        1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        mTvReplacePhoneGetVerificationCode.setEnabled(false);
                        mTvReplacePhoneGetVerificationCode
                                .setText(String.valueOf(millisUntilFinished / 1000) + "s");
                    }

                    //倒计时结束按钮归位
                    @Override
                    public void onFinish() {
                        mTvReplacePhoneGetVerificationCode.setEnabled(true);
                        mTvReplacePhoneGetVerificationCode.setText("获取验证码");
                    }
                }.start();
                disDialog();
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
        if (TextUtils.isEmpty(mEdtReplacePhoneVerificationCode.getText().toString())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
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


    private void showDialog(){
        if (loadDialog!=null && !loadDialog.isShowing()){
            loadDialog.show();
        }
    }

    private void disDialog(){
        if (loadDialog!=null && loadDialog.isShowing()){
            loadDialog.dismiss();
        }
    }

}
