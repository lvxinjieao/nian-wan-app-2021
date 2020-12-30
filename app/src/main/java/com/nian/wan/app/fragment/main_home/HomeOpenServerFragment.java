package com.nian.wan.app.fragment.main_home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.fragment.AlreadyOpenServerFragment;
import com.nian.wan.app.fragment.ForeShowServerFragment;
import com.nian.wan.app.view.CollectionViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 新开服
 */
public class HomeOpenServerFragment extends  Fragment {
    //已开服布局
    @BindView(R.id.rl_already_open_server)
    RelativeLayout mRlAlreadyOpenServer;
    //已开服图片
    @BindView(R.id.img_already_open_server)
    ImageView mImgAlreadyOpenServer;
    //已开服标题
    @BindView(R.id.tv_already_open_server)
    TextView mTvAlreadyOpenServer;
    //新服预告布局
    @BindView(R.id.rl_server_foreshow)
    RelativeLayout mRlServerForeshow;
    //新服预告图片
    @BindView(R.id.img_server_foreshow)
    ImageView mImgServerForeshow;
    //新服预告标题
    @BindView(R.id.tv_server_foreshow)
    TextView mTvServerForeshow;
    //新开服ViewPager
    @BindView(R.id.vp_open_server_content)
    CollectionViewPager mVpOpenServerContent;

    private AlreadyOpenServerFragment alreadyOpenServerFragment;
    private ForeShowServerFragment foreShowServerFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_open_server, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mVpOpenServerContent.setPagingEnabled(true);
        mVpOpenServerContent.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        if (alreadyOpenServerFragment == null){
                            alreadyOpenServerFragment = new AlreadyOpenServerFragment();
                        }
                        return alreadyOpenServerFragment;
                    case 1:
                        if (foreShowServerFragment == null){
                            foreShowServerFragment = new ForeShowServerFragment();
                        }
                        return foreShowServerFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        mVpOpenServerContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setupOne();
                        break;
                    case 1:
                        setupTwo();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.rl_already_open_server, R.id.rl_server_foreshow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_already_open_server:
                setupOne();
                break;
            case R.id.rl_server_foreshow:
                setupTwo();
                break;
        }
    }


    public void setupOne() {
        mVpOpenServerContent.setCurrentItem(0);
        mRlAlreadyOpenServer.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.game_activities_shape_item_one_check));
        mImgAlreadyOpenServer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.service_opened_unselect));
        mTvAlreadyOpenServer.setTextColor(getResources().getColor(R.color.font_white));
        mRlServerForeshow.setBackground(null);
        mImgServerForeshow.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.service_trailer_select));
        mTvServerForeshow.setTextColor(getResources().getColor(R.color.zhuse_lan));

    }

    public void setupTwo() {
        mVpOpenServerContent.setCurrentItem(1);
        mRlAlreadyOpenServer.setBackground(null);
        mImgAlreadyOpenServer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.service_opened_select));
        mTvAlreadyOpenServer.setTextColor(getResources().getColor(R.color.zhuse_lan));
        mRlServerForeshow.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.game_activities_shape_item_two_check));
        mImgServerForeshow.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.service_trailer_unselect));
        mTvServerForeshow.setTextColor(getResources().getColor(R.color.font_white));
    }


}
