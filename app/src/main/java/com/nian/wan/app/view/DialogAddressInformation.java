package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description:
 * @Author: XiaYuShi
 * @Date: Created in 地址保存提示
 * @Modified By:
 * @Modified Date:
 */
public class DialogAddressInformation extends Dialog {


    @BindView(R.id.tv_dialog_information)
    TextView tvDialogInformation;
    @BindView(R.id.tv_dialog_confirm)
    TextView tvDialogConfirm;
    private View inflate;
    private Context mContext;
    private String information;

    public DialogAddressInformation(Context context, int themeResId, String information) {
        super(context, themeResId);
        this.mContext = context;
        this.information = information;
        inflate = LinearLayout.inflate(context, R.layout.dialog_address_right_input, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvDialogInformation.setText(information);
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.tv_dialog_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            //好的
            case R.id.tv_dialog_confirm:
                dismissDialog();
                break;
        }
    }


}
