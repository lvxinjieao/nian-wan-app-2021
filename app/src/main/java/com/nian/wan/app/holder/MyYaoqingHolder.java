package com.nian.wan.app.holder;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseHolder;
import com.nian.wan.app.bean.MyYaoqingBean;
import com.mc.developmentkit.utils.MCUtils;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * \
 * 我的邀请Holder
 * Created by Administrator on 2017/3/21.
 */

public class MyYaoqingHolder extends BaseHolder<MyYaoqingBean> {
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.qian)
    TextView qian;

    @Override
    protected void refreshView(MyYaoqingBean myYaoqingBean, int position, Activity activity) {
        String s = hidePhoneNumberMiddleFour(myYaoqingBean.account);
        account.setText(s);
        time.setText(MCUtils.getDataYMD(myYaoqingBean.time));
        qian.setText(myYaoqingBean.qian);
    }

    @Override
    protected View initView() {
        View inflate = LinearLayout.inflate(x.app(), R.layout.holder_myyaoqing, null);
        ButterKnife.bind(this, inflate);
        inflate.setTag(this);
        return inflate;
    }

    public String hidePhoneNumberMiddleFour(String phoneNUm) {

        if(phoneNUm != null && !"".equals(phoneNUm)) {
            if(phoneNUm.length()<8){
                return phoneNUm;
            }
            String maskNumber = phoneNUm.substring(0, 3) + "****" + phoneNUm.substring(7, phoneNUm.length());
            return maskNumber;
        } else {
            return phoneNUm;
        }
    }
}
