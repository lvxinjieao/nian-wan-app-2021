package com.nian.wan.app.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 个人中心-已实名认证
 */
public class AlreadyRealNameActivity extends FragmentActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_already_real_name)
    TextView mTvAlreadyName;
    @BindView(R.id.tv_already_real_name_id_card)
    TextView mTvAlreadyIdCard;
    //H5游戏传来的重新加载URL
    private String reloadUrl;
    //是否要执行H5重新加载
    private boolean h5GameReload;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_already_real_name);
        ButterKnife.bind(this);
        Utils.initActionBarPosition(this, tou);
        title.setText("实名认证");

        mTvAlreadyName.setText("真实姓名：" + getIntent().getStringExtra("real_name"));
        mTvAlreadyIdCard.setText("身份证号：" + Utils.hiddenIdCard(getIntent()
                .getStringExtra("id_card")));
        h5GameReload = getIntent().getBooleanExtra("h5GameReload", false);
        if (h5GameReload) {
            mTvAlreadyName.setText("真实姓名：" + getIntent()
                    .getStringExtra("personName"));
            mTvAlreadyIdCard.setText("身份证号：" + Utils.hiddenIdCard(getIntent()
                    .getStringExtra("idCard")));
            reloadUrl = getIntent().getStringExtra("reloadUrl");
        }
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back:
                finish();
                break;
        }
    }


    /**
     * 响应点击事件
     *
     * @param action
     */
    public void doClick(int action) {

    }


}
