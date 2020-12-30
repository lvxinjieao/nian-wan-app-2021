package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.MyGiftActivity;
import com.nian.wan.app.activity.WebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 礼包领取成功
 * Created by Administrator on 2017/5/2.
 */

public class DialogGiftCheng extends Dialog {
    private final View inflate;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.jihuoma)
    TextView jihuoma;
    @BindView(R.id.mygift)
    TextView mygift;
    @BindView(R.id.copy)
    TextView copy;

    private String jihuocode;
    private Context mContext;
    private String url;

    public DialogGiftCheng(Context context, int myDialogStyle,String JiHuoMa,String Gameurl) {
        super(context,myDialogStyle);
        this.jihuocode = JiHuoMa;
        this.mContext = context;
        this.url = Gameurl;
        inflate = LinearLayout.inflate(context, R.layout.dialog_gift_cheng, null);
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
        jihuoma.setText(jihuocode);
    }

    @OnClick({R.id.close, R.id.copy,R.id.mygift})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.copy:
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(jihuocode);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url",url);
                mContext.startActivity(intent);
                dismiss();
                break;
            case R.id.mygift:
                mContext.startActivity(new Intent(mContext, MyGiftActivity.class));
                dismiss();
                break;
        }
    }

}
