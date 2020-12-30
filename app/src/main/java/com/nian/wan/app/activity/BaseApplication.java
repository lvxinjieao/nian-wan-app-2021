package com.nian.wan.app.activity;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.arialyy.aria.core.Aria;
import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.Utils;
import com.nian.wan.app.http.TimeService;
import com.orhanobut.hawk.Hawk;
import com.tencent.smtt.sdk.QbSdk;

import org.xutils.x;

public class BaseApplication extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        context = this;
        Utils.init(this);
        Hawk.init(this).build();
        x.Ext.init(this);
        Aria.get(this).getDownloadConfig().setConvertSpeed(true);

        if (!ServiceUtils.isServiceRunning(this, "timer")) {
            ServiceUtils.startService(this, TimeService.class);
        }
        /**
         * 腾讯X5浏览器初始化
         */
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功
                //否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("->>", "是否初始化完成" + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        QbSdk.initX5Environment(getApplicationContext(), cb); //x5内核初始化接口

    }

    public static Context getApplication() {
        return context;
    }


}