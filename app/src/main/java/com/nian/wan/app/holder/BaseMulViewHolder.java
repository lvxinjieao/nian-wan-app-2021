package com.nian.wan.app.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nian.wan.app.bean.BaseMulDataBean;

/**
 * @Author: XiaYuShi
 * @Description:
 * @Date: Created in 2017/10/19 16:14
 * @Modified By:
 * @Modified Date:
 */
public abstract class BaseMulViewHolder<T extends BaseMulDataBean> extends RecyclerView.ViewHolder {

    public BaseMulViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(T dataModel, int postion);

}
