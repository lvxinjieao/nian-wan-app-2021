package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.BangBiBean;
import com.bumptech.glide.Glide;
import com.mc.developmentkit.views.FilletImageView;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 绑币Holder
 * Created by Administrator on 2017/4/26.
 */
public class BangbiHolder extends BaseHolder<BangBiBean.MsgBean> {
    @BindView(R.id.icon)
    FilletImageView icon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.yue)
    TextView yue;

    @Override
    protected void refreshView(BangBiBean.MsgBean s, int position, Activity activity) {
        Glide.with(x.app()).load(s.getIcon()).error(R.drawable.default_picture).into(icon);
        name.setText(s.getGame_name());
        if(s.getBind_balance()!=null&&!s.getBind_balance().equals("null")){
            yue.setText(s.getBind_balance());
        }else{
            yue.setText("0.00");
        }
    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.holder_bangbi, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }
}
