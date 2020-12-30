package com.nian.wan.app.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nian.wan.app.http.HttpConstant;

import org.xutils.x;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/6/28.
 */
public class FileUtils {

    public static final String CACHE = "cache";
    public static final String ICON = "icon";
    public static final String DOWNLOAD = "download";
    public static final String ROOT = HttpConstant.FilePath;

    /**
     * 获取图片的缓存的路径
     *
     * @return
     */
    public static File getIconDir() {
        return getDir(ICON);
    }

    /**
     * 获取app下载的路径
     *
     * @return
     */
    public static File getDownloadDir() {
        return getDir(DOWNLOAD);
    }

    /**
     * 获取缓存路径
     *
     * @return
     */
    public static File getCacheDir() {
        return getDir(CACHE);
    }

    public static File getDir(String cache) {
        StringBuilder path = new StringBuilder();
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            path.append(File.separator);
            path.append("Android");
            path.append(File.separator);
            path.append("data");
            path.append(File.separator);
            path.append(x.app().getPackageName());
            path.append(File.separator);
            path.append(ROOT);// /mnt/sdcard/GooglePlay
            path.append(File.separator);
            path.append(File.separator);
            path.append(cache);// /mnt/sdcard/GooglePlay/cache
        } else {
            File filesDir = x.app().getCacheDir();    //  cache  getFileDir file
            path.append(filesDir.getAbsolutePath());// /data/data/com.itheima.googleplay/cache
            path.append(File.separator);///data/data/com.itheima.googleplay/cache/
            path.append(cache);///data/data/com.itheima.googleplay/cache/cache
        }
        String c = path.toString();
        File file = new File(c);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();// 创建文件夹
        }
        return file;
    }

    /**
     * 获取父路径
     *
     * @param
     * @return
     */
    public static File getDir_de() {
        StringBuilder path = new StringBuilder();
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            path.append(File.separator);
            path.append("Android");
            path.append(File.separator);
            path.append("data");
            path.append(File.separator);
            path.append(x.app().getPackageName());
            path.append(File.separator);// '/'
            path.append(ROOT);// /mnt/sdcard/GooglePlay
            path.append(File.separator);// '/'
        } else {
            File filesDir = x.app().getCacheDir();    //  cache  getFileDir file
            path.append(filesDir.getAbsolutePath());// /data/data/com.itheima.googleplay/cache
        }
        String c = path.toString();
        File file = new File(c);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();// 创建文件夹
        }
        return file;
    }

    /**
     * 获取父路径
     *
     * @param
     * @return
     */
    public static File getPath() {
        StringBuilder path = new StringBuilder();
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            path.append(File.separator);
            path.append("Android");
            path.append(File.separator);
            path.append("data");
            path.append(File.separator);
            path.append(x.app().getPackageName());
            path.append(File.separator);// '/'
            path.append(ROOT);// /mnt/sdcard/GooglePlay
        } else {
            File filesDir = x.app().getCacheDir();      //  cache  getFileDir file
            path.append(filesDir.getAbsolutePath());    // /data/data/com.itheima.googleplay/cache
        }
        String c = path.toString();
        File file = new File(c);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();// 创建文件夹
        }
        return file;
    }

    /**
     * 判断sd卡是否可用
     */
    public static boolean isSDAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * * 清除本应用所有的数据 * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }


    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath, TextView a, View b) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true, a, b);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                        a.setText("清理完成!");
                        b.setVisibility(View.VISIBLE);
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                            a.setText("清理完成!");
                            b.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }
}
