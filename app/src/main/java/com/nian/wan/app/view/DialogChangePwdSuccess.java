package com.nian.wan.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 更改密码成功弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogChangePwdSuccess extends Dialog {

    @BindView(R.id.tv_change_password_go_login)
    TextView mTvGoLogin;
    @BindView(R.id.img_change_password_close)
    ImageView mImgChangePasswordClose;


    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();

    public DialogChangePwdSuccess(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        inflate = LinearLayout.inflate(context, R.layout.dialog_change_password_success, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
    }

    /**
     * 关闭登录弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.tv_change_password_go_login, R.id.img_change_password_close})
    public void onClick(View view) {
        switch (view.getId()) {
            //去登录
            case R.id.tv_change_password_go_login:
                dismissDialog();
                Activity activity = (Activity) mContext;
                DialogLoginActivity loginActivity =
                        new DialogLoginActivity(getContext(), "LGOIN");
                FragmentTransaction transaction = activity
                        .getFragmentManager()
                        .beginTransaction();
                transaction.add(loginActivity, "WoDe");
                transaction.show(loginActivity);
                transaction.commitAllowingStateLoss();
                break;
            //关闭重置密码成功窗口
            case R.id.img_change_password_close:
                dismissDialog();
                break;
        }
    }
}
