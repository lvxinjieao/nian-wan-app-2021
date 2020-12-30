package com.nian.wan.app.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.db.UserInfoDB;
import com.nian.wan.app.fragment.BalanceBindPTBFragment;
import com.nian.wan.app.fragment.BalancePTBFragment;
import com.nian.wan.app.fragment.BalanceScoreFragment;
import com.nian.wan.app.utils.Utils;
import com.nian.wan.app.view.DialogGoLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值中心
 */
public class BalanceActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    //返回上级
    @BindView(R.id.back)
    RelativeLayout back;
    //标题栏
    @BindView(R.id.title)
    TextView title;
    //余额ViewPager
    @BindView(R.id.vp_balance_content)
    ViewPager mVpBalanceContent;
    //余额TabLayout
    @BindView(R.id.tb_balance_title)
    TabLayout mTbBalanceTitle;

    @Override
    public void initView() {
        setContentView(R.layout.viewpager_balance);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("余额");
        getUserInfo();
        mTbBalanceTitle.addTab(mTbBalanceTitle.newTab().setText("积分"));
        mTbBalanceTitle.addTab(mTbBalanceTitle.newTab().setText("平台币"));
        mTbBalanceTitle.addTab(mTbBalanceTitle.newTab().setText("绑定平台币"));
        //设置tab模式，当前为系统默认模式
        mTbBalanceTitle.setTabMode(TabLayout.MODE_FIXED);
        mVpBalanceContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new BalanceScoreFragment();
                    case 1:
                        return new BalancePTBFragment();
                    case 2:
                        return new BalanceBindPTBFragment();
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
                        return "积分";
                    case 1:
                        return "平台币";
                    case 2:
                        return "绑定平台币";
                }
                return null;
            }
        });
        mTbBalanceTitle.setupWithViewPager(mVpBalanceContent);
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    private void getUserInfo() {
        UserInfoDB userInfoDB = Utils.getPersistentUserInfo();
        if (null == userInfoDB) {

            new DialogGoLogin(BalanceActivity.this,
                    R.style.MyDialogStyle, "暂时无法玩游戏哦~T_T~").show();
        }
    }


}
