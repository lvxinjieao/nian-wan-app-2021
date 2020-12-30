package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.PtbBean;

import org.xutils.x;

/**
 * Created by LeBron on 2016/10/8.
 */

public class Ptb_Holder extends BaseHolder<PtbBean> {

    private ImageView ptb_Icon;
    private TextView ptb_Name;
    private TextView ptb_yue;


    @Override
    protected View initView() {

//        View view= LayoutInflater.from(x.app()).inflate(R.layout.ptb_list_item,null);
//        ptb_Icon= (MyImageView) view.findViewById(R.id.ptb_icon);
//        ptb_Name= (TextView) view.findViewById(R.id.ptb_gamename);
//        ptb_yue= (TextView) view.findViewById(R.id.ptbye);
//        view.setTag(this);
        return null;
    }

    @Override
    protected void refreshView(PtbBean ptbBean, int position, Activity act) {
        Glide.with(x.app()).load(ptbBean.ptb_game_icon).error(R.drawable.default_picture).transform(new RoundedCorners(6)).into(ptb_Icon);
        ptb_Name.setText(ptbBean.ptb_game_name);
        ptb_yue.setText(ptbBean.ptb_yue+"");
    }

}
