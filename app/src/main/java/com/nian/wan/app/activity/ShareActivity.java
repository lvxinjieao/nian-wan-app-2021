package com.nian.wan.app.activity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.ShareInfo;
import com.nian.wan.app.utils.Shares;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分享
 * Created by Administrator on 2017/5/5.
 */

public class ShareActivity extends BaseFragmentActivity {
    @BindView(R.id.qq)
    RelativeLayout qq;
    @BindView(R.id.weixin)
    RelativeLayout weixin;
    @BindView(R.id.pyq)
    LinearLayout pyq;
    @BindView(R.id.kongjian)
    RelativeLayout kongjian;
    @BindView(R.id.xlwb)
    RelativeLayout xlwb;
    @BindView(R.id.quxiao)
    TextView quxiao;
    private ShareInfo shareInfo;

    @Override
    public void initView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        shareInfo = (ShareInfo)getIntent().getSerializableExtra("shareInfo");
    }

    @OnClick({R.id.qq, R.id.weixin, R.id.pyq, R.id.kongjian, R.id.xlwb, R.id.quxiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qq:
                Shares.QQ(x.app(),shareInfo);
                break;
            case R.id.weixin:
                Shares.Wechat(x.app(),shareInfo);
                break;
            case R.id.pyq:
                Shares.WechatMoments(x.app(),shareInfo);
                break;
            case R.id.kongjian:
                Shares.QZone(x.app(),shareInfo);
                break;
            case R.id.xlwb:
                Shares.SinaWeibo(x.app(),shareInfo);
                break;
            case R.id.quxiao:
                finish();
                break;
        }
        finish();
    }
}
