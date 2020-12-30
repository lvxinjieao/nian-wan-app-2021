package com.nian.wan.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;


/**
 * 描述：渐变色文字
 * 作者：闫冰
 * 时间: 20180602 11:14
 */

public class GradientTextView extends android.support.v7.widget.AppCompatTextView {
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private Rect mTextBound = new Rect();

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mViewHeight = getMeasuredHeight();
        int mViewWidth = 0;
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, mViewHeight,
                new int[] {  0xFF1eced4, 0xFF3d54e0 }
                , null, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

}
