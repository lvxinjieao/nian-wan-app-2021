package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;

import org.xutils.x;

/**
 * 足迹天
 * Created by Administrator on 2017/5/4.
 */

public class TianHolder extends BaseHolder<String> {
    @Override
    protected void refreshView(String s, int position, Activity activity) {

    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.holder_tian, null);
        inflate.setTag(this);
        return inflate;
    }
}
