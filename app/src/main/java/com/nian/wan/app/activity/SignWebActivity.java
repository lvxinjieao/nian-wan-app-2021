package com.nian.wan.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.ShareInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.Utils;
import com.mc.developmentkit.utils.ToastUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONObject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignWebActivity extends BaseFragmentActivity {

    private String TAG = "SignWebActivity";

    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.webview)
    WebView x5WebView;

    @BindView(R.id.share)
    ImageView share;

    @BindView(R.id.back)
    RelativeLayout back;

    @BindView(R.id.title)
    TextView title;


    private String url;
    private String name;
    private boolean isShare = false;
    private boolean canChack = true;

    @Override
    public void initView() {
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
        isShare = getIntent().getBooleanExtra("share", false);
        title.setText(name);

        Log.e(TAG, "url:" + url);
        if (isShare) {
            share.setVisibility(View.VISIBLE);
        }
        initdata();
    }

    private void initdata() {
        x5WebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// 设置允许JS弹窗
        x5WebView.getSettings().setUseWideViewPort(true); //自适应屏幕
        x5WebView.getSettings().setAppCacheEnabled(true);//设置缓存
        x5WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //不使用缓存，只从网络获取数据
        x5WebView.setHorizontalScrollBarEnabled(false);
        x5WebView.setVerticalScrollBarEnabled(false);
        x5WebView.setScrollbarFadingEnabled(true);
        x5WebView.getSettings().setJavaScriptEnabled(true);// 支持js交互
        WebSettings webSettings = x5WebView.getSettings();
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAppCachePath(x.app().getCacheDir().toString());
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationDatabasePath(x.app().getFilesDir().toString());
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("UTF-8");
        x5WebView.addJavascriptInterface(new JsInterface(), "mengchuang");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }

        x5WebView.loadUrl(url);
        x5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


    /**
     * JS交互
     */
    class JsInterface {
        @JavascriptInterface
        public void goChange() {  //H5签到页面，签到成功后回退页面
            Intent intent = new Intent(SignWebActivity.this, HomeStoreMissionActivity.class);
            intent.putExtra("result", 1);
            SignWebActivity.this.setResult(1, intent);
            SignWebActivity.this.finish();
        }
    }


    @OnClick({R.id.back, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                canChack = false;
                getDates();
                break;
        }
    }


    /**
     * 请求分享所需数据
     */
    private void getDates() {
        Map<String, String> map = new HashMap<>();
        HttpConstant.POST(mHandler, HttpConstant.API_SHOP_SHARE, map, false);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("分享返回参数", msg.obj.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getInt("code") == 200) {
                            canChack = true;
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            ShareInfo shareInfo = new ShareInfo();
                            shareInfo.logoUrl = jsonData.getString("cover");
                            shareInfo.shareUrl = jsonData.getString("url");
                            shareInfo.title = jsonData.getString("title");
                            shareInfo.text = jsonData.getString("introduction");
                            if (canChack) {
                                Intent intent = new Intent(SignWebActivity.this,
                                        ShareActivity.class);
                                intent.putExtra("shareInfo", shareInfo);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("分享异常", e.toString());
                    }
                    break;
                case 2:
                    ToastUtil.showToast("网络缓慢");
                    break;
            }
        }
    };


}
