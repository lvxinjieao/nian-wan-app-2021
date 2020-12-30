package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 绑定手机号码重复弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogBindPhoneIsSame extends Dialog {

    private final String string;
    //关闭成功绑定手机弹窗
    @BindView(R.id.img_bind_phone_success_close)
    RelativeLayout mImgBindPhoneSuccessClose;
    @BindView(R.id.tv_phone_bind_success)
    TextView tvPhoneBindSuccess;
    @BindView(R.id.tv_phone)
    TextView tvPhone;


    private View inflate;
    private Context mContext;

    public DialogBindPhoneIsSame(Context context, int themeResId,String phone) {
        super(context, themeResId);
        this.mContext = context;
        string = phone;
        inflate = LinearLayout.inflate(context, R.layout.dialog_phone_bind_same, null);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvPhone.setText("绑定手机号："+string);
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.img_bind_phone_success_close})
    public void onClick(View view) {
        switch (view.getId()) {
            //关闭成功绑定手机弹窗
            case R.id.img_bind_phone_success_close:
                dismissDialog();
                break;
        }
    }
}
