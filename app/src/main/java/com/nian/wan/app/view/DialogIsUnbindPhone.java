package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.AlreadyBindPhoneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 确认解绑手机号弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogIsUnbindPhone extends Dialog {

    @BindView(R.id.tv_unbind_phone_cancel)
    TextView mTvUnbindPhoneFailedCancel;
    @BindView(R.id.tv_unbind_phone_confirm)
    TextView mTvUnbindPhoneFailedConfirm;


    private View inflate;
    private Context mContext;
    private Handler mIsUnbindPhoneHandler = new Handler();

    public DialogIsUnbindPhone(Context context, int themeResId, Handler handler) {
        super(context, themeResId);
        this.mContext = context;
        this.mIsUnbindPhoneHandler = handler;
        inflate = LinearLayout.inflate(context, R.layout.dialog_unbind_phone, null);
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


    @OnClick({R.id.tv_unbind_phone_cancel, R.id.tv_unbind_phone_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            //取消
            case R.id.tv_unbind_phone_cancel:
                dismissDialog();
                break;
            //确认
            case R.id.tv_unbind_phone_confirm:
                dismissDialog();
                Message message = Message.obtain();
                message.what = AlreadyBindPhoneActivity.EVENT_UNBIND_PHONE_SUCCESS;
                mIsUnbindPhoneHandler.sendMessage(message);
                break;
        }
    }
}
