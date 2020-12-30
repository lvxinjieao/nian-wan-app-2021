package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 收藏游戏成功弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogCollectedSuccess extends Dialog {


    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();
    private boolean isCollected;
    private TextView tips;

    public DialogCollectedSuccess(Context context, int themeResId, boolean isCollected,String title) {
        super(context, themeResId);
        this.mContext = context;
        this.isCollected = isCollected;
        this.getWindow().setDimAmount(0f);
        inflate = LinearLayout.inflate(context, R.layout.dialog_collected_game_success, null);
        tips = (TextView) inflate.findViewById(R.id.tv_dialog_pay_success);
        if(title==null){
            if (!isCollected) {
                tips.setText("已取消收藏");
            }
        }else{
            tips.setText(title);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        //倒计时60秒，一次1秒
        CountDownTimer timer = new CountDownTimer(1 * 1000, 1000) {
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
