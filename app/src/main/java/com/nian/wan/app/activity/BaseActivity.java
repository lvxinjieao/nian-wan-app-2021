package com.nian.wan.app.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.nian.wan.app.R;
import com.nian.wan.app.download.DownloadManager;
import com.nian.wan.app.http.HttpConstant;
import com.blankj.utilcode.util.ServiceUtils;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (!ServiceUtils.isServiceRunning(this, HttpConstant.DownServer)) {
            ServiceUtils.startService(this, DownloadManager.class);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.close_left_in, R.anim.close_left_out);
    }
}
