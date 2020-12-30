package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BalanceActivity;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 成功绑定手机弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogBindPhoneSuccess extends Dialog {

    //关闭成功绑定手机弹窗
    @BindView(R.id.img_bind_phone_success_close)
    RelativeLayout mImgBindPhoneSuccessClose;
    //去领取
    @BindView(R.id.tv_bind_phone_success_go_get)
    TextView mTvBindPhoneSuccessGoGet;
    @BindView(R.id.tv_phone_bind_success)
    TextView tvPhoneBindSuccess;


    private View inflate;
    private Context mContext;
    private String score;

    public DialogBindPhoneSuccess(Context context, int themeResId,String score) {
        super(context, themeResId);
        this.mContext = context;
        inflate = LinearLayout.inflate(context, R.layout.dialog_phone_bind_success, null);
        this.score = score;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvPhoneBindSuccess.setText("绑定成功!获得" + score + "积分!");
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.img_bind_phone_success_close, R.id.tv_bind_phone_success_go_get})
    public void onClick(View view) {
        switch (view.getId()) {
            //关闭成功绑定手机弹窗
            case R.id.img_bind_phone_success_close:
                dismissDialog();
                break;
            //查看积分
            case R.id.tv_bind_phone_success_go_get:
                dismissDialog();
                mContext.startActivity(new Intent(mContext, BalanceActivity.class));
                break;
        }
    }
}
