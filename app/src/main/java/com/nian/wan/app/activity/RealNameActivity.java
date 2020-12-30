package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogRealNameSuccess;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-实名认证
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/2 14:54
 * @Modified By:
 * @Modified Date:
 */
public class RealNameActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;

    //真实姓名
    @BindView(R.id.edt_real_name_your_name)
    EditText mEdtRealNameYourName;
    //身份证号
    @BindView(R.id.edt_real_name_id_card)
    EditText mEdtRealNameIdCard;
    //提交
    @BindView(R.id.btn_real_name_sumbit)
    Button mBtnRealNameSubmit;
    //错误提示
    @BindView(R.id.ll_real_name_error_tip)
    LinearLayout mLlRealNameErrorTip;

    //提交实名认证
    private static final int ACTION_SUBMIT_REAL_NAME = 0;
    //实名认证成功
    public static final int EVENT_REAL_NAME_SUCCESS = 1;
    //实名认证失败
    public static final int EVENT_REAL_NAME_FAILED = 2;
    //实名认证返回详情页
    public static final int EVENT_REAL_NAME_DETAIL = 3;
    //H5游戏传来的重新加载URL
    private String reloadUrl;
    //是否要执行H5重新加载
    private boolean h5GameReload;

    @SuppressLint("HandlerLeak")
    private Handler mRealNameHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //实名认证成功
                case EVENT_REAL_NAME_SUCCESS:
                    setResult(PersonalCenter.AUTH_REAL_NAME_SUCCESS);
                    finish();
                    break;
                //实名认证失败
                case EVENT_REAL_NAME_FAILED:
                    mLlRealNameErrorTip.setVisibility(View.VISIBLE);
                    setResult(PersonalCenter.AUTH_REAL_NAME_FAILED);
                    finish();
                    break;
                //实名认证返回详情页
                case EVENT_REAL_NAME_DETAIL:
                    Intent intent = new Intent(RealNameActivity.this, AlreadyRealNameActivity.class);
                    intent.putExtra("personName", msg.getData().getString("personName"));
                    intent.putExtra("idCard", msg.getData().getString("idCard"));
                    intent.putExtra("reloadUrl", msg.getData().getString("reloadUrl"));
                    intent.putExtra("h5GameReload", msg.getData().getBoolean("h5GameReload"));
                    RealNameActivity.this.startActivity(intent);
                    finish();
                    break;
            }
        }
    };


    @Override
    public void initView() {
        setContentView(R.layout.activity_real_name);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        this.h5GameReload = getIntent().getBooleanExtra("h5_game_reload", false);
        this.reloadUrl = getIntent().getStringExtra("reload_url");
        title.setText("实名认证");
        modifySumbitButton(false);
        mEdtRealNameYourName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && mEdtRealNameIdCard.getText().length() > 0) {
                    modifySumbitButton(true);
                } else {
                    modifySumbitButton(false);
                }
            }
        });
        mEdtRealNameIdCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && mEdtRealNameYourName.getText().length() > 0) {
                    modifySumbitButton(true);
                } else {
                    modifySumbitButton(false);
                }
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                mEdtRealNameIdCard.setText(mEdtRealNameIdCard.getText()
                        .toString().toLowerCase());
                break;
            case MotionEvent.ACTION_DOWN:
                mEdtRealNameIdCard.setText(mEdtRealNameIdCard.getText()
                        .toString().toLowerCase());
                break;
            case MotionEvent.ACTION_MOVE:
                mEdtRealNameIdCard.setText(mEdtRealNameIdCard.getText()
                        .toString().toLowerCase());
                break;
        }
        return super.onTouchEvent(event);
    }

    private void modifySumbitButton(boolean isEnabled) {
        if (isEnabled) {
            mBtnRealNameSubmit.setEnabled(true);
            mBtnRealNameSubmit.setBackground(ContextCompat
                    .getDrawable(this, R.drawable.zhi_jiao_gray));
            mBtnRealNameSubmit.setTextColor(getResources().getColor(R.color.bai));
        } else {
            mBtnRealNameSubmit.setEnabled(false);
            mBtnRealNameSubmit.setBackground(ContextCompat
                    .getDrawable(this, R.drawable.zhi_jiao_gray));
            mBtnRealNameSubmit.setTextColor(getResources().getColor(R.color.font_d2d2d2));
        }

    }

    @OnClick({R.id.btn_real_name_sumbit, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            //实名认证提交
            case R.id.btn_real_name_sumbit:
                doClick(ACTION_SUBMIT_REAL_NAME);
                break;
            //返回
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 实名认证
     */
    public void realName() {
        if (null != Utils.getPersistentUserInfo()) {
            Map<String, String> realNameParams = new HashMap<>();
            realNameParams.put("idcard", mEdtRealNameIdCard.getText().toString());
            realNameParams.put("real_name", mEdtRealNameYourName.getText().toString());
            Type type = new TypeToken<HttpResult<String>>() {}.getType();
            new HttpUtils<String>(type, HttpConstant.API_PERSONAL_CENTER_USER_AUTH, realNameParams, "实名认证", true) {

                @Override
                protected void _onSuccess(String s, String msg) {
                    new DialogRealNameSuccess(RealNameActivity.this,
                            R.style.MyDialogStyle, mRealNameHandler, h5GameReload, reloadUrl)
                            .show();
                }

                @Override
                protected void _onError(String message, int code) {
                    if (1122 == code) {
                        mLlRealNameErrorTip.setVisibility(View.VISIBLE);
                    } else {
                        mLlRealNameErrorTip.setVisibility(View.INVISIBLE);
                        ToastUtil.showToast(message);
                    }
                }

                @Override
                protected void _onError() {
                }
            };
        }
    }



    /**
     * 响应点击事件
     *
     * @param action
     */
    public void doClick(int action) {
        switch (action) {
            case ACTION_SUBMIT_REAL_NAME:
                //检查输入格式
                if (checkInputFromat()) {
                    realName();
                }
                break;
        }
    }


    /**
     * 实名认证非空验证，格式验证
     */
    private boolean checkInputFromat() {
        Pattern patternIdCard = Pattern.compile("\\d{17}[\\d|x]|\\d{15}");
        Matcher matcherIdCard = patternIdCard.matcher(mEdtRealNameIdCard.getText().toString());
        if (!matcherIdCard.matches()) {
            Toast.makeText(this, "身份证号码填写不正确，如有字母请小写", Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern patternName = Pattern.compile("^[\\u4e00-\\u9fa5]+(·[\\u4e00-\\u9fa5]+)*$");
        Matcher matcherName = patternName.matcher(mEdtRealNameYourName.getText().toString());
        if (!matcherName.matches()) {
            Toast.makeText(this, "请正确输入姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 返回按键监听
     */
    @Override
    public void onBackPressed() {
        finish();
    }

}
