package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.nian.wan.app.R;


/**
 * Created by 齐天大圣 on 2016/9/7.
 */
public class LoadDialog extends Dialog {

    private ImageView jiazai;
    private AnimationDrawable background;
    public static LoadDialog mInstace = null;

    public LoadDialog(Context context) {
        super(context);
    }

    public LoadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);
        mInstace = this;
        jiazai = (ImageView)findViewById(R.id.jiazai);
        jiazai.setBackgroundResource(R.drawable.loading);
        background = (AnimationDrawable) jiazai.getBackground();

        if(background.isRunning())//是否正在运行？

        {
            background.stop();//停止
            return;

        }
        background.start();//启动

    }

}
