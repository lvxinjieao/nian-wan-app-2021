package com.nian.wan.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nian.wan.app.fragment.main_my.RegisterAccount;
import com.nian.wan.app.fragment.main_my.RegisterPhone;

/**
 * 注册Adapter
 * Created by Administrator on 2017/4/24.
 */

public class RegisterAdapter extends FragmentPagerAdapter {

    private RegisterPhone registerPhone;
    private RegisterAccount registerAccount;

    public RegisterAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            if(registerPhone==null){
                registerPhone = new RegisterPhone();
                return registerPhone;
            }else{
                return registerPhone;
            }
        }else if (position==1){
            if(registerAccount==null){
                registerAccount = new RegisterAccount();
                return registerAccount;
            }else{
                return registerAccount;
            }
        }else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
