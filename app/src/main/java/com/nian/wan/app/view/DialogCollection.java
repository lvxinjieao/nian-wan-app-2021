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
import com.nian.wan.app.activity.MyGameActivity;
import com.nian.wan.app.activity.WebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收藏
 * Created by Administrator on 2017/5/3.
 */

public class DialogCollection extends Dialog {


    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.go_shoucang)
    TextView goShoucang;
    @BindView(R.id.kaishi_play)
    TextView kaishiPlay;

    private View inflate;
    private String gameURL;
    private Context mContext;

//    public DialogCollection(Context context) {
//        super(context);
//    }

    public DialogCollection(Context context, int themeResId,String gameURL) {
        super(context, themeResId);
        this.mContext = context;
        this.gameURL = gameURL;
        inflate = LinearLayout.inflate(context, R.layout.dialog_collection, null);
    }

//    protected DialogCollection(Context context, boolean cancelable, OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.close, R.id.go_shoucang, R.id.kaishi_play})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.go_shoucang:
                mContext.startActivity(new Intent(mContext, MyGameActivity.class));
                dismiss();
                break;
            case R.id.kaishi_play:
                Intent intent =new Intent(mContext, WebActivity.class);
                intent.putExtra("url",gameURL);
                mContext.startActivity(intent);
                dismiss();
                break;
        }
    }
}
