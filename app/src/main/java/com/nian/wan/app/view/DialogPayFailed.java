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
 * @Date: Created in 充值失败弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogPayFailed extends Dialog {

    @BindView(R.id.tv_pay_failed_cancel)
    TextView mTvPayFailedCancel;
    @BindView(R.id.tv_pay_failed_retry)
    TextView mTvPayFailedRetry;


    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();

    public DialogPayFailed(Context context, int themeResId) {
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


    @OnClick({R.id.tv_pay_failed_cancel, R.id.tv_pay_failed_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            //取消
            case R.id.tv_pay_failed_cancel:
                dismissDialog();
                break;
            //重试
            case R.id.tv_pay_failed_retry:
                dismissDialog();
                break;
        }
    }
}
