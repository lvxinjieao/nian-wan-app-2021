package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created  领取礼包失败弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogGetGiftFailed extends Dialog {
    @BindView(R.id.img_get_gift_failed_close)
    RelativeLayout imgGetGiftFailedClose;
    @BindView(R.id.tv_get_gift_failed_retry)
    TextView tvGetGiftFailedRetry;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private View inflate;
    private Context mContext;
    private Handler transferWithDetailActivity;
    private String Msg;

    public DialogGetGiftFailed(Context context, int themeResId, Handler handler,String hintMsg) {
        super(context, themeResId);
        this.mContext = context;
        this.transferWithDetailActivity = handler;
        this.Msg = hintMsg;
        inflate = LinearLayout.inflate(context, R.layout.dialog_get_gift_failed, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvHint.setText(Msg);
    }

    /**
     * 关闭登录弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.img_get_gift_failed_close, R.id.tv_get_gift_failed_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //关闭弹窗
            case R.id.img_get_gift_failed_close:
                dismissDialog();
                break;
            //重试
            case R.id.tv_get_gift_failed_retry:
                Message message = Message.obtain();
                message.what = 1;
                transferWithDetailActivity.sendMessage(message);
                dismissDialog();
                break;
        }
    }
}
