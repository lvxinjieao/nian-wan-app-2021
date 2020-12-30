package com.nian.wan.app.fragment.main_my;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.SignUtils;
import com.nian.wan.app.utils.Utils;
import com.mc.developmentkit.bean.GetPaidInfo;
import com.mc.developmentkit.utils.ToastUtil;
import com.switfpass.pay.activity.PayResult;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Administrator on 2016/7/14.
 */
public class ZFBmessage {
    // 商户PID
    public static String PARTNER = "";
    // 商户收款账号
    public static final String SELLER = "";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";

    private static final int SDK_PAY_FLAG = 1;
    private Activity activity;

    public ZFBmessage(Activity activity) {
        this.activity = activity;

    }

    /**
     * 支付宝支付
     */
    public void pay(GetPaidInfo info) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", info.good_name);
        map.put("code", info.code);
        map.put("price", info.pay_amount);
        map.put("body", info.body);
        map.put("token", info.user_sign);
        map.put("extend", info.good_name);
        map.put("get_amount", info.pay_amount);

        if (!TextUtils.isEmpty(info.game_id)) {
            map.put("game_id", info.game_id);
        }

        Log.e("支付参数", "title:" + info.good_name + ",code:" + info.code + ",price:" + info.pay_amount + ",game_id:" + info.game_id);
        HttpConstant.POST(handler, HttpConstant.API_PAY_ALIPAY, map, false);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    Log.e("支付返回码：", resultStatus);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                        //activity.finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            // activity.finish();
                        } else {
                            if (TextUtils.equals(resultStatus, "4001")) {
                                Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                                //activity.finish();
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                                //activity.finish();
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.e("支付宝支付JSON", (String) msg.obj);
                    try {
                        JSONObject jsonObject = new JSONObject((String) msg.obj);
                        if (jsonObject.getInt("code") == 200) {
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            String orderInfo = jsonData.getString("orderInfo");
                            String sign = jsonData.getString("order_sign");
                            String md5_sign = jsonData.getString("md5_sign");
                            String s = SignUtils.md5(orderInfo + "mengchuang");
                            Log.e("加密后的sign", s);
                            if (s.equals(md5_sign)) {
                                /**
                                 * 完整的符合支付宝参数规范的订单信息
                                 */

                                final String payInfo = orderInfo;
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        // 构造PayTask 对象
                                        PayTask alipay = new PayTask(activity);
                                        // 调用支付接口，获取支付结果
                                        String result = alipay.pay(payInfo, true);
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                // 必须异步调用
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {
                                Utils.TS("MD5加密sign不一致！");
                            }
                        } else {
                            ToastUtil.showToast(jsonObject.getString("msg"));
                        }
                    } catch (Exception e) {
                        Log.e("解析支付宝异常", e.toString());
                        ToastUtil.showToast("解析支付参数异常，请稍候再试");
                    }
                    break;
                case 2:
                    Utils.TS("支付宝错误+" + msg.obj);
                    break;
            }
        }
    };
}
