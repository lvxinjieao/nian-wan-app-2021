package com.nian.wan.app.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Description: 我的收藏自定义ViewPager
 */
public class CollectionViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public boolean isPagingEnabled() {
        return isPagingEnabled;
    }

    public CollectionViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollectionViewPager(Context context) {
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
