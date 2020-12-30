package com.nian.wan.app.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nian.wan.app.R;

/**
 * 描述：
 * 作者：钮家齐
 * 时间: 2018-01-24 11:22
 */
public class ViewPagerBehavior extends CoordinatorLayout.Behavior<View> {
    public ViewPagerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        int measuredHeight = parent.findViewById(R.id.appbar_layout).getMeasuredHeight();
//        int viewHeight = parent.findViewById(R.id.view_height).getMeasuredHeight();
        //因为UCViewTitle默认是在屏幕外不可见，所以在UCViewTitle进行布局的时候设置其topMargin让其不可见
        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin = measuredHeight;
//        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).height = parent.getHeight()-viewHeight;
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override

    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //告知监听的dependency是Button
        return dependency!=null&&dependency.getId() == R.id.appbar_layout;
    }

    @Override
    //当 dependency(Button)变化的时候，可以对child(TextView)进行操作
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.e("onDependentViewChanged",""+dependency.getTop());
        child.setTranslationY((int) (dependency.getTop()));
        return true;
    }
}
