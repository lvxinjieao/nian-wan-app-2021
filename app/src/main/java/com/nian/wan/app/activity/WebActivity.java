package com.nian.wan.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 齐天大圣 on 2016/10/12.
 */

public class WebActivity extends Activity {
    @BindView(R.id.img_load_h5_error)
    ImageView imgLoadH5Error;
    @BindView(R.id.wv_load_h5_game)
    WebView webView;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.h5_activity);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        layoutTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }




    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.resumeTimers();
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
