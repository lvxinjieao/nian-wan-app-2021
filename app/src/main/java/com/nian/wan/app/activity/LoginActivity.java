package com.nian.wan.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SpannableStringUtils;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.RememberBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.LoginCallBack;
import com.nian.wan.app.utils.Utils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 登录activity
 * Created by Administrator on 2017/4/24.
 */

public class  LoginActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.account_look)
    CheckBox accountLook;
    @BindView(R.id.user_account)
    EditText userAccount;
    @BindView(R.id.pass_look)
    CheckBox passLook;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.cb_jizhu)
    CheckBox cbJizhu;
    @BindView(R.id.forgetpassword)
    TextView forgetpassword;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.error)
    RelativeLayout error;
    @BindView(R.id.zhuce)
    TextView zhuce;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.weixin)
    ImageView weixin;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        initdata();
        DbManager db = DatabaseOpenHelper.getInstance();
        try {
            RememberBean byId = db.findById(RememberBean.class, 1);
            if(byId!=null){
                userAccount.setText(byId.account);
                userPassword.setText(byId.password);
                if(byId.account.equals("")){
                    cbJizhu.setChecked(false);
                }else{
                    cbJizhu.setChecked(true);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initdata() {
        zhuce.setMovementMethod(LinkMovementMethod.getInstance());
        zhuce.setText(SpannableStringUtils.getBuilder("马上注册").setUnderline().create());
        passLook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    userPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    userPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
//        accountLook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(!isChecked){
//                    userAccount.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }else{
//                    userAccount.setInputType(InputType.TYPE_CLASS_TEXT);
//                }
//            }
//        });

         userAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
//                if(s1.equals("")){
//                    accountLook.setVisibility(View.GONE);
//                }else{
//                    accountLook.setVisibility(View.VISIBLE);
//                }
            }
        });
        userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if(s1.equals("")){
                    passLook.setVisibility(View.GONE);
                }else{
                    passLook.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.forgetpassword, R.id.login, R.id.qq, R.id.weixin,R.id.back,R.id.zhuce})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgetpassword:                   //忘记密码
                startActivity(new Intent(this,RetrievePasswordActivity.class));
                break;
            case R.id.login:                            //登录
                Login();
                break;
            case R.id.qq:                               //qq登录
                LoginCallBack.loginType="4";
                ShareSDK.initSDK(this);
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.removeAccount(true);
                ShareSDK.removeCookieOnAuthorize(true);
                qq.setPlatformActionListener(new LoginCallBack(this));
                qq.SSOSetting(false);
                qq.showUser(null);
                break;
            case R.id.weixin:                           //微信登录
                LoginCallBack.loginType="2";
                ShareSDK.initSDK(this);
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                wx.removeAccount(true);
                ShareSDK.removeCookieOnAuthorize(true);
                wx.setPlatformActionListener(new LoginCallBack(this));
                wx.SSOSetting(false);
                wx.showUser(null);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.zhuce:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }

    private void Login() {
        KeyboardUtils.hideSoftInput(this);
        String account = userAccount.getText().toString();
        String password = userPassword.getText().toString();
        if(account.equals("")){
            error.setVisibility(View.VISIBLE);
            errorText.setText("请输入账号");
            return;
        }
        if(password.equals("")){
            error.setVisibility(View.VISIBLE);
            errorText.setText("请输入密码");
            return;
        }
        error.setVisibility(View.INVISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("account",account);
        map.put("password",password);
        HttpConstant.POST(handler, HttpConstant.LoginUrl,map,false);//min
    }


    /**
     * 登录
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    boolean b = HttpUtils.DNSUserLogin(msg.obj.toString());
                    if(b){
                        if(cbJizhu.isChecked()){
                            String account = userAccount.getText().toString();
                            String password = userPassword.getText().toString();
                            RememberBean rememberBean = new RememberBean();
                            rememberBean.id = 1;
                            rememberBean.account = account;
                            rememberBean.password = password;
                            DbManager db = DatabaseOpenHelper.getInstance();
                            try {
                                db.saveOrUpdate(rememberBean);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }else{
                            RememberBean rememberBean = new RememberBean();
                            rememberBean.id = 1;
                            rememberBean.account = "";
                            rememberBean.password = "";
                            DbManager db = DatabaseOpenHelper.getInstance();
                            try {
                                db.saveOrUpdate(rememberBean);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                        LoginActivity.this.finish();
                    }
                    break;
                case 2:
                    error.setVisibility(View.VISIBLE);
                    errorText.setText("网络缓慢");
                    break;
            }
        }
    };
}
