package com.nian.wan.app.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.DialogLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanbing on 2017/5/12.
 * 提示去登陆Dialog
 */

public class DialogGoLogin extends Dialog{

    @BindView(R.id.btnNothing)
    Button btnNothing;
    @BindView(R.id.btnGoLogin)
    Button btnGoLogin;
    @BindView(R.id.close)
    RelativeLayout close;
    @BindView(R.id.tvHint)
    TextView tvHint;

    private final View inflate;
    private String Hint;
    private Activity mContext;

    public DialogGoLogin(Activity context, int themeResId, String Hint) {
        super(context, themeResId);
        this.mContext = context;
        this.Hint = Hint;

        inflate = LinearLayout.inflate(context, R.layout.dialog_go_login, null);
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

        if (Hint!=null){
            tvHint.setText(Hint);
        }
    }

    @OnClick({R.id.btnNothing, R.id.btnGoLogin,R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNothing:
                dismiss();
                break;
            case R.id.btnGoLogin:
                DialogLoginActivity loginActivity = new DialogLoginActivity(mContext, "LGOIN");
                FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();
                transaction.add(loginActivity, "WoDe");
                transaction.show(loginActivity);
                transaction.commitAllowingStateLoss();
                dismiss();
                break;
            case R.id.close:
                dismiss();
                break;
        }
    }
}
