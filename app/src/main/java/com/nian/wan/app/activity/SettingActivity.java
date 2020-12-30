package com.nian.wan.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.Utils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置activity
 */
public class SettingActivity extends BaseFragmentActivity {
    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.modifypassword)
    RelativeLayout modifypassword;
    @BindView(R.id.bindphone)
    RelativeLayout bindphone;
    @BindView(R.id.tuichu)
    TextView tuichu;
    public static SettingActivity act;

    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        act = this;
        Utils.initActionBarPosition(this,tou);
        title.setText("设置");
    }

    @OnClick({R.id.back, R.id.modifypassword, R.id.bindphone, R.id.tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.modifypassword:
                startActivity(new Intent(this,ModifyPasswordActivity.class));
                break;
            case R.id.bindphone:
                UserInfo loginUser = Utils.getLoginUser();
                if(loginUser.phone.equals("")){
                    startActivity(new Intent(this,BangdingPhoneActivity.class));
                }else{
                    startActivity(new Intent(this,ReplacePhoneActivity.class));
                }
                break;
            case R.id.tuichu:
                DbManager db = DatabaseOpenHelper.getInstance();
                try {
                    db.delete(UserInfo.class);
                    finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
