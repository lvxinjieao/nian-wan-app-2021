package com.nian.wan.app.activity;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.fragment.PointRecordAllFragment;
import com.nian.wan.app.fragment.PointRecordExchangeFragment;
import com.nian.wan.app.fragment.PointRecordGetFragment;
import com.nian.wan.app.utils.Utils;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/24
 * @Description: 首页商城积分记录
 * @Modify By:
 * @ModifyDate:
 */
public class HomeStoreScoreRecordActivity extends BaseFragmentActivity {

    @BindView(R.id.mtou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_home_store_record_help)
    ImageView imgHomeStoreRecordHelp;
    @BindView(R.id.tb_home_store_score_mission_title)
    TabLayout tbHomeStoreScoreMissionTitle;
    @BindView(R.id.vLine)
    View vLine;
    @BindView(R.id.vp_home_viewpager)
    ViewPager vpHomeViewpager;

    @Override
    public void initView() {
        setContentView(R.layout.home_store_score_mission_record);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("积分记录");
        tbHomeStoreScoreMissionTitle.addTab(tbHomeStoreScoreMissionTitle.newTab().setText("全部"));
        tbHomeStoreScoreMissionTitle.addTab(tbHomeStoreScoreMissionTitle.newTab().setText("获取"));
        tbHomeStoreScoreMissionTitle.addTab(tbHomeStoreScoreMissionTitle.newTab().setText("兑换"));

        //设置tab模式，当前为系统默认模式
        tbHomeStoreScoreMissionTitle.setTabMode(TabLayout.MODE_FIXED);
        vpHomeViewpager.setOffscreenPageLimit(3);
        vpHomeViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new PointRecordAllFragment();
                    case 1:
                        return new PointRecordGetFragment();
                    case 2:
                        return new PointRecordExchangeFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "全部";
                    case 1:
                        return "获取";
                    case 2:
                        return "兑换";
                }
                return null;
            }
        });
        tbHomeStoreScoreMissionTitle.setupWithViewPager(vpHomeViewpager);
    }


    @OnClick({R.id.back, R.id.img_home_store_record_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.img_home_store_record_help:
                Intent storeRegularIntent = new Intent(this, HomeStoreRegularActivity.class);
                startActivity(storeRegularIntent);
                break;
        }
    }
}
