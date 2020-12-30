package com.nian.wan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.nian.wan.app.R;
import com.nian.wan.app.fragment.fragment_racking.RackingFragment;
import com.nian.wan.app.utils.Utils;
import android.widget.LinearLayout;;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：排行榜
 */
public class RackingActivity extends FragmentActivity {


    @BindView(R.id.tou)
    ImageView tou;

    @BindView(R.id.btn_back)
    LinearLayout btnBack;

    @BindView(R.id.btn_search)
    ImageView btnSearch;


    @BindView(R.id.viewPager)
    ViewPager viewPager;


//    public RackingFragment rackingFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racking);
        ButterKnife.bind(this);

        Utils.initActionBarPosition(this, tou);
        viewPager.setOffscreenPageLimit(1);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Fragment getItem(int position) {
                return new RackingFragment();
            }
        });

        viewPager.setCurrentItem(0);
    }


    @OnClick({R.id.btn_back, /**R.id.btn_shouyou, R.id.btn_h5, */R.id.btn_search})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
//            case R.id.btn_shouyou:
//                viewPager.setCurrentItem(0);
//                break;
//            case R.id.btn_h5:
//                viewPager.setCurrentItem(1);
//                break;
            case R.id.btn_search:
                startActivity(new Intent(RackingActivity.this, SearchActivity.class));
                break;
        }
    }

}
