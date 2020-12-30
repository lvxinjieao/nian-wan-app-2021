package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈
 * Created by Administrator on 2017/4/26.
 */

public class FeedBackDialog extends Dialog {

    @BindView(R.id.quxiao)
    TextView quxiao;
    @BindView(R.id.queding)
    TextView queding;
    private View inflate;

    public FeedBackDialog(Context context) {
        super(context);
    }

    public FeedBackDialog(Context context, int themeResId) {
        super(context, themeResId);
        inflate = LinearLayout.inflate(context, R.layout.dialog_feedback, null);
    }

    protected FeedBackDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(inflate);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.quxiao, R.id.queding})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quxiao:
                dismiss();
                break;
            case R.id.queding:
                dismiss();
                break;
        }
    }
}
