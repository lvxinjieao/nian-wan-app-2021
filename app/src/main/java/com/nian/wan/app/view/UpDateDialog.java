package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


/**
 * @Author: XiaYuShi
 * @Date: 2018/2/7
 * @Description:
 * @Modify By:
 * @ModifyDate:
 */
public class UpDateDialog extends Dialog {

    private View view;
    private TextView tvTitle, tvSize, tvSpeed, tvCancle, tvSure;
    private ProgressBar progressBar;
    private Context context;

    public UpDateDialog(Context context, int themeResId) {
        super(context, themeResId);
        view = LinearLayout.inflate(context, R.layout.dialog_up_date, null);
        this.context = context;
    }

    public UpDateDialog(Context context) {
        super(context);
    }

    protected UpDateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(view);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        tvSpeed = (TextView) view.findViewById(R.id.tv_download_spend);
        tvSize = (TextView) view.findViewById(R.id.tv_download_size);
        tvCancle = (TextView) view.findViewById(R.id.tv_download_cance);
        tvSure = (TextView) view.findViewById(R.id.tv_download_sure);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        tvCancle.setOnClickListener(itemClickListner);
        tvSure.setOnClickListener(itemClickListner);
        if (!TextUtils.isEmpty(Utils.getPersistentAboutUsData().getAPP_FILE_SIZE())){
            tvSize.setText("0M/" + Utils.getPersistentAboutUsData().getAPP_FILE_SIZE());
        }else {
            tvSize.setText("0M/" + "0M");
        }
    }


    private View.OnClickListener itemClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == tvCancle.getId()) {
                dismiss();
            }
            if (v.getId() == tvSure.getId()) {
                if (!TextUtils.isEmpty(Utils.getPersistentAboutUsData().getAPP_DOWNLOAD())) {
                    startDown(Utils.getPersistentAboutUsData().getAPP_DOWNLOAD());
                    tvSure.setEnabled(false);
                } else {
                    Log.e("下载链接为空", "下载链接为空");
                    Utils.TS("下载失败，下载地址有误");
                    dismiss();
                }

            }
        }
    };

    private File apkFile;
    private boolean isComplete = false;
    //下载

    protected void startDown(String url) {
        RequestParams entity = new RequestParams(url);
        entity.setAutoResume(true);//是否断点下载
        entity.setAutoRename(false);//下载完成后是否自动重命名
        entity.setCancelFast(true);//快速取消
        entity.setSaveFilePath(Utils.getApkFile(String.valueOf(00003)).getAbsolutePath());//文件保存路径
        Callback.Cancelable cancelable = x.http().get(entity, new Callback.ProgressCallback<File>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.e("下载失败",arg0.toString());
                Utils.TS("下载失败");
                dismiss();
                //System.exit(0);
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(File arg0) {
                apkFile = Utils.getApkFile(String.valueOf(00003));
                Utils.installApp(context, apkFile);
                isComplete = true;
                tvSpeed.setText("0kb/s");
                tvSure.setText("安装");
                dismiss();
            }

            @Override
            public void onLoading(long arg0, long arg1, boolean arg2) {
                int progress = (int) (arg1 * 100f / arg0 + 0.5f);
                String spent = Utils.getSpent(arg1);
                String size1 = Utils.getSize(arg1, arg0);
                tvSpeed.setText(spent);
                tvSize.setText(size1);
                progressBar.setProgress(progress);
            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onWaiting() {
                tvSpeed.setText("0kb/s");
                tvSize.setText("0M/" + "0M");
                progressBar.setProgress(0);
            }
        });
    }
}
