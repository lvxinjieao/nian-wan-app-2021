package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BindPhoneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Description: 登录成功后pop弹窗提示绑定
 * @Author: XiaYuShi
 * @Date: Created in
 * @Modified By:
 * @Modified Date:
 */
public class DialogLoginPopTipBind extends Dialog {


    @BindView(R.id.img_pop_bind_close)
    ImageView imgPopBindClose;
    @BindView(R.id.tv_pop_bind_score)
    TextView tvPopBindScore;
    @BindView(R.id.tv_go_bind)
    TextView tvGoBind;
    private View inflate;
    private Context mContext;
    private String point;

    public DialogLoginPopTipBind(Context context, int themeResId, String point) {
        super(context, themeResId);
        this.mContext = context;
        this.point = point;
        inflate = LinearLayout.inflate(context, R.layout.dialog_login_tip_bind, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvPopBindScore.setText("绑定送" + point + "积分");

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }


    @OnClick({R.id.img_pop_bind_close, R.id.tv_go_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pop_bind_close:
                dismissDialog();
                break;
            case R.id.tv_go_bind:
                Intent intent = new Intent(mContext, BindPhoneActivity.class);
                mContext.startActivity(intent);
                dismissDialog();
                break;
        }
    }
}
