package com.nian.wan.app.activity;

import android.app.Activity;
import android.util.Log;
import android.view.View;

public abstract class BaseHolder<Data> {

    protected View view;
    protected Data data;


    public BaseHolder() {
        view = initView();
        view.setTag(this);
    }

    public void setData(Data data, int position, Activity activity) {
        try {
            this.data = data;
            refreshView(data, position, activity);
        } catch (Exception e) {
            Log.e("BaseHolder报错了~", e.toString());
        }
    }


    protected abstract void refreshView(Data data, int position, Activity activity);

    /**
     * 初始化View对象 及其控件
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 当Holder中的view对象显示到界面上的时候调用
     *
     * @return
     */
    public View getContentView() {
        return view;
    }

}
