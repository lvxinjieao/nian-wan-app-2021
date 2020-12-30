package com.nian.wan.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.bean.ThirdLoginBean;
import com.nian.wan.app.bean.UserLoginBean;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;

import org.xutils.x;

import java.lang.reflect.Type;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class LoginCallBack implements PlatformActionListener {

    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    public static String loginType;
    private final Activity activity;
    private UserLoginBean userLoginBean;

    public LoginCallBack(Activity a) {
        activity = a;
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        LogUtils.w("登录成功回调", platform.getDb().exportData());
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            loginType = platform.getDb().getPlatformNname();
            msg.what = MSG_AUTH_COMPLETE;
            msg.arg2 = action;
            msg.obj = platform.getDb().exportData();
            sHandler.sendMessage(msg);
        }

    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        LogUtils.e("登录失败回调", platform.getDb().exportData());
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_ERROR;
            msg.arg2 = action;
            msg.obj = platform.getDb().exportData();
            sHandler.sendMessage(msg);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        LogUtils.e("登录取消回调", platform.getDb().exportData());
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_CANCEL;
            msg.arg2 = action;
            msg.obj = platform.getDb().exportData();
            sHandler.sendMessage(msg);
        }
    }

    @SuppressLint("HandlerLeak")
    android.os.Handler sHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    Log.e("第三方登录成功", "成功---------");
                    ThirdLoginBean thirdLoginBean = HttpUtils.thirdPartyLoginGetInfo(loginType, (String) msg.obj);
                    if (thirdLoginBean != null) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("nickname", thirdLoginBean.nickname);
                        map.put("unionid", thirdLoginBean.id);
                        map.put("access_token", thirdLoginBean.token);
                        map.put("head_icon", thirdLoginBean.icon);
                        map.put("third_login_type", loginType.equals("QQ") ? "4" : "2");
                        map.put(" promote_id", new PromoteUtils().getPromoteId());
                        Type type = new TypeToken<HttpResult<UserLoginBean>>() {}.getType();

                        new HttpUtils<UserLoginBean>(type, HttpConstant.API_USER_THIRD_LOGIN, map, "第三方登录", false) {
                            @Override
                            protected void _onSuccess(UserLoginBean userLoginBean, String msg) {
                                //将登录的用户信息保存到数据库中
                                Utils.persistentUserInfo(userLoginBean);
                                Intent normalSuccessIntent = new Intent("com.yinu.login");
                                normalSuccessIntent.putExtra("login_status", DialogLoginActivity.EVENT_THIRD_LOGIN_SUCCESS);
                                LocalBroadcastManager.getInstance(x.app()).sendBroadcast(normalSuccessIntent);

                                Intent thirdSuccessIntent = new Intent("com.yinu.login");
                                thirdSuccessIntent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_SUCCESS);
                                LocalBroadcastManager.getInstance(x.app()).sendBroadcast(thirdSuccessIntent);
                            }

                            @Override
                            protected void _onError(String message, int code) {
                                Utils.TS(message);
                                Intent normalFailedIntent = new Intent("com.yinu.login");
                                normalFailedIntent.putExtra("login_status", DialogLoginActivity.EVENT_THIRD_LOGIN_FAILED);
                                LocalBroadcastManager.getInstance(x.app()).sendBroadcast(normalFailedIntent);

                                Intent thirdFailedIntent = new Intent("com.yinu.login");
                                thirdFailedIntent.putExtra("login_status", DialogLoginActivity.EVENT_LOGIN_FILED);
                                LocalBroadcastManager.getInstance(x.app()).sendBroadcast(thirdFailedIntent);
                            }

                            @Override
                            protected void _onError() {
                                ToastUtil.showToast("网络缓慢");
                            }
                        };

                    } else {
                        Log.e("第三方用户信息为空", "--------");
                    }
                    break;
                case 2:
                    Log.e("第三方登录失败", "失败");
                    ToastUtil.showToast("第三方登录失败");
                    break;
                case 1:
                    Log.e("第三方登录取消", "取消");
                    ToastUtil.showToast("第三方登录取消");
                    break;
            }
        }
    };

}