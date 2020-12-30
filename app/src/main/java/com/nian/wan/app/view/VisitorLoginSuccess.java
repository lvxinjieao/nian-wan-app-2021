package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BindPhoneActivity;
import android.widget.LinearLayout;;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 游客登录成功弹窗
 * @Modified By:
 * @Modified Date:
 */
public class VisitorLoginSuccess extends Dialog {


    @BindView(R.id.img_visitor_login_close)
    RelativeLayout imgVisitorLoginClose;
    @BindView(R.id.tv_visitor_login_success_title)
    TextView tvVisitorLoginSuccessTitle;
    @BindView(R.id.tv_visitor_login_account)
    TextView tvVisitorLoginAccount;
    @BindView(R.id.tv_visitor_login_password)
    TextView tvVisitorLoginPassword;
    @BindView(R.id.ll_visitor_login_account)
    LinearLayout llVisitorLoginAccount;
    @BindView(R.id.tv_visitor_login_go_bind)
    TextView tvVisitorLoginGoBind;
    @BindView(R.id.tv_phone_bind_success)
    TextView tvPhoneBindSuccess;
    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();
    private String visitorAccount;
    private String visitorPassword;
    private String bindPoint;

    public VisitorLoginSuccess(Context context, int themeResId,
                               String visitorAccount, String visitorPassword, String bindPoint) {
        super(context, themeResId);
        this.mContext = context;
        inflate = LinearLayout.inflate(context, R.layout.visitor_login_success, null);
        this.visitorAccount = visitorAccount;
        this.visitorPassword = visitorPassword;
        this.bindPoint = bindPoint;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvVisitorLoginAccount.setText(visitorAccount);
        tvVisitorLoginPassword.setText(visitorPassword);
        String point = tvPhoneBindSuccess.getText().toString()
                .replace("**", String.valueOf(bindPoint));
        tvPhoneBindSuccess.setText(point);
    }

    /**
     * 关闭登录弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.img_visitor_login_close, R.id.tv_visitor_login_go_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //关闭弹窗
            case R.id.img_visitor_login_close:
                dismissDialog();
                break;
            //去绑定
            case R.id.tv_visitor_login_go_bind:
                dismissDialog();
                mContext.startActivity(new Intent(mContext, BindPhoneActivity.class));
                break;
        }
    }
}
