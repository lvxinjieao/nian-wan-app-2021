package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.RulesBean;
import android.widget.RelativeLayout;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：规则Holder
 * 作者：钮家齐
 * 时间: 2018-01-19 16:54
 */
public class GuiZeHolder extends BaseHolder<RulesBean> {
    @BindView(R.id.img_1)
    ImageView img1;
    @BindView(R.id.rl_home_store_score_mission_score_help)
    RelativeLayout rlHomeStoreScoreMissionScoreHelp;
    @BindView(R.id.context)
    TextView context;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private boolean item_1 = true;

    @Override
    protected void refreshView(RulesBean s, int position, Activity activity) {
        tvTitle.setText(s.getTitle());
        context.setText(s.getContent());
        if (item_1) {
            item_1 = false;
            context.setVisibility(View.GONE);
            img1.setImageResource(R.drawable.right_gray);
        } else {
            item_1 = true;
            context.setVisibility(View.VISIBLE);
            img1.setImageResource(R.drawable.ma_more_up);
        }
    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.item_guize, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }

    @OnClick(R.id.rl_home_store_score_mission_score_help)
    public void onViewClicked() {
        if (item_1) {
            item_1 = false;
            context.setVisibility(View.GONE);
            img1.setImageResource(R.drawable.right_gray);
        } else {
            item_1 = true;
            context.setVisibility(View.VISIBLE);
            img1.setImageResource(R.drawable.ma_more_up);
        }
    }
}
