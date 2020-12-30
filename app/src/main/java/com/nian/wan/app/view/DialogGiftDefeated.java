package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nian.wan.app.R;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 领取失败Dialog
 */
public class DialogGiftDefeated extends Dialog {

    private final View inflate;
    @BindView(R.id.img_get_gift_failed_close)
    RelativeLayout imgGetGiftFailedClose;

    public DialogGiftDefeated(Context context, int themeResId) {
        super(context, themeResId);
        inflate = LinearLayout.inflate(context, R.layout.dialog_gift_failed, null);
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

    @OnClick(R.id.img_get_gift_failed_close)
    public void onViewClicked() {
        dismiss();
    }
}
