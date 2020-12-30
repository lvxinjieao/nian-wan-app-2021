package com.nian.wan.app.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";
	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, HttpConstant.AppId);
		api.handleIntent(getIntent(), this);
		api.registerApp(HttpConstant.AppId);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onResp(BaseResp baseResp) {
		Log.e("微信支付失败码",String.valueOf(baseResp.errCode));
		switch (baseResp.errCode) {
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			Utils.TS("支付失败 认证被否决");
			break;
		case BaseResp.ErrCode.ERR_COMM:
			Utils.TS("支付失败：微信支付错误");
			break;
		case BaseResp.ErrCode.ERR_OK:
			Utils.TS("微信支付成功");
			break;
		case BaseResp.ErrCode.ERR_SENT_FAILED:
			Utils.TS("支付失败：发送失败");	
			break;
		case BaseResp.ErrCode.ERR_UNSUPPORT:
			Utils.TS("支付失败：不支持错误");	
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			Utils.TS("支付失败：用户取消");	
			break;
		default:
			Utils.TS("支付失败：其他");
			break;
		}
		WXPayEntryActivity.this.finish();
	}
	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
	}
}