package com.nian.wan.app.activity;


import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.bean.ShareInfo;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.utils.FileUtils;
import com.nian.wan.app.utils.Shares;
import com.nian.wan.app.utils.Utils;

import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

;

/**
 * 展示邀请好友分享二维码页面
 */
public class ShareQRCodeActivity extends Activity {
    @BindView(R.id.qq)
    RelativeLayout qq;
    @BindView(R.id.weixin)
    RelativeLayout weixin;
    @BindView(R.id.pyq)
    LinearLayout pyq;
    @BindView(R.id.kongjian)
    RelativeLayout kongjian;
    @BindView(R.id.fuzhi)
    RelativeLayout fuzhi;
    @BindView(R.id.erweima)
    ImageView erweima;
    @BindView(R.id.rl_group)
    RelativeLayout rlGroup;

    private int name;
    private ShareInfo shareInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_qr_share);
        ButterKnife.bind(this);

        shareInfo = (ShareInfo) getIntent().getSerializableExtra("shareInfo");
        name = getIntent().getIntExtra("name", 0);
        setQqCode();
    }

    @OnClick({R.id.qq, R.id.weixin, R.id.pyq, R.id.kongjian,R.id.fuzhi,R.id.rl_group})
    public void onClick(View view) {
        Shares.TYPE = name;
        switch (view.getId()) {
            case R.id.qq:                   //分享到QQ
                Shares.QQ(x.app(), shareInfo);
                break;
            case R.id.weixin:               //分享到微信
                Shares.Wechat(x.app(), shareInfo);
                break;
            case R.id.pyq:                  //分享到微信朋友圈
                Shares.WechatMoments(x.app(), shareInfo);
                break;
            case R.id.kongjian:             //分享到QQ空间
                Shares.QZone(x.app(), shareInfo);
                break;
            case R.id.fuzhi:              //复制链接
                ClipboardManager cmb = (ClipboardManager) x.app().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(shareInfo.shareUrl);
                ToastUtil.showToast("复制成功");
                break;
            case R.id.rl_group:
                finish();
                break;
            default:
                break;
        }
        finish();
    }


    /**
     * 生成二维码Bitmap
     * @param content   内容
     * @param widthPix  图片宽度
     * @param heightPix 图片高度
     * @param logoBm    二维码中心的Logo图标（可以为null）
     * @param filePath  用于存储二维码图片的文件路径
     * @return 生成二维码及保存文件是否成功
     */
    public static boolean createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm, String filePath) {
        try {
            if (content == null || "".equals(content)) {
                return false;
            }

            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 1); //default is 4

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            Log.e("创建二维码异常",e.toString());
        }

        return false;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            //canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }


    /**
     * 保存二维码
     */
    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... params) {
            String result = getResources().getString(R.string.save_picture_failed);
            try {
                File imageFile = new File(FileUtils.getIconDir(), System.currentTimeMillis() + ".jpg");
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = params[0];
                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                result = getResources().getString(R.string.save_picture_success, FileUtils.getIconDir().getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            erweima.setDrawingCacheEnabled(false);
        }
    }


    /**
     * 二维码相关设置
     */
    private void setQqCode(){
        if (!TextUtils.isEmpty(shareInfo.shareUrl)){
            Bitmap bmp= BitmapFactory.decodeResource(x.app().getResources(), R.drawable.app_icon);
            int i = Utils.dip2px(120);
            String path = FileUtils.getIconDir().getAbsolutePath() + "/二维码.jpg";
            boolean qrImage = createQRImage(shareInfo.shareUrl, i, i, bmp,path);
            if(qrImage){
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                erweima.setImageBitmap(bitmap);

                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(), path, HttpConstant.FilePath, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // 最后通知图库更新
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
            }
            erweima.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToastUtil.showToast("成功保存至相册");
                    return false;
                }
            });
        }
    }

}
