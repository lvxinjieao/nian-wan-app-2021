package com.nian.wan.app.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 齐天大圣 on 2016/10/8.
 */

public class VP extends ViewPager {

    public VP(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VP(Context context) {
        super(context);
    }

    // 表示事件是否拦截, 返回false表示不拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    /**
     * 重写onTouchEvent事件,什么都不用做
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }
}
