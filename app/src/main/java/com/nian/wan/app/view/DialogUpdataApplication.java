package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 更新APP弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogUpdataApplication extends Dialog {


    @BindView(R.id.tv_dialog_pay_success)
    TextView tvDialogPaySuccess;
    @BindView(R.id.tv_update_cancel)
    TextView tvUpdateCancel;
    @BindView(R.id.tv_update_confirm)
    TextView tvUpdateConfirm;
    @BindView(R.id.cc)
    LinearLayout cc;
    private View inflate;
    private Context mContext;
    private Handler changePwdHandler = new Handler();

    public DialogUpdataApplication(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        inflate = LinearLayout.inflate(context, R.layout.dialog_update_application, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.tv_update_cancel, R.id.tv_update_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update_cancel:
                dismissDialog();
                break;
            case R.id.tv_update_confirm:
                dismissDialog();
                Intent openUrlIntent = new Intent();
                openUrlIntent.setAction("android.intent.action.VIEW");
                Log.e("版本更新链接",Utils.getPersistentAboutUsData().getAPP_DOWNLOAD());
                Uri content_url = Uri.parse(Utils.getPersistentAboutUsData() .getAPP_DOWNLOAD());
                openUrlIntent.setData(content_url);
                openUrlIntent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
                mContext.startActivity(openUrlIntent);
                break;
        }
    }
}
