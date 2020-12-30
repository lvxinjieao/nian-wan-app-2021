package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 退出确认弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogExcitConfirm extends Dialog {

    @BindView(R.id.tv_excit_login_confirm_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_excit_login_confirm)
    TextView mTvConfirm;


    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();

    public DialogExcitConfirm(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        inflate = LinearLayout.inflate(context, R.layout.dialog_excit_confirm, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.tv_excit_login_confirm_cancel, R.id.tv_excit_login_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            //取消
            case R.id.tv_excit_login_confirm_cancel:
                dismissDialog();
                break;
            //确认
            case R.id.tv_excit_login_confirm:
                Utils.deletePersistentUserInfo();
                dismissDialog();
                Intent intent = new Intent("com.yinu.login");
                intent.putExtra("login_status",
                        DialogLoginActivity.EVENT_LOGIN_EXCIT);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                break;
        }
    }
}
