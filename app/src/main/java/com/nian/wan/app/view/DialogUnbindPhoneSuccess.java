package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.nian.wan.app.R;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 成功解绑手机弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogUnbindPhoneSuccess extends Dialog {


    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();

    public DialogUnbindPhoneSuccess(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        inflate = LinearLayout.inflate(context, R.layout.dialog_unbind_phone_success, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        //倒计时60秒，一次1秒
        CountDownTimer timer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            //倒计时结束按钮归位
            @Override
            public void onFinish() {
                dismissDialog();
            }
        }.start();
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }

}
