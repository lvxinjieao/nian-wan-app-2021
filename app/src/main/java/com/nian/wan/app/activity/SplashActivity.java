package com.nian.wan.app.activity;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.reflect.TypeToken;
import com.mc.developmentkit.utils.NetworkUtils;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.AboutUsData;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.http.HttpUtils;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.PromoteUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends BaseFragmentActivity {


    @BindView(R.id.splash_image)
    ImageView splash_image;

    @BindView(R.id.splash_skip)
    Button splash_skip;

    private Timer timer;

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        PermissionsUtil.requestPermission(SplashActivity.this, new PermissionListener() {

            @Override
            public void permissionGranted(String[] permissions) {  //已经获得授权
                start();
            }

            @Override
            public void permissionDenied(String[] permissions) {
                ToastUtil.showToast("未获得授权");
                finish();
            }
        }, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});


        //欢迎页无论以哪种方式启动都要首先缓存关于我们信息
        if (NetworkUtils.NetworkIsOk()) {
            Type type = new TypeToken<HttpResult<AboutUsData>>() {
            }.getType();
            Map<String, String> map = new HashMap();
            map.put("promote_id", new PromoteUtils().getPromoteId());
            map.put("type", "1");
            new HttpUtils<AboutUsData>(type, HttpConstant.API_PERSONAL_USER_ABOUT_US, map, "关于我们信息", false) {

                @Override
                protected void _onSuccess(AboutUsData aboutUsData, String msg) {
                    DbManager db = DatabaseOpenHelper.getInstance();
                    aboutUsData.id = 2;
                    HttpConstant.qunkey = aboutUsData.getGroup_key();
                    try {
                        db.saveOrUpdate(aboutUsData);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void _onError(String message, int code) {
                }

                @Override
                protected void _onError() {
                }
            };
        } else {
            ToastUtil.showToast("沒有连接网络...");
        }
    }


    private void start() {

        timer = new Timer();

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        };

        if (NetworkUtils.NetworkIsOk()) {
            timer.schedule(task, 2500);
        } else {
            ToastUtil.showToast("沒有连接网络...");
        }
    }


    @OnClick({R.id.splash_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.splash_skip:
                if (NetworkUtils.NetworkIsOk()) {
                    timer.cancel();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    ToastUtil.showToast("沒有连接网络...");
                }
                break;
        }
    }
}
