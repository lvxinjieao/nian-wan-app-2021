package com.nian.wan.app.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author: XiaYuShi
 * @Description:
 * @Date: Created in 2017/10/13 17:13
 * @Modified By:
 * @Modified Date:
 */
public class ResetPasswordViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public ResetPasswordViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResetPasswordViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
