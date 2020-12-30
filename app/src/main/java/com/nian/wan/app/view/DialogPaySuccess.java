package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 充值成功弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogPaySuccess extends Dialog {

    @BindView(R.id.tv_pay_success_cancel)
    TextView mTvPaySuccessCancel;
    @BindView(R.id.tv_pay_success_confirm)
    TextView mTvPaySuccessConfirm;


    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();

    public DialogPaySuccess(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        inflate = LinearLayout.inflate(context, R.layout.dialog_pay_success, null);
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


    @OnClick({R.id.tv_pay_success_cancel, R.id.tv_pay_success_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            //取消
            case R.id.tv_pay_success_cancel:
                dismissDialog();
                break;
            //确认
            case R.id.tv_pay_success_confirm:
                dismissDialog();
                break;
        }
    }
}
