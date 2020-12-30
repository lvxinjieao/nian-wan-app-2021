package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.AppInfo;
import com.nian.wan.app.view.ShapeImageView;
import com.bumptech.glide.Glide;

import org.xutils.x;

/**
 * Created by 齐天大圣 on 2016/10/10.
 */

public class GassHolder extends BaseHolder<AppInfo> {

    private ShapeImageView icon;
    private TextView name;

    @Override
    protected void refreshView(AppInfo appInfo, int position, Activity act) {
        Glide.with(x.app()).load(appInfo.icon_url).error(R.drawable.default_picture).into(icon);
                name.setText(appInfo.name);
    }

    @Override
    protected View initView() {
        View view= LayoutInflater.from(x.app()).inflate(R.layout.item_gass,null);
        icon = (ShapeImageView)view.findViewById(R.id.icon);
        name = (TextView)view.findViewById(R.id.name);
        view.setTag(this);
        return view;
    }
}
