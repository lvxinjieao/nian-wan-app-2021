package com.nian.wan.app.fragment.main_gift;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：
 * 作者：闫冰
 * 时间: 2018-07-26 19:17
 */
public class Gift extends Fragment {


//    @BindView(R.id.img_shouyou)
//    ImageView imgShouyou;

//    @BindView(R.id.btn_shouyou)
//    LinearLayout btnShouyou;

//    @BindView(R.id.img_h5)
//    ImageView imgH5;

//    @BindView(R.id.btn_h5)
//    LinearLayout btnH5;

    @BindView(R.id.btn_search)
    ImageView btnSearch;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private GiftShouyouFragment giftShouyouFragment;
    //private GiftH5Fragment giftH5Fragment;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, null);
        ButterKnife.bind(this, view);

        //预加载防止用户快速滑动导致的卡顿
        viewPager.setOffscreenPageLimit(1);



//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        imgH5.setVisibility(View.GONE);
//                        imgShouyou.setVisibility(View.VISIBLE);
//                        break;
//                    case 1:
//                        imgH5.setVisibility(View.VISIBLE);
//                        imgShouyou.setVisibility(View.GONE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        if (giftShouyouFragment == null){
                            giftShouyouFragment = new GiftShouyouFragment();
                        }
                        return giftShouyouFragment;

//                    case 1:
//                        if (giftH5Fragment == null){
//                            giftH5Fragment = new GiftH5Fragment();
//                        }
//                        return giftH5Fragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
        return view;
    }


    @OnClick({/**R.id.btn_shouyou, R.id.btn_h5,*/ R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.btn_shouyou:
//                viewPager.setCurrentItem(0);
//                break;
//            case R.id.btn_h5:
//                viewPager.setCurrentItem(1);
//                break;
            case R.id.btn_search:
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }
}
