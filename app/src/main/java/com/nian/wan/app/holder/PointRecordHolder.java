package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.PointRecordBean;
import com.nian.wan.app.utils.TimeUtils;
import com.bumptech.glide.Glide;
import android.widget.LinearLayout;;
import android.widget.RelativeLayout;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/15.
 */

public class PointRecordHolder extends BaseHolder<PointRecordBean> {
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_home_store_score_content)
    RelativeLayout rlHomeStoreScoreContent;
    @BindView(R.id.tv_home_store_cost_description)
    TextView tvHomeStoreCostDescription;
    @BindView(R.id.ll_home_store_cost_description)
    LinearLayout llHomeStoreCostDescription;


    private Activity activity;
    private PointRecordBean bean;
    private int type;

    public PointRecordHolder(int type) {
        this.type = type;
    }

    @Override
    protected void refreshView(PointRecordBean bean, int position, Activity activity) {
        this.activity = activity;
        this.bean = bean;
        Glide.with(x.app()).load(bean.cover).error(R.drawable.default_picture).into(imgIcon);
        tvTitle.setText(bean.name);
        tvTime.setText(TimeUtils.timedate(bean.create_time));
        if (bean.type == 2) {
            tvPoint.setText("+" + bean.point);
            tvPoint.setTextColor(activity.getResources().getColor(R.color.zhuse_lan));
        } else if (bean.type == 3) {
            tvPoint.setText("-" + bean.point);
            tvPoint.setTextColor(activity.getResources().getColor(R.color.theme_red));
        }

        int goodType = Integer.valueOf(bean.goodType);
        switch (goodType){
            case 6:   //兑换平台币消费
                Glide.with(x.app()).load(R.mipmap.mall_record_pingtaibi).into(imgIcon);
                break;
            case 7:   //签到奖励
                Glide.with(x.app()).load(R.mipmap.mall_record_sign).into(imgIcon);
                break;
            case 11:   //邀请好友注册奖励
                Glide.with(x.app()).load(R.mipmap.icon_invitation).into(imgIcon);
                break;
        }

        if ("2".equals(bean.goodType) || "1".equals(bean.goodType)) {
            tvHomeStoreCostDescription.setText(bean.goodmark);
        }
    }

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(x.app()).inflate(R.layout.holder_point_record, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }

}
