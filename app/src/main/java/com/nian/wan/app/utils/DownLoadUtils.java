package com.nian.wan.app.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DownLoadUtils {

    public interface DownLoadInterFace {
        void afterDownLoad(ArrayList<String> savePaths);
    }

    public static void downLoad(String savePath, DownLoadInterFace downLoadInterFace, String... urls) {
        new DownLoadTask(savePath, downLoadInterFace).execute(urls);
    }

    public static void downLoad(Activity activity, String savePath, DownLoadInterFace downLoadInterFace, String... urls) {
        new DownLoadTask(activity, savePath, downLoadInterFace).execute(urls);
    }

    private static class DownLoadTask extends AsyncTask<String, Integer, ArrayList<String>> {

        private Activity activity;
        private String mSavePath;
        private DownLoadInterFace mDownLoadInterFace;

        private DownLoadTask(String savePath, DownLoadInterFace downLoadTask) {
            this.mSavePath = savePath;
            this.mDownLoadInterFace = downLoadTask;
        }

        private DownLoadTask(Activity activity, String savePath, DownLoadInterFace downLoadTask) {
            this.activity = activity;
            this.mSavePath = savePath;
            this.mDownLoadInterFace = downLoadTask;
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            final ArrayList<String> names = new ArrayList<>();

            for (final String url : params) {
                if (!TextUtils.isEmpty(url)) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  // 获得存储卡的路径

                        //////////////////////////////////////////
//                        FileOutputStream fos = null;
//                        InputStream is = null;
//                        try {
//                            URL downUrl = new URL(url);// 创建连接
//                            HttpURLConnection conn = (HttpURLConnection) downUrl.openConnection();
//                            conn.connect();
//                            is = conn.getInputStream(); // 创建输入流
//                            File file = new File(mSavePath);
////
////                          // 判断文件目录是否存在
//                            if (!file.exists()) {
//                                file.mkdirs();
//                            }
////
//                            String[] split = url.split("/");
//                            String fileName = split[split.length - 1];//图片名
//                            final File mApkFile = new File(mSavePath, fileName);//图片路径
//                            names.add(mApkFile.getAbsolutePath());//绝对路劲
//                            fos = new FileOutputStream(mApkFile, false);
//                            int count = 0;
//                            // 缓存
//                            byte buf[] = new byte[1024];
//                            while (true) {
//                                int read = is.read(buf);
//                                if (read == -1) {
//                                    break;
//                                }
//                                fos.write(buf, 0, read);
//                                count += read;
//                                publishProgress(count);
//                            }
//                            fos.flush();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            try {
//                                if (is != null) {
//                                    is.close();
//                                }
//                                if (fos != null) {
//                                    fos.close();
//                                }
//                            } catch (IOException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
                        //////////////////////////////////////////
                        Glide.with(activity).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                File file = new File(mSavePath);
                                // 判断文件目录是否存在
                                if (!file.exists()) {
                                    file.mkdirs();
                                }

                                String[] split = url.split("/");//切割
                                String fileName = split[split.length - 1];//图片名
                                final File mApkFile = new File(mSavePath, fileName);//图片路径

                                names.add(mApkFile.getAbsolutePath());//绝对路劲（全路劲）

                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(mApkFile);
                                    resource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//
//                                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                                File f = new File(mApkFile.getAbsolutePath());
//                                Uri contentUri = Uri.fromFile(f);
//                                mediaScanIntent.setData(contentUri);
//                                activity.sendBroadcast(mediaScanIntent);
                            }
                        });
                    }
                }
            }
            return names;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (mDownLoadInterFace != null) {
                mDownLoadInterFace.afterDownLoad(strings);
            }
        }
    }
}
