package com.nian.wan.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.utils.ToastUtil;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：关注我们-二维码Dialog
 * 作者：闫冰
 * 时间: 2018-07-19 9:12
 */
public class DialogQRCode extends Dialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_RQ_Code)
    ImageView imgRQCode;
    @BindView(R.id.btn_jump_wechat)
    TextView btnJumpWechat;
    @BindView(R.id.btn_close)
    RelativeLayout btnClose;
    private String ingURL;
    private String title;
    private Activity activity;
    private View view;

    public DialogQRCode(@NonNull Activity context, int themeResId, String url,String title) {
        super(context, themeResId);
        this.activity = context;
        this.ingURL = url;
        this.title = title;
        view = LinearLayout.inflate(context, R.layout.dialog_qr_code, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        ButterKnife.bind(this, view);
//        btnJumpWechat.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        Glide.with(activity).load(ingURL).error(R.drawable.default_picture).into(imgRQCode);
        tvTitle.setText(title);
    }

    @OnClick({R.id.btn_jump_wechat, R.id.btn_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jump_wechat:
                getWechatApi();
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }

    /**
     * 跳转到微信
     */
    private void getWechatApi(){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            activity.startActivity(intent);
            dismiss();
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            ToastUtil.showToast("请先安装微信App");
        }
    }
}
