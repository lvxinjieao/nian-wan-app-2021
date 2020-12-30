package com.nian.wan.app.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.utils.DownLoadImageService;
import com.nian.wan.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：关注微信公众号Dialog
 * 作者：闫冰
 * 时间: 2018-05-23 11:10
 */
public class DialogWeChatOfficialAccounts extends Dialog {
    @BindView(R.id.img_RQ_Code)
    ImageView imgRQCode;
    @BindView(R.id.btn_save_code)
    TextView btnSaveCode;
    @BindView(R.id.btn_close)
    RelativeLayout btnClose;

    private View view;
    private Activity activity;
    private String codeURL;

    public DialogWeChatOfficialAccounts(@NonNull Activity mActivity, int themeResId, String imgURL) {
        super(mActivity, themeResId);
        this.codeURL = imgURL;
        this.activity = mActivity;
        view = LinearLayout.inflate(mActivity, R.layout.dialog_wechat_official_accounts, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        ButterKnife.bind(this);

        Glide.with(activity).load(codeURL).into(imgRQCode);
    }


    @OnClick({R.id.btn_save_code, R.id.btn_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_code:
                saveCode(codeURL);
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }


    private void saveCode(final String imgURL){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsUtil.requestPermission(activity, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    if (imgURL!=null && !imgURL.equals("")){
                        onDownLoad(imgURL);
                    }
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    Utils.TS("权限被拒绝，保存二维码失败");
                }
            }, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        } else {
            if (imgURL!=null && !imgURL.equals("")){
                onDownLoad(imgURL);
            }
        }
    }


    /**
     * 启动图片下载线程
     */
    private void onDownLoad(String url) {
        DownLoadImageService service = new DownLoadImageService(activity.getApplicationContext(), url, new DownLoadImageService.ImageDownLoadCallBack() {

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                Log.e("二维码bitmap","保存成功");
                mHander.sendEmptyMessage(1);
            }

            @Override
            public void onDownLoadFailed() {
                Log.e("二维码图片","保存失败");
                mHander.sendEmptyMessage(2);
            }
        });
        new Thread(service).start();
    }

    @SuppressLint("HandlerLeak")
    Handler mHander  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Utils.TS("二维码保存成功");
                    dismiss();
                    break;
                case 2:
                    Utils.TS("二维码设置失败，请稍候再试");
                    break;
            }
        }
    };

}
