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

import com.blankj.utilcode.util.SpannableStringUtils;
import com.mc.developmentkit.activitys.LoginActivity;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.SignWebActivity;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.view.ValidationCode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 普通账号注册
 */
public class RegisterAccount extends Fragment {

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_yzm)
    EditText etYzm;

    @BindView(R.id.getyanzhegnma)
    ValidationCode getyanzhegnma;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_register_account, null);
        ButterKnife.bind(this, inflate);
        denglu.setMovementMethod(LinkMovementMethod.getInstance());
        denglu.setText(SpannableStringUtils.getBuilder("马上登录").setUnderline().create());
        return inflate;
    }

    @OnClick({R.id.xieyi, R.id.tv_register, R.id.denglu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xieyi:                    //协议.txt
                Intent intent =new Intent(getActivity(), SignWebActivity.class);
                intent.putExtra("url","http://newh5.vlcms.com/app.php?s=/Article/agreement.html");
                intent.putExtra("name", "协议.txt");
                startActivity(intent);
                break;
            case R.id.tv_register:              //注册
                Register();
                break;
            case R.id.denglu:                   //登录
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
            errorText.setText("请输入用户名或手机号");
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
        if(password.length()<6){
            error.setVisibility(View.VISIBLE);
            errorText.setText("密码长度不得小于6位");
            return;
        }
        if(password.length()>20){
            error.setVisibility(View.VISIBLE);
            errorText.setText("密码长度不得大于20位");
            return;
        }
        if(!getyanzhegnma.isEqualsIgnoreCase(yzm)){
            error.setVisibility(View.VISIBLE);
            errorText.setText("验证码输入有误");
            return;
        }
        if(!cbTongyi.isChecked()){
            error.setVisibility(View.VISIBLE);
            errorText.setText("请先同意用户注册协议");
            return;
        }
        error.setVisibility(View.GONE);
        HashMap<String, String> map = new HashMap<>();
        map.put("account",phone);
        map.put("password",password);
        HttpConstant.POST(mhandler, HttpConstant.userRegister,map,false);//min
    }


    /**
     * 注册
     */
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean b = HttpUtils.DNSRegister(msg.obj.toString());
                    if(b){
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
