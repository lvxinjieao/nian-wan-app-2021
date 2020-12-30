package com.nian.wan.app.fragment.main_my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SpannableStringUtils;
import com.mc.developmentkit.activitys.LoginActivity;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.SignWebActivity;
import com.nian.wan.app.bean.EvenBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.http.TimeService;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 手机号注册
 */
public class RegisterPhone extends Fragment {

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_yzm)
    EditText etYzm;

    @BindView(R.id.getyanzhegnma)
    TextView getyanzhegnma;

    @BindView(R.id.cb_tongyi)
    CheckBox cbTongyi;

    @BindView(R.id.xieyi)
    TextView xieyi;

    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.denglu)
    TextView denglu;

    @BindView(R.id.error_text)
    TextView errorText;

    @BindView(R.id.error)
    RelativeLayout error;

    private boolean niu = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_register_phone, null);
        ButterKnife.bind(this, inflate);
        denglu.setMovementMethod(LinkMovementMethod.getInstance());
        denglu.setText(SpannableStringUtils.getBuilder("马上登录").setUnderline().create());
        return inflate;
    }

    @OnClick({R.id.getyanzhegnma, R.id.xieyi, R.id.tv_register, R.id.denglu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getyanzhegnma:                    //获取验证码
                if (!cbTongyi.isChecked()) {
                    error.setVisibility(View.VISIBLE);
                    errorText.setText("请先同意用户注册协议");
                    return;
                } else {
                    error.setVisibility(View.GONE);
                    PostMobileNumber();
                }
                break;
            case R.id.xieyi:                            //注册协议
                Intent intent = new Intent(getActivity(), SignWebActivity.class);
                intent.putExtra("url", "http://nianwan.cn/app.php?s=/Article/agreement.html");
                intent.putExtra("name", "协议.txt");
                startActivity(intent);
                break;
            case R.id.tv_register:                      //注册
                Register();
                break;
            case R.id.denglu:                           //跳转登录页面
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    private void Register() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String yzm = etYzm.getText().toString();
        if (phone.equals("")) {
            error.setVisibility(View.VISIBLE);
            errorText.setText("请输入手机号");
            return;
        }
        if (password.equals("")) {
            error.setVisibility(View.VISIBLE);
            errorText.setText("请输入密码");
            return;
        }
        if (yzm.equals("")) {
            error.setVisibility(View.VISIBLE);
            errorText.setText("请输入验证码");
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            error.setVisibility(View.VISIBLE);
            errorText.setText("请输入正确的手机号码");
            return;
        }
        if (password.length() < 6) {
            error.setVisibility(View.VISIBLE);
            errorText.setText("密码长度不得小于6位");
            return;
        }
        if (password.length() > 20) {
            error.setVisibility(View.VISIBLE);
            errorText.setText("密码长度不得大于20位");
            return;
        }
        error.setVisibility(View.GONE);
        HashMap<String, String> map = new HashMap<>();
        map.put("account", phone);
        map.put("password", password);
        map.put("vcode", yzm);
        HttpConstant.POST(mhandler, HttpConstant.userPhoneRegister, map, false);//min
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void setText(EvenBean bean) {
        if (bean.a == 2) {
            getyanzhegnma.setClickable(false);
            getyanzhegnma.setText(bean.time);
        } else {
            getyanzhegnma.setClickable(true);
            getyanzhegnma.setText(bean.time);
            getyanzhegnma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostMobileNumber();
                }
            });
        }
    }

    private void PostMobileNumber() {
        if (!niu) {
            String s = etPhone.getText().toString();
            if (!RegexUtils.isMobileExact(s)) {
                error.setVisibility(View.VISIBLE);
                errorText.setText("请输入正确的手机号码");
            } else {
                TimeService.getInstance().StratTime();
                HashMap<String, String> map = new HashMap<>();
                map.put("phone", s);
                HttpConstant.POST(handler, HttpConstant.getPhoneCode, map, false);//min
            }
        }
    }

    /**
     * 获取验证码
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean b = HttpUtils.DNSGetYZM(msg.obj.toString());
                    if (!b) {
                        TimeService.getInstance().StopTime();
                        getyanzhegnma.setText("获取验证码");
                    }
                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };
    /**
     * 注册
     */
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean b = HttpUtils.DNSRegister(msg.obj.toString());
                    if (b) {
                        getActivity().finish();
                    }
                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };
}
