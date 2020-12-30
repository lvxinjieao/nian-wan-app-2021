package com.nian.wan.app.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.ShareDateBean;
import com.nian.wan.app.bean.ShareInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * \
 * 新闻详情
 * Created by Administrator on 2017/5/2.
 */

public class XinWenDetActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.webview)
    WebView webview;

    private String URL;
    private String topTitle;
    private String id;
    private ShareDateBean shareDateBean = new ShareDateBean();
    private int type_id;

    @Override
    public void initView() {
        setContentView(R.layout.activity_xinwendet);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);

        URL = getIntent().getStringExtra("URL");
        topTitle = getIntent().getStringExtra("topTitle");
        id = getIntent().getStringExtra("id");
        title.setText(topTitle);


        type_id = getIntent().getIntExtra("type_id",-1); //判断是首页轮播图点进来的还是新闻或活动列表点进来的
        if (type_id==-1){
            share.setVisibility(View.VISIBLE);
        }else {
            share.setVisibility(View.GONE);           //如果是首页轮播图进来的 就隐藏分享按钮
        }

        if(type_id==2){
            share.setVisibility(View.VISIBLE);
        }

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //webview相关设置
        WebSettings mSettings = webview.getSettings();
        mSettings.setSupportZoom(true);
        mSettings.setUseWideViewPort(true);
        mSettings.setDefaultTextEncodingName("GBK");
        mSettings.setLoadsImagesAutomatically(true);
        webview.loadUrl(URL+"/type/1");
        Log.e("链接", URL);

    }

    @OnClick({ R.id.back, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                getDates();
                break;
        }
    }


    /**
     * 请求分享所需数据
     */
    private void getDates(){
        Map<String,String> map = new HashMap<>();
        map.put("article_id",id);
        map.put("type","2");//文章分享
        Type type = new TypeToken<HttpResult<ShareDateBean>>() {
        }.getType();
        new HttpUtils<ShareDateBean>(type, HttpConstant.GetShare, map, "请求文章分享所需数据", false) {
            @Override
            protected void _onSuccess(ShareDateBean shareDateBean, String msg) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.logoUrl=shareDateBean.getCover();
                shareInfo.shareUrl=shareDateBean.getUrl();
                shareInfo.title=shareDateBean.getTitle();
                shareInfo.text=shareDateBean.getIntroduction();
                Intent intent = new Intent(XinWenDetActivity.this,ShareActivity.class);
                intent.putExtra("shareInfo",shareInfo);
                startActivity(intent);
            }

            @Override
            protected void _onError(String message, int code) {
                Utils.TS(message);
            }

            @Override
            protected void _onError() {
                ToastUtil.showToast("网络缓慢");
            }
        };
    }
}
