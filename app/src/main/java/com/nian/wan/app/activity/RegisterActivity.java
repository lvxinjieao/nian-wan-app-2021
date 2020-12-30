package com.nian.wan.app.activity;

import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseFragmentActivity;
import com.nian.wan.app.adapter.RegisterAdapter;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册Activity
 * Created by Administrator on 2017/4/24.
 */

public class RegisterActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.rg_phone_register)
    RadioButton rgPhoneRegister;
    @BindView(R.id.rg_account_register)
    RadioButton rgAccountRegister;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.vp)
    ViewPager vp;
    private int screenWidth;

    @Override
    public void initView() {
        setContentView(R.layout.activity_register_is);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        initData();
    }

    private void initData() {
        RegisterAdapter registerAdapter = new RegisterAdapter(getSupportFragmentManager());
        vp.setAdapter(registerAdapter);
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_phone_register:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rg_account_register:
                        vp.setCurrentItem(1);
                        break;
                }
            }
        });

        rgPhoneRegister.setChecked(true);
        //获取屏幕的宽度
        DisplayMetrics outMetrics=new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth =outMetrics.widthPixels;

        //设置mTabLine宽度//获取控件的(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) line.getLayoutParams();//获取控件的布局参数对象
        lp.width= screenWidth /2;
        line.setLayoutParams(lp); //设置该控件的layoutParams参数

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) line.getLayoutParams();
                //获取组件距离左侧组件的距离
                lp.leftMargin=(int) ((positionOffset+position)* screenWidth /2);
                line.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rgPhoneRegister.setChecked(true);
                        break;
                    case 1:
                        rgAccountRegister.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
