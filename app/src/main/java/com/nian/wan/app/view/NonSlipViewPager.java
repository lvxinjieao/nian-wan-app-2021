package com.nian.wan.app.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 描述： 不可左右滑动的viewpager
 * 作者：钮家齐
 * 时间: 2017-05-23 9:50
 */
public class NonSlipViewPager  extends ViewPager {
    public NonSlipViewPager(Context context) {
        super(context);
    }

    public NonSlipViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
