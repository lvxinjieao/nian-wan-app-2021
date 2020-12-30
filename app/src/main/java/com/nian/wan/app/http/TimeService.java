package com.nian.wan.app.http;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.nian.wan.app.bean.EvenBean;


/**
 * 描述：倒计时服务
 */
public class TimeService extends Service {

    private static TimeService instance;
    private EvenBean evenBean;
    public TimeCount time1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static TimeService getInstance() {
        if (instance == null) {
            instance = new TimeService();
        }
        return instance;
    }

    public void StratTime(){
        evenBean = new EvenBean();
        time1 = new TimeCount(60000, 1000,evenBean);// 构造CountDownTimer对象
        time1.start();// 开始计时
    }

    public void StopTime(){
        time1.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        time1.cancel();
    }
}
