package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.FenBean;
import com.nian.wan.app.view.ShapeImageView;
import com.bumptech.glide.Glide;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FenHolder extends BaseHolder<FenBean.GroupBean> {

    @BindView(R.id.icon)
    public ShapeImageView icon;

    @BindView(R.id.shuliang)
    public TextView shuliang;

    @BindView(R.id.name)
    public TextView name;

    @Override
    protected void refreshView(FenBean.GroupBean Info, int position, Activity act) {
        Glide.with(x.app()).load(Info.getIcon()).error(R.drawable.default_picture).into(icon);
        if (Info.isColor) {
            name.setTextColor(x.app().getResources().getColor(R.color.zhuse_lan));
            shuliang.setTextColor(x.app().getResources().getColor(R.color.zhuse_lan));
        } else {
            name.setTextColor(x.app().getResources().getColor(R.color.font_black_2));
            shuliang.setTextColor(x.app().getResources().getColor(R.color.font_black_2));
        }
        shuliang.setText(Info.getCounts());
        name.setText(Info.getType_name());
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(x.app()).inflate(R.layout.item_fenlei, null);
        ButterKnife.bind(this, view);
        view.setTag(this);
        return view;
    }

}
