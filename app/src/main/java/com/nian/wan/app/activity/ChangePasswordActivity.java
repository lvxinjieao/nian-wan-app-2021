package com.nian.wan.app.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.UserChangePasswordBean;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-修改密码
 */
public class ChangePasswordActivity extends BaseFragmentActivity implements
        CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    //原有密码
    @BindView(R.id.edt_modify_original_paswword)
    EditText mEdtModifyOriginalPassword;
    //新密码
    @BindView(R.id.edt_modify_new_paswword)
    EditText mEdtModifyNewPassword;
    //确认密码
    @BindView(R.id.edt_modify_confirm_paswword)
    EditText mEdtModifyConfirmPassword;
    //提交密码
    @BindView(R.id.tv_modify_submit_paswword)
    TextView mTvModifySubmitPassword;
    //显示原有密码
    @BindView(R.id.cb_modify_show_password)
    CheckBox mCbModifyShowPassword;
    //清除新密码
    @BindView(R.id.img_modify_clear_new_password)
    ImageView mImgModifyClearNewPassword;
    //显示新密码
    @BindView(R.id.cb_modify_new_show_password)
    CheckBox mCbModifyNewShowPassword;
    //清除确认密码
    @BindView(R.id.img_mofidy_clear_confirm_password)
    ImageView mImgModifyClearConfirmPassword;
    //显示确认密码
    @BindView(R.id.cb_modify_comfirm_show_password)
    CheckBox mCbModifyConfirmShowPassword;

    private UserChangePasswordBean userChangePasswordBean;


    private static final int ACTION_REPLACE_PHONE = 0;
    private static final int ACTION_UNBIND_PHONE = 1;
    public static final int EVENT_UNBIND_PHONE_SUCCESS = 2;
    public static final int EVENT_UNBIND_PHONE_FAILED = 3;
    public static final String ACTION_INTENT_UNBIND_PHONE_SUCCESS
            = "ACTION_INTENT_UNBIND_PHONE_SUCCESS";
    public static final int ACTION_CHANGE_PASSWORD = 4;


    @Override
    public void initView() {
        setContentView(R.layout.activity_modifypassword);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("修改密码");
        modifySumbitButton(false);
        addInputClearListener(mEdtModifyOriginalPassword, null, mCbModifyShowPassword);
        addInputClearListener(mEdtModifyNewPassword, mImgModifyClearNewPassword, mCbModifyNewShowPassword);
        addInputClearListener(mEdtModifyConfirmPassword, mImgModifyClearConfirmPassword, mCbModifyConfirmShowPassword);
        mCbModifyShowPassword.setOnCheckedChangeListener(this);
        mCbModifyNewShowPassword.setOnCheckedChangeListener(this);
        mCbModifyConfirmShowPassword.setOnCheckedChangeListener(this);
    }

    private void modifySumbitButton(boolean isEnabled) {
        if (isEnabled) {
            mTvModifySubmitPassword.setEnabled(true);
            mTvModifySubmitPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.zhi_jiao_black));
            mTvModifySubmitPassword.setTextColor(getResources().getColor(R.color.zi_hei));
        } else {
            mTvModifySubmitPassword.setEnabled(false);
            mTvModifySubmitPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.zhi_jiao_gray));
            mTvModifySubmitPassword.setTextColor(getResources().getColor(R.color.icon_gray));
        }

    }


    @OnClick({R.id.back, R.id.tv_modify_submit_paswword})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
            //提交密码
            case R.id.tv_modify_submit_paswword:
                if (checkModifyPwdFormat()) {
                    doClick(ACTION_CHANGE_PASSWORD);
                }
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
            case ACTION_CHANGE_PASSWORD:
                changePassword();
                break;
        }
    }

    /**
     * 更改密码
     */
    public void changePassword() {
        if (null != Utils.getPersistentUserInfo()) {
            Map<String, String> changePasswordParams = new HashMap<>();
            changePasswordParams.put("oldpwd", mEdtModifyOriginalPassword.getText().toString());
            changePasswordParams.put("newpwd", mEdtModifyNewPassword.getText().toString());
            changePasswordParams.put("nnewpwd", mEdtModifyConfirmPassword.getText().toString());
            Type type = new TypeToken<HttpResult<String>>() {
            }.getType();
            new HttpUtils<String>(type, HttpConstant.API_PERSONAL_CENTER_CHANGE_PWD, changePasswordParams, "更改密码", true) {
                @Override
                protected void _onSuccess(String s, String msg) {
                    Intent intent = new Intent();
                    intent.putExtra("change_pwd_msg", s);
                    setResult(PersonalCenter.CHANGE_PASSWORD_SUCCESS, intent);
                    ChangePasswordActivity.this.finish();
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
    }

    /**
     * 找回密码输入框非空验证、输入格式验证
     *
     * @return
     */
    public boolean checkModifyPwdFormat() {
        if (TextUtils.isEmpty(mEdtModifyOriginalPassword.getText())) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return false;
        }
//        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$");
//        Matcher matcher = pattern.matcher(mEdtModifyNewPassword.getText().toString());
//        if (!matcher.matches()) {
//            Toast.makeText(this, "密码格式不正确", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        String s = mEdtModifyNewPassword.getText().toString();
        if (!(s.length() >= 6 && s.length() <= 15)) {
            Toast.makeText(this, "新密码格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mEdtModifyNewPassword.getText().toString()
                .equals(mEdtModifyConfirmPassword.getText().toString())) {
            Toast.makeText(this, "新密码和确认密码不一致", Toast.LENGTH_SHORT).show();
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
    public void addInputClearListener(final EditText editText, final ImageView imgDelete, final CheckBox cbPassword) {
        //输入框内有字符的前提下根据输入框获取焦点或失去焦点来显示隐藏
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //获取焦点
                if (b && editText.getText().length() > 0) {
                    modifySumbitButton(true);
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
                    modifySumbitButton(false);
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
                    modifySumbitButton(true);
                    if (null != imgDelete) {
                        imgDelete.setVisibility(View.VISIBLE);
                    }
                    if (null != cbPassword) {
                        cbPassword.setVisibility(View.VISIBLE);
                    }
                }
                if (!editText.isFocused() || editable.length() <= 0) {
                    modifySumbitButton(false);
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

    /**
     * 监听显示隐藏密码checked事件
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            //显示或隐藏原有密码
            case R.id.cb_modify_show_password:
                if (isChecked) {
                    mEdtModifyOriginalPassword
                            .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtModifyOriginalPassword
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            //显示新密码
            case R.id.cb_modify_new_show_password:
                if (isChecked) {
                    mEdtModifyNewPassword
                            .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtModifyNewPassword
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            //显示确认密码
            case R.id.cb_modify_comfirm_show_password:
                if (isChecked) {
                    mEdtModifyConfirmPassword
                            .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtModifyConfirmPassword
                            .setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }
}
