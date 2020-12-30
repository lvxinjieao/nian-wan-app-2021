package com.nian.wan.app.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.nian.wan.app.http.HttpConstant;
import com.mc.developmentkit.bean.GetPaidInfo;
import com.mc.developmentkit.utils.ToastUtil;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class WFTmessage {


    private Activity activity;

    public WFTmessage(Activity activity) {
        this.activity = activity;
    }

    /**
     * 微信支付
     */
    public void weixinpay(GetPaidInfo info) {
        HashMap<String, String> map = new HashMap<>();
        map.put("body",info.body);
        map.put("extend",info.good_name);
        map.put("price",info.pay_amount);
        map.put("title",info.good_name);
        map.put("token",info.user_sign);
        map.put("get_amount",info.pay_amount);
        map.put("code",info.code);

        if (!TextUtils.isEmpty(info.game_id)){
            map.put("game_id",info.game_id);
        }


        Log.e("request data",map.toString());
        HttpConstant.POST(handler, HttpConstant.API_PAY_WECHAT,map,false);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("微信json",msg.obj.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        int code = jsonObject.getInt("code");

                        if(code==200){
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            String wxtype = jsonData.getString("wxtype");

                            if(wxtype.equals("wft")){
                                RequestMsg requestMsg = new RequestMsg();
                                requestMsg.setTokenId(jsonData.getString("token_id"));
                                requestMsg.setAppId(HttpConstant.AppId);
                                requestMsg.setTradeType(MainApplication.WX_APP_TYPE);
                                PayPlugin.unifiedAppPay(activity, requestMsg);
                            }else{
                                IWXAPI api = WXAPIFactory.createWXAPI(activity, null);
                                api.registerApp(jsonData.getString("appid"));

                                PayReq request = new PayReq();
                                request.appId = jsonData.getString("appid");
                                request.partnerId = jsonData.getString("partnerid");
                                request.prepayId = jsonData.getString("out_trade_no");
                                request.nonceStr = jsonData.getString("noncestr");
                                request.timeStamp = jsonData.getString("timestamp");
                                request.packageValue = jsonData.getString("package");
                                request.sign = jsonData.getString("sign");
                                api.sendReq(request);
                            }
                        }else{
                            ToastUtil.showToast(jsonObject.getString("msg"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("解析威富通和微信异常",e.toString());
                        ToastUtil.showToast("解析支付参数异常，请稍候再试");
                    }
                    break;
                case 2:
                    //ToastUtil.showToast("网络异常");
                    break;
            }

        }

    };

}
