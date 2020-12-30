package com.nian.wan.app.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.activity.HomeGiftDetailActivity;
import com.nian.wan.app.adapter.HomeGiftChildAdapter;
import com.nian.wan.app.bean.HomeGiftBean;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.ShapeImageView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeGiftGroupHolder extends BaseHolder<HomeGiftBean> {

    @BindView(R.id.child_list)
    ListView childListView;

    @BindView(R.id.tv_more_num)
    TextView tvMoreNum;

    @BindView(R.id.btn_layout_more)
    LinearLayout btnLayoutMore;

    @BindView(R.id.vw_line_big)
    View lineBig;

    @BindView(R.id.img_home_gift_pic)
    ShapeImageView icon;

    @BindView(R.id.tv_home_gift_game_name)
    TextView gameName;

    @BindView(R.id.tv_home_gift_gift_number)
    TextView giftNum;

    private Activity activity;
    private HomeGiftChildAdapter childAdapter;
    private List<HomeGiftBean.GblistBean> listBean;

    @Override
    protected void refreshView(final HomeGiftBean homeGiftBean, final int position, final Activity activity) {
        this.activity = activity;
        gameName.setText(homeGiftBean.getGame_name());
        giftNum.setText(homeGiftBean.getGbnum());
        Glide.with(activity).load(homeGiftBean.getIcon()).error(R.drawable.default_picture).into(icon);
        childListView.setFocusable(false);

        if (childAdapter == null) {
            childAdapter = new HomeGiftChildAdapter(activity);
            childListView.setAdapter(childAdapter);
        }
        if (listBean == null) {
            listBean = new ArrayList<>();
        }
        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, HomeGiftDetailActivity.class);
                intent.putExtra("gift_id", homeGiftBean.getGblist().get(i).getGift_id());
                activity.startActivity(intent);
            }
        });

        if (homeGiftBean.getGblist().size() > 0) {
            listBean.clear();
            listBean.add(homeGiftBean.getGblist().get(0));
            childAdapter.setData(listBean);

            if (homeGiftBean.getGblist().size() > 1) {
                if (homeGiftBean.isShowAll) {  //用于防止已经点击查看更多按钮后，上滑出屏幕后再下滑回来时，“查看更多”按钮会再次显示
                    btnLayoutMore.setVisibility(View.GONE);
                    listBean.clear();
                    listBean.addAll(homeGiftBean.getGblist());
                    childAdapter.setData(listBean);
                    Utils.setListView(childListView);
                    btnLayoutMore.setVisibility(View.GONE);
                } else {
                    btnLayoutMore.setVisibility(View.VISIBLE);
                    homeGiftBean.isShowAll = false;
                    Utils.setListView(childListView);
                    tvMoreNum.setText("查看更多礼包（" + (homeGiftBean.getGblist().size() - 1) + "）");
                    btnLayoutMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            homeGiftBean.isShowAll = true;
                            listBean.clear();
                            listBean.addAll(homeGiftBean.getGblist());
                            childAdapter.setData(listBean);
                            Utils.setListView(childListView);
                            btnLayoutMore.setVisibility(View.GONE);
                        }
                    });
                }
            } else {
                homeGiftBean.isShowAll = false;
                Utils.setListView(childListView);
                btnLayoutMore.setVisibility(View.GONE);
            }
        }


    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.home_gift_group_item, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }
}
