package com.nian.wan.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nian.wan.app.R;
import com.nian.wan.app.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 描述：下载Diaolog
 * 作者：闫冰
 * 时间: 2018-03-06 14:16
 */
public class DownLoadDialog extends Dialog {
    private View view;
    private TextView tvTitle, tvHint, tvPercent, tvSize, btnOk,btnCancel;
    private ProgressBar progressBar;
    private Context context;
    private boolean isWifi;
    private RelativeLayout downLayout;
    private String DownURL;
    private Callback.Cancelable cancelable;
    private boolean isDownLoad;

    public DownLoadDialog(@NonNull Context context, int themeResId, String downUrl, boolean isWIFI) {
        super(context, themeResId);
        view = LinearLayout.inflate(context,R.layout.dialog_download,null);
        this.context = context;
        this.isWifi = isWIFI;
        this.DownURL = downUrl;
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
        downLayout = (RelativeLayout) view.findViewById(R.id.down_layout);
        tvTitle = (TextView) view.findViewById(R.id.tv_download_title);
        tvHint = (TextView) view.findViewById(R.id.tv_net_hint);
        tvSize = (TextView) view.findViewById(R.id.tv_download_size);
        tvPercent = (TextView) view.findViewById(R.id.tv_download_percent);
        btnOk = (TextView) view.findViewById(R.id.btn_ok);
        btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        btnCancel.setOnClickListener(itemClickListner);
        btnOk.setOnClickListener(itemClickListner);
        tvSize.setText("0/" + 100);

        if (isWifi){
            tvTitle.setText("正在下载");
            tvHint.setVisibility(View.GONE);
            btnOk.setVisibility(View.GONE);
            downLayout.setVisibility(View.VISIBLE);
            startDown(DownURL);
        }else {
            tvTitle.setText("确定下载?");
            tvHint.setVisibility(View.VISIBLE);
            downLayout.setVisibility(View.GONE);
            btnOk.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener itemClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == btnCancel.getId()) {
                if (isDownLoad){
                    cancelable.cancel();
                }
                dismiss();
            }
            if (v.getId() == btnOk.getId()) {
                if (!TextUtils.isEmpty(DownURL)) {
                    startDown(DownURL);
                } else {
                    Log.e("下载链接为空", "下载链接为空");
                    Utils.TS("下载失败，下载链接参数有误");
                    dismiss();
                }

            }
        }
    };

    private File apkFile;
    //下载
    protected void startDown(String url) {
        btnOk.setVisibility(View.GONE);
        tvTitle.setText("正在下载");
        tvHint.setVisibility(View.GONE);
        downLayout.setVisibility(View.VISIBLE);

        RequestParams entity = new RequestParams(url);
        entity.setAutoResume(true);//是否断点下载
        entity.setAutoRename(false);//下载完成后是否自动重命名
        entity.setCancelFast(true);//快速取消
        entity.setSaveFilePath(Utils.getApkFile(String.valueOf(00003)).getAbsolutePath());//文件保存路径
        cancelable = x.http().get(entity, new Callback.ProgressCallback<File>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Utils.TS("下载失败");
                dismiss();
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(File arg0) {
                apkFile = Utils.getApkFile(String.valueOf(00003));
                tvPercent.setText("100%");
                tvSize.setText("100/"+100);
                String apkName = apkFile.getName();
                if (apkName.substring(apkName.length()-4,apkName.length()).equals(".apk")){
                    Utils.installApp(context, apkFile);
                }else {
                    Utils.TS("此文件不是安卓apk安装包，暂不支持打开此文件");
                }
                dismiss();
            }

            @Override
            public void onLoading(long arg0, long arg1, boolean arg2) {
                int progress = (int) (arg1 * 100f / arg0 + 0.5f);
                String spent = Utils.getSpent(arg1);
                String size1 = Utils.getSize(arg1, arg0);
                tvPercent.setText(progress+"%");
                tvSize.setText(progress+"/"+100);
                progressBar.setProgress(progress);
            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onWaiting() {
                tvPercent.setText("0%");
                tvSize.setText("0/"+100);
                progressBar.setProgress(0);
            }
        });
        isDownLoad = true;
    }
}
