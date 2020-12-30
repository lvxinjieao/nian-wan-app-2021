package com.nian.wan.app.fragment.fragment_racking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nian.wan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：排行榜-手游Fragment
 */
public class RackingFragment extends Fragment {

    @BindView(R.id.btn_tuijian)
    TextView btnTuijian;

    @BindView(R.id.btn_remen)
    TextView btnRemen;

    @BindView(R.id.btn_zuixin)
    TextView btnZuixin;

    @BindView(R.id.btn_xiazai)
    TextView btnXiazai;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_racking_shouyou, null);
        ButterKnife.bind(this, view);

        //预加载4页防止用户快速滑动导致的卡顿
        viewPager.setOffscreenPageLimit(4);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setStly(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return new RackingChildFragment(position + 1);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        return view;
    }


    @OnClick({R.id.btn_tuijian, R.id.btn_remen, R.id.btn_zuixin, R.id.btn_xiazai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tuijian:
                setStly(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn_remen:
                setStly(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.btn_zuixin:
                setStly(2);
                viewPager.setCurrentItem(2);
                break;
            case R.id.btn_xiazai:
                setStly(3);
                viewPager.setCurrentItem(3);
                break;
        }
    }


    /**
     * 顶部按钮切换样式
     */
    private void setStly(int what) {
        btnTuijian.setTextSize(14);
        btnRemen.setTextSize(14);
        btnZuixin.setTextSize(14);
        btnXiazai.setTextSize(14);

        switch (what) {
            case 0:
                btnTuijian.setTextSize(16);
                btnTuijian.getPaint().setFakeBoldText(true);
                break;

            case 1:
                btnRemen.setTextSize(16);
                btnRemen.getPaint().setFakeBoldText(true);
                break;

            case 2:
                btnZuixin.setTextSize(16);
                btnZuixin.getPaint().setFakeBoldText(true);
                break;

            case 3:
                btnXiazai.setTextSize(16);
                btnXiazai.getPaint().setFakeBoldText(true);
                break;
        }
    }


}
