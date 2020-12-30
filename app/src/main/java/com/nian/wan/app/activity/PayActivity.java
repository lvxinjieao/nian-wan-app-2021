package com.nian.wan.app.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;
import android.widget.LinearLayout;;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：
 * 作者：闫冰
 * 时间: 2018-07-27 10:24
 */
public class PayActivity extends BaseFragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
//
//    @BindView(R.id.img_ptb)
//    ImageView imgPtb;
//
//    @BindView(R.id.btn_ptb)
//    LinearLayout btnPtb;
//
//    @BindView(R.id.img_zhekou)
//    ImageView imgZhekou;


//    @BindView(R.id.btn_zhekou)
//    LinearLayout btnZhekou;

    @BindView(R.id.btn_back)
    LinearLayout btnBack;

//    @BindView(R.id.heard)
//    RelativeLayout heard;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private PayPTBFragment payPTBFragment;
//    private PayZheKouPay payZheKouPay;

    @Override
    public void initView() {
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);

        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        if (payPTBFragment == null){
                            payPTBFragment = new PayPTBFragment();
                        }
                        return payPTBFragment;
//                    case 1:
//                        if (payZheKouPay == null){
//                            payZheKouPay = new PayZheKouPay();
//                        }
//                        return payZheKouPay;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

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
//                        imgZhekou.setVisibility(View.GONE);
//                        imgPtb.setVisibility(View.VISIBLE);
//                        break;
//                    case 1:
//                        imgZhekou.setVisibility(View.VISIBLE);
//                        imgPtb.setVisibility(View.GONE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        viewPager.setCurrentItem(0);
    }

    @OnClick({/**R.id.btn_ptb, R.id.btn_zhekou, */R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.btn_ptb:
//                viewPager.setCurrentItem(0);
//                break;
//            case R.id.btn_zhekou:
//                viewPager.setCurrentItem(1);
//                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
