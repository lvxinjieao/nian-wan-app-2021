package com.nian.wan.app.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 验证原来的手机号
 * Created by Administrator on 2017/4/26.
 */

public class VerificationPhoneActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.miao)
    TextView miao;
    @BindView(R.id.zhong)
    RelativeLayout zhong;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private TimeCount time;
    private String phone;

    @Override
    public void initView() {
        setContentView(R.layout.activity_yanzhengphone);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this,tou);
        title.setText("验证原手机号");
        phone = Utils.getLoginUser().phone;
    }





    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程显示
            miao.setText(millisUntilFinished / 1000 + "");
//            getyzm.setVisibility(View.GONE);
            zhong.setVisibility(View.VISIBLE);
        }
        @Override
        public void onFinish() {// 计时完毕时触发
//            getyzm.setVisibility(View.VISIBLE);
            zhong.setVisibility(View.GONE);
        }
    }
}
