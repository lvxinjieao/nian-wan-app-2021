package com.nian.wan.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重置密码Dialog
 * Created by Administrator on 2017/4/25.
 */

public class PassDialog extends Dialog {

    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.go_login)
    TextView goLogin;
    private View inflate;
    private Activity activity;

    public PassDialog(Activity context, int themeResId) {
        super(context, themeResId);
        activity = context;
        inflate = LinearLayout.inflate(context, R.layout.dialog_chongzhipass, null);

    }

    public PassDialog(Context context) {
        super(context);
    }

    protected PassDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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

    @OnClick({R.id.close, R.id.go_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.go_login:
                dismiss();
                activity.finish();
                break;
        }
    }
}
