package com.nian.wan.app.fragment.main_game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.fragment.PersonalCenter;
import com.nian.wan.app.fragment.main_home.HomeNewItemsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：首页游戏Fragment
 */
public class GamePage extends Fragment {

    @BindView(R.id.game_page_viewPager)
    ViewPager viewPager;

    @BindView(R.id.tui_jian)
    LinearLayout tui_jian;

    @BindView(R.id.re_men)
    LinearLayout re_men;

    @BindView(R.id.shang_jia)
    LinearLayout shang_jia;

    @BindView(R.id.fen_lei)
    LinearLayout fen_lei;

    @BindView(R.id.tv_fen_lei)
    TextView tv_fen_lei;

    @BindView(R.id.tv_shang_jia)
    TextView tv_shang_jia;

    @BindView(R.id.tv_re_men)
    TextView tv_re_men;

    @BindView(R.id.tv_tui_jian)
    TextView tv_tui_jian;

    public TuiFragment tuiFragment;
    public HotFragment hotFragment;
    public HomeNewItemsFragment homeNewItemsFragment;
    public FenleiFragment fenleiFragment;

    public int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_game, null);
        ButterKnife.bind(this, view);

        viewPager.setOffscreenPageLimit(4);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                tv_tui_jian.setTextColor(Color.parseColor("#ff888888"));
                tv_re_men.setTextSize(14);

                tv_re_men.setTextColor(Color.parseColor("#ff888888"));
                tv_shang_jia.setTextSize(14);

                tv_shang_jia.setTextColor(Color.parseColor("#ff888888"));
                tv_fen_lei.setTextSize(14);

                tv_fen_lei.setTextColor(Color.parseColor("#ff888888"));

                switch (position) {
                    case 0:
                        tv_tui_jian.setTextSize(18);
                        tv_tui_jian.setTextColor(Color.parseColor("#99cc33"));
                        break;
                    case 1:
                        tv_re_men.setTextSize(18);
                        tv_re_men.setTextColor(Color.parseColor("#99cc33"));
                        break;
                    case 2:
                        tv_shang_jia.setTextSize(18);
                        tv_shang_jia.setTextColor(Color.parseColor("#99cc33"));
                        break;
                    case 3:
                        tv_fen_lei.setTextSize(18);
                        tv_fen_lei.setTextColor(Color.parseColor("#99cc33"));
                        break;
                }
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        if (tuiFragment == null) {
                            tuiFragment = new TuiFragment();
                        }
                        return tuiFragment;
                    case 1:
                        if (hotFragment == null) {
                            hotFragment = new HotFragment();
                        }
                        return hotFragment;
                    case 2:
                        if (homeNewItemsFragment == null) {
                            homeNewItemsFragment = new HomeNewItemsFragment();
                        }
                        return homeNewItemsFragment;
                    case 3:
                        if (fenleiFragment == null) {
                            fenleiFragment = new FenleiFragment();
                        }
                        return fenleiFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        regsiterBrodcast();
        return view;
    }


    @OnClick({R.id.tui_jian, R.id.re_men, R.id.shang_jia, R.id.fen_lei})
    public void onViewClicked(View view) {

        tv_tui_jian.setTextSize(14);
        tv_tui_jian.setTextColor(Color.parseColor("#ff888888"));
        tv_re_men.setTextSize(14);
        tv_re_men.setTextColor(Color.parseColor("#ff888888"));
        tv_shang_jia.setTextSize(14);
        tv_shang_jia.setTextColor(Color.parseColor("#ff888888"));
        tv_fen_lei.setTextSize(14);
        tv_fen_lei.setTextColor(Color.parseColor("#ff888888"));

        switch (view.getId()) {
            case R.id.tui_jian:
                position = 0;
                tv_tui_jian.setTextSize(18);
                tv_tui_jian.setTextColor(Color.parseColor("#99cc33"));
                break;

            case R.id.re_men:
                position = 1;
                tv_re_men.setTextSize(18);
                tv_re_men.setTextColor(Color.parseColor("#99cc33"));
                break;

            case R.id.shang_jia:
                position = 2;
                tv_shang_jia.setTextSize(18);
                tv_shang_jia.setTextColor(Color.parseColor("#99cc33"));
                break;

            case R.id.fen_lei:
                position = 3;
                tv_fen_lei.setTextSize(18);
                tv_fen_lei.setTextColor(Color.parseColor("#99cc33"));
                break;
        }

        viewPager.setCurrentItem(position);
    }


    /**
     * 别的页面发出的跳转页面的广播监听
     */
    private Broadcast mainActivityBroadcast;

    private void regsiterBrodcast() {
        mainActivityBroadcast = new Broadcast();
        IntentFilter intentFilter = new IntentFilter("com.yinu.change.viewpage.index");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mainActivityBroadcast, intentFilter);
    }

    private class Broadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("change_status", -1)) {
                //跳转到推荐手游游戏-推荐游戏
                case PersonalCenter.ACTION_GO_RECOMMEND_SY_GAME:
                    viewPager.setCurrentItem(0);
                    break;
                //跳转到推荐手游游戏-热门游戏
                case PersonalCenter.ACTION_GO_HOT_SY_GAME:
                    viewPager.setCurrentItem(0);
                    break;
                //跳转到新上架手游游戏-热门游戏
                case PersonalCenter.ACTION_GO_NEW_SY_GAME:
                    viewPager.setCurrentItem(0);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setCurrentItem(position);
    }
}
