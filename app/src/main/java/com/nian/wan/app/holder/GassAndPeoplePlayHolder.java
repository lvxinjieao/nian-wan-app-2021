package com.nian.wan.app.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.activity.GameDetailH5Activity;
import com.nian.wan.app.activity.GameDetailActivity;
import com.nian.wan.app.bean.GassBean;
import com.nian.wan.app.view.ShapeImageView;
import com.bumptech.glide.Glide;

import org.xutils.x;

public class GassAndPeoplePlayHolder extends BaseHolder<GassBean> {

    private ShapeImageView icon;
    private TextView name;
    private TextView startGame;
    private Activity activity;
    private GassBean gassBean;
    private ImageView imgTag;

    @Override
    protected void refreshView(GassBean msgEntity, int position, final Activity activity) {
        this.activity = activity;
        this.gassBean = msgEntity;

        Glide.with(x.app()).load(gassBean.getIcon()).error(R.drawable.default_picture).into(icon);
        name.setText(msgEntity.getGame_name());
        if (gassBean.getSdk_version().equals("3")) {
            imgTag.setBackgroundResource(R.mipmap.tag_h5);
        } else {
            imgTag.setBackgroundResource(R.mipmap.tag_shouyou);
        }
        if (msgEntity.getSdk_version().equals("3")) {
            startGame.setText("开始");
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("game_id", Integer.valueOf(gassBean.getGame_id()));
                    intent.setClass(activity, GameDetailH5Activity.class);
                    activity.startActivity(intent);
                }
            });
        } else {
            startGame.setText("下载");
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("game_id", Integer.valueOf(gassBean.getGame_id()));
                    intent.setClass(activity, GameDetailActivity.class);
                    activity.startActivity(intent);
                }
            });
        }


    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(x.app()).inflate(R.layout.item_gass, null);
        icon = (ShapeImageView) view.findViewById(R.id.icon);
        name = (TextView) view.findViewById(R.id.name);
        startGame = (TextView) view.findViewById(R.id.tv_start_game);
        imgTag = (ImageView) view.findViewById(R.id.img_tag);
        view.setTag(this);
        return view;
    }
}
