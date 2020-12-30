package com.nian.wan.app.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.ChongZhiPasswordBean;
import com.nian.wan.app.bean.RetrievePasswordBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.PassDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;
import com.mc.developmentkit.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码
 * Created by Administrator on 2017/4/25.
 */

public class RetrievePasswordActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.getyanzhegnma)
    TextView getyanzhegnma;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.et_new_pass)
    EditText etNewPass;
    @BindView(R.id.et_twe_pass)
    EditText etTwePass;
    @BindView(R.id.tv_tijiao)
    TextView tvTijiao;
    @BindView(R.id.zhaohui_one)
    LinearLayout zhaohuiOne;
    @BindView(R.id.zhaohui_twe)
    LinearLayout zhaohuiTwe;
    private boolean niu = false;
    private TimeCount time;
    private String phone;
    private RetrievePasswordBean retrievePasswordBean = new RetrievePasswordBean();
    private ChongZhiPasswordBean chongZhiPasswordBean = new ChongZhiPasswordBean();

    @Override
    public void initView() {
        setContentView(R.layout.activity_retrievepassword);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
    }

    @OnClick({R.id.back, R.id.getyanzhegnma, R.id.tv_next, R.id.tv_tijiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.getyanzhegnma:
                PostMobileNumber();
                break;
            case R.id.tv_next:
                Next();
                break;
            case R.id.tv_tijiao:
                TiJiao();
                break;
        }
    }

    /**
     * 提交新密码
     */
    private void TiJiao() {
        String newpass = etNewPass.getText().toString();
        String TwePass = etTwePass.getText().toString();
        if (newpass.equals("")) {
            ToastUtil.showToast("请输入新密码");
            return;
        }
        if (TwePass.equals("")) {
            ToastUtil.showToast("请再次输入新密码");
            return;
        }

        if (!newpass.equals(TwePass)) {
            ToastUtil.showToast("两次密码输入不一致");
            return;
        }

        if (newpass.length() < 6 || TwePass.length() < 6) {
            ToastUtil.showToast("密码长度不得小于6位");
            return;
        }
        if (newpass.length() > 20 || TwePass.length() > 20) {
            ToastUtil.showToast("密码长度不得大于20位");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("password", newpass);
        map.put("password_again", TwePass);
        map.put("phone", phone);
        HttpConstant.POST(xhandler, HttpConstant.UpPassUrl, map, false);//min
    }

    /**
     * 下一步
     */
    private void Next() {
        phone = etPhone.getText().toString();
        String yanzhengma = etYzm.getText().toString();
        if (phone.equals("")) {
            ToastUtil.showToast("请输入手机号");
            return;
        }
        if (yanzhengma.equals("")) {
            ToastUtil.showToast("请输入验证码");
            return;
        }
        if (!checkIsMobile(phone)
               /* !RegexUtils.isMobileExact(phone)*/) {
            ToastUtil.showToast("手机号码格式不正确");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", yanzhengma);
        HttpConstant.POST(mhandler, HttpConstant.forgetPassword, map, false);//min
    }


    /**
     * 正则匹配是否是手机号
     */
    private boolean checkIsMobile(String phone) {
        //匹配手机号
        Pattern pattern = Pattern.compile("^[1][345678][0-9]{9}");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程显示
            niu = true;
            getyanzhegnma.setClickable(false);
            getyanzhegnma.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            niu = false;
            getyanzhegnma.setText("重新获取");
            getyanzhegnma.setClickable(true);
            getyanzhegnma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostMobileNumber();
                }
            });
        }
    }

    /**
     * 获取验证码
     */
    private void PostMobileNumber() {
        if (!niu) {
            String s = etPhone.getText().toString();
            if (!RegexUtils.isMobileExact(s)) {
                ToastUtil.showToast("请输入正确的手机号码");
            } else {
                time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
                time.start();// 开始计时
                HashMap<String, String> map = new HashMap<>();
                map.put("phone", s);
                map.put("demand", "2");
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
                    Log.e("获取验证码json", msg.obj.toString());
                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };

    /**
     * 提交验证码
     */
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LogUtils.e("提交验证码返回的json", msg.obj.toString());
                    retrievePasswordBean = new Gson().fromJson(msg.obj.toString(), RetrievePasswordBean.class);
                    if (retrievePasswordBean.getStatus() == 1) {
                        zhaohuiOne.setVisibility(View.GONE);
                        zhaohuiTwe.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtil.showToast(retrievePasswordBean.getMsg());
                    }
                    break;
                case 2:
                    // ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };


    /**
     * 提交新密码
     */
    Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("忘记密码提交新密码返回的json", msg.obj.toString());
                    chongZhiPasswordBean = new Gson().fromJson(msg.obj.toString(), ChongZhiPasswordBean.class);
                    if (chongZhiPasswordBean.getStatus() == 1) {
                        PassDialog passDialog = new PassDialog(RetrievePasswordActivity.this, R.style.MyDialogStyle);
                        passDialog.show();
                    } else {
                        ToastUtil.showToast(chongZhiPasswordBean.getMsg());
                    }

                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
            }
        }
    };

}
