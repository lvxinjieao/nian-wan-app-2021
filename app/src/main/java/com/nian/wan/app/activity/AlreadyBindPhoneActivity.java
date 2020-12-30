package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogBindPhoneIsSame;
import com.nian.wan.app.view.DialogBindPhoneSuccess;
import com.nian.wan.app.view.DialogIsUnbindPhone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-已绑定手机号
 */
public class AlreadyBindPhoneActivity extends FragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    //更换手机号
    @BindView(R.id.tv_already_bind_phone_replace)
    TextView mTvAlreadyBindPhoneReplace;
    //解除绑定手机号
    @BindView(R.id.img_already_bind_phone_unbind)
    ImageView mImgAlreadyBindPhoneUnbind;
    @BindView(R.id.phone)
    TextView phone;

    private String bindType;
    private String mobile;
    private static final int ACTION_REPLACE_PHONE = 0;
    private static final int ACTION_UNBIND_PHONE = 1;
    public static final int EVENT_UNBIND_PHONE_SUCCESS = 2;
    public static final int EVENT_UNBIND_PHONE_FAILED = 3;
    public static final String ACTION_INTENT_UNBIND_PHONE_SUCCESS = "ACTION_INTENT_UNBIND_PHONE_SUCCESS";


    @SuppressLint("HandlerLeak")
    private Handler mAlreadyBindPhoneHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //解绑手机成功
                case EVENT_UNBIND_PHONE_SUCCESS:
                    Intent intent = new Intent(AlreadyBindPhoneActivity.this, CheckOriginalPhoneActivity.class);
                    intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                    startActivity(intent);
                    finish();
                    break;
                //解绑手机失败
                case EVENT_UNBIND_PHONE_FAILED:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_replacephone);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        bindType = getIntent().getStringExtra("bindType");
        mobile = getIntent().getStringExtra("mobile");
        title.setText("绑定手机号");

        phone.setText("您绑定的手机号：" + Utils.YinCang(mobile));
        if (!TextUtils.isEmpty(bindType)){
            //首次绑定积分字段data才会有值，再次绑定返回0
            if ("-1".equals(bindType)) {
                new DialogBindPhoneIsSame(AlreadyBindPhoneActivity.this, R.style.MyDialogStyle, Utils.YinCang(mobile)).show();
            } else {
                new DialogBindPhoneSuccess(AlreadyBindPhoneActivity.this, R.style.MyDialogStyle, bindType).show();
            }
        }
    }

    @OnClick({R.id.back, R.id.tv_already_bind_phone_replace, R.id.img_already_bind_phone_unbind})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
            //更换手机号
            case R.id.tv_already_bind_phone_replace:
                doClick(ACTION_REPLACE_PHONE);
                break;
            //解除绑定手机号
            case R.id.img_already_bind_phone_unbind:
                doClick(ACTION_UNBIND_PHONE);
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
            //响应更换手机号
            case ACTION_REPLACE_PHONE:
                new DialogIsUnbindPhone(this, R.style.MyDialogStyle, mAlreadyBindPhoneHandler).show();
                break;
            //响应解除绑定手机号
            case ACTION_UNBIND_PHONE:

                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
