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
import com.nian.wan.app.activity.NewAddressActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 保存地址确认退出
 * @Modified By:
 * @Modified Date:
 */
public class DialogAddressIsConfirmBack extends Dialog {

    @BindView(R.id.tv_unbind_phone_cancel)
    TextView mTvUnbindPhoneFailedCancel;
    @BindView(R.id.tv_unbind_phone_confirm)
    TextView mTvUnbindPhoneFailedConfirm;


    private View inflate;
    private Context mContext;
    private Handler mIsAddressExcitHandler = new Handler();

    public DialogAddressIsConfirmBack(Context context, int themeResId, Handler handler) {
        super(context, themeResId);
        this.mContext = context;
        this.mIsAddressExcitHandler = handler;
        inflate = LinearLayout.inflate(context, R.layout.dialog_address_is_confirm_back, null);
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
                message.what = NewAddressActivity.CONFIRM_EXCIT;
                mIsAddressExcitHandler.sendMessage(message);
                break;
        }
    }
}
