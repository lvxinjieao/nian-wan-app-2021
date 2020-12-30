package com.nian.wan.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.AddressActivity;
import com.nian.wan.app.activity.DialogLoginActivity;
import com.nian.wan.app.activity.HomeStoreMissionActivity;
import android.widget.LinearLayout;;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/14.
 */

public class DialogStorePublic extends Dialog {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_dialog_msg)
    TextView tvDialogMsg;
    @BindView(R.id.tv_dialog_canel)
    TextView tvDialogCanel;
    @BindView(R.id.tv_dialog_ok)
    TextView tvDialogOk;
    @BindView(R.id.cc)
    LinearLayout cc;

    private View inflate;
    private Activity mActivity;
    private String Title;
    private String Message;
    private int type;
    private final int TYPE_POINT_NOT_ENOUGH = 0;  //积分不足
    private final int TYPE_NOT_LOGIN = 1;   //没登录
    private final int TYPE_NO_ADDRESS = 2; //没有收获地址
    private final int TYPE_SUCCESS = 3; //兑换成功
    private final int TYPE_FAIL = 4;    //提交失败
    private final int TYPE_NOT_ENOUGH = 5;    //库存不足

    public DialogStorePublic(@NonNull Activity activity, int themeResId, String title, String message, int type) {
        super(activity, themeResId);
        this.mActivity = activity;
        this.type = type;
        this.Title = title;
        this.Message = message;
        inflate = LinearLayout.inflate(mActivity, R.layout.dialog_store_public, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
        tvDialogTitle.setText(Title);
        tvDialogMsg.setText(Message);
        switch (type) {
            case TYPE_POINT_NOT_ENOUGH: //积分不足
                tvDialogOk.setText("获取积分");
                break;
            case TYPE_NOT_LOGIN:  //没登录
                tvDialogOk.setText("去登录");
                break;
            case TYPE_NO_ADDRESS:  //没有收获地址
                tvDialogOk.setText("去添加");
                break;
            case TYPE_SUCCESS:  //兑换成功
                tvDialogOk.setText("继续兑换");
                break;
            case TYPE_FAIL:  //提交失败
                tvDialogOk.setText("重试");
                break;
            case TYPE_NOT_ENOUGH: //库存不足
                tvDialogCanel.setVisibility(View.GONE);
                tvDialogOk.setText("好的");
                break;
        }
    }

    /**
     * 关闭弹出窗
     */
    private void dismissDialog() {
        if (isShowing()){
            dismiss();
        }
//        if (isShowing()) {
//            Intent intent = new Intent("com.yinu.login");
//            intent.putExtra("login_status", HomeStoreGoodsDetailActivity.RESUME_COST);
//            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intent);
//            dismiss();
//        }
    }

    @OnClick({R.id.tv_dialog_canel, R.id.tv_dialog_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_canel:
                dismissDialog();
                break;
            case R.id.tv_dialog_ok:
                switch (type) {
                    case TYPE_POINT_NOT_ENOUGH: //积分不足
                        Intent intent = new Intent(mActivity, HomeStoreMissionActivity.class);
                        intent.putExtra("key", 2);
                        mActivity.startActivity(intent);
                        dismissDialog();
                        break;
                    case TYPE_NOT_LOGIN:  //没登录
                        dismissDialog();
                        DialogLoginActivity loginActivity = new DialogLoginActivity(getContext(), "LGOIN");
                        FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
                        transaction.add(loginActivity, "WoDe");
                        transaction.show(loginActivity);
                        transaction.commitAllowingStateLoss();
                        break;
                    case TYPE_NO_ADDRESS:  //没有收获地址
                        mActivity.startActivity(new Intent(mActivity, AddressActivity.class));
                        dismissDialog();
                        break;
                    case TYPE_SUCCESS:  //兑换成功
                        dismissDialog();
                        mActivity.finish();
                        break;
                    case TYPE_FAIL:  //提交失败
                        dismissDialog();
                        break;
                    case TYPE_NOT_ENOUGH:  //库存不足
                        dismissDialog();
                        break;
                }
                break;
        }
    }
}
