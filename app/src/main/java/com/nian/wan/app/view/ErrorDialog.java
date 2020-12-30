package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.nian.wan.app.R;


/**
 * Created by 齐天大圣 on 2016/9/7.
 */
public class ErrorDialog extends Dialog {
    public static ErrorDialog mInstace = null;
    private ImageView error;
    private String string;
    private WebView webView;

    public ErrorDialog(Context context) {
        super(context);
    }

    public ErrorDialog(Context context, int themeResId , String url, WebView view) {
        super(context, themeResId);
        string = url;
        webView = view;
    }

    protected ErrorDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_error);
        mInstace = this;
        error = (ImageView)findViewById(R.id.jiazai);
        error.setBackgroundResource(R.mipmap.error);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ErrorDialog.this.dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        webView.loadUrl(string);
    }
}
