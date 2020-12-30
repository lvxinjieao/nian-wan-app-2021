package com.nian.wan.app.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.nian.wan.app.bean.IconBean;
import com.nian.wan.app.bean.Splash;
import com.nian.wan.app.http.HttpConstant;
import com.nian.wan.app.http.HttpResult;
import com.nian.wan.app.utils.DownLoadUtils;
import com.nian.wan.app.utils.FileUtils;
import com.nian.wan.app.utils.LogUtils;
import com.nian.wan.app.utils.SerializableUtils;
import com.nian.wan.app.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SplashDownLoadService extends IntentService {

    public Splash splash;
    public static Activity act;
    public static final int TYPE_ANDROID = 1;
    public static final String SPLASH_FILE_NAME = "splash.png";

    public SplashDownLoadService() {
        super("SplashDownLoad");
    }

    public static void startDownLoadSplashImage(Activity context, String action) {
        act = context;
        Intent intent = new Intent(context, SplashDownLoadService.class);
        intent.putExtra("tiao", action);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getStringExtra("tiao");
            if (action.equals("download_splash")) {
                loadSplashNetDate();
            }
        }
    }

    private void loadSplashNetDate() {
        HttpConstant.POST(handler, HttpConstant.API_SPLASH, null, false, null, "闪屏页返回的数据");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        String aNull = msg.obj.toString().replace("null", "\"\"");
                        LogUtils.w("闪屏页返回的数据", aNull);
                        JSONObject jsonObject = new JSONObject(aNull);
                        int code = jsonObject.getInt("code");
                        String msg1 = jsonObject.getString("msg");
                        if (code == 200) {
                            Type type = new TypeToken<HttpResult<IconBean>>() {
                            }.getType();
                            HttpResult<IconBean> data = HttpConstant.getData(aNull, type, "闪屏页返回的数据");
                            if (data != null) {
                                String start_icon = data.getData().getStart_icon();
                                Splash splashLocal = getSplashLocal();

                                String absolutePath = FileUtils.getIconDir().getAbsolutePath();
                                splash = new Splash(start_icon, absolutePath);


                                if (!start_icon.equals("")) {
                                    if (splashLocal == null) {
                                        Log.e("SplashDemo", "splashLocal 为空导致下载");
                                        startDownLoadSplash(absolutePath, start_icon);
                                    } else if (isNeedDownLoad(splashLocal.path, start_icon)) {
                                        Log.e("SplashDemo", "isNeedDownLoad 导致下载");
                                        startDownLoadSplash(absolutePath, start_icon);
                                    }
                                } else {
                                    if (splashLocal != null) {
                                        File splashFile = null;
                                        try {
                                            splashFile = SerializableUtils.getSerializableFile(absolutePath, SPLASH_FILE_NAME);
                                            if (splashFile.exists()) {
                                                splashFile.delete();
                                                Log.e("SplashDemo", "mScreen为空删除本地文件");
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } else {
                                LogUtils.e("HttpResult解析失败", "HttpResult解析失败");
                            }
                        } else {
//                            _onError(GetErrorMsg.getMes(code));
                            Utils.TS(msg1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LogUtils.e("MCHttp错误", "" + e.toString());
                    }
                    break;
            }
        }
    };

    private Splash getSplashLocal() {
        Splash splash = null;
        try {
            File splashFile = SerializableUtils.getSerializableFile(FileUtils.getIconDir().getAbsolutePath(), SPLASH_FILE_NAME);
            splash = (Splash) SerializableUtils.readObject(splashFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splash;
    }

    /**
     * @param path 本地存储的图片绝对路径
     * @param url  网络获取url
     * @return 比较储存的 图片名称的哈希值与 网络获取的哈希值是否相同
     */
    private boolean isNeedDownLoad(String path, String url) {
        if (TextUtils.isEmpty(path)) {
            Log.d("SplashDemo", "本地path " + TextUtils.isEmpty(path));
            Log.d("SplashDemo", "本地url " + TextUtils.isEmpty(url));
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            Log.d("SplashDemo", "本地file " + file.exists());
            return true;
        }
        if (getImageName(path).hashCode() != getImageName(url).hashCode()) {
            Log.d("SplashDemo", "path hashcode " + getImageName(path) + " " + getImageName(path).hashCode());
            Log.d("SplashDemo", "url hashcode " + getImageName(url) + " " + getImageName(url).hashCode());
            return true;
        }
        return false;
    }


    private String getImageName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] split = url.split("/");
        String nameWith_ = split[split.length - 1];
        String[] split1 = nameWith_.split("\\.");
        return split1[0];
    }

    private void startDownLoadSplash(final String splashPath, final String url) {


        DownLoadUtils.downLoad(act,splashPath, new DownLoadUtils.DownLoadInterFace() {

            @Override
            public void afterDownLoad(ArrayList<String> savePaths) {
                if (savePaths.size() == 1) {
                    Log.d("SplashDemo", "闪屏页面下载完成" + savePaths);
                    if (splash != null) {
                        splash.path = savePaths.get(0);
                    }
                    SerializableUtils.writeObject(splash, FileUtils.getIconDir().getAbsolutePath() + "/" + SPLASH_FILE_NAME);
                } else {
                    Log.d("SplashDemo", "闪屏页面下载失败" + savePaths);
                }
            }
        }, url);
    }


}