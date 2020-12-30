package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import android.widget.LinearLayout;;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created  领取礼包成功弹窗
 * @Modified By:
 * @Modified Date:
 */
public class DialogGetGiftSuccess extends Dialog {


    @BindView(R.id.img_get_gift_success_close)
    RelativeLayout imgGetGiftSuccessClose;
    @BindView(R.id.vv)
    ImageView vv;
    @BindView(R.id.tv_get_gift_success)
    TextView tvGetGiftSuccess;
    @BindView(R.id.tv_get_gift_success_code_tips)
    TextView tvGetGiftSuccessCodeTips;
    @BindView(R.id.tv_get_gift_success_code)
    TextView tvGetGiftSuccessCode;
    @BindView(R.id.ll_get_gift_success_middle)
    LinearLayout llGetGiftSuccessMiddle;
    @BindView(R.id.tv_get_gift_success_summary)
    TextView tvGetGiftSuccessSummary;
    @BindView(R.id.tv_get_gift_success_copy_code)
    TextView tvGetGiftSuccessCopyCode;
    private View inflate;
    private Context mContext;
    private String giftCode;

    public DialogGetGiftSuccess(Context context, int themeResId, String giftCode) {
        super(context, themeResId);
        this.mContext = context;
        this.giftCode = giftCode;
        inflate = LinearLayout.inflate(context, R.layout.dialog_get_gift_success, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvGetGiftSuccessCode.setText(giftCode);
    }

    /**
     * 关闭登录弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.img_get_gift_success_close, R.id.tv_get_gift_success_copy_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //关闭弹窗
            case R.id.img_get_gift_success_close:
                dismissDialog();
                break;
            //复制礼包码
            case R.id.tv_get_gift_success_copy_code:
                ClipboardManager clipboardManager =
                        (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("lable", giftCode);
                clipboardManager.setPrimaryClip(clipData);
                new DialogCollectedSuccess(mContext,
                        R.style.MyDialogStyle, false,"已复制")
                        .show();
                dismissDialog();
                break;
        }
    }
}
