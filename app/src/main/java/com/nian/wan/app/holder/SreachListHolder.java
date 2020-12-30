package com.nian.wan.app.holder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.SearchGameBean;
import com.bumptech.glide.Glide;

import org.xutils.x;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SreachListHolder extends BaseHolder<SearchGameBean.MsgEntity> {
    private TextView tv_game_name;
    private ImageView img_icon;
    private Activity activity;
    private SearchGameBean.MsgEntity Date;


    @Override
    protected void refreshView(SearchGameBean.MsgEntity msgEntity, int position, Activity activity) {
        this.activity = activity;
        this.Date = msgEntity;

        Glide.with(x.app()).load(msgEntity.getCover()).error(R.drawable.default_picture).into(img_icon);
        tv_game_name.setText(msgEntity.getGame_name());
        Log.e("game_name是",msgEntity.getGame_name());
    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.search_item, null);
        tv_game_name = (TextView) inflate.findViewById(R.id.tv_game_name);
        img_icon = (ImageView) inflate.findViewById(R.id.img_icon);
        inflate.setTag(this);
        return inflate;
    }
}
