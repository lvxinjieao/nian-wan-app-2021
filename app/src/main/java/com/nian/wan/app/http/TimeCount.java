package com.nian.wan.app.http;

import android.os.CountDownTimer;

import com.nian.wan.app.bean.EvenBean;

import de.greenrobot.event.EventBus;

public class TimeCount extends CountDownTimer {

    private final EvenBean evenBean;
    public long time = 0;

    public TimeCount(long millisInFuture, long countDownInterval, EvenBean evenBean) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        this.evenBean = evenBean;
    }

    @Override
    public void onTick(long millisUntilFinished) { // 计时过程显示
        evenBean.a = 2;
        time = millisUntilFinished / 1000;
        evenBean.time = millisUntilFinished / 1000 + "秒";
        EventBus.getDefault().post(evenBean);
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        evenBean.a = 3;
        time = 0;
        evenBean.time = "重新获取验证码";
        EventBus.getDefault().post(evenBean);
    }
}
