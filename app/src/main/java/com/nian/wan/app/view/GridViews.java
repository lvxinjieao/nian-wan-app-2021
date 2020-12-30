package com.nian.wan.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViews extends GridView {

    public GridViews(Context context) {
        super(context);
    }

    public GridViews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        } else {
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

}
