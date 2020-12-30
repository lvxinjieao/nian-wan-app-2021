package com.nian.wan.app.utils;

import android.util.Log;

/**
 * 描述：Log工具类
 */
public class LogUtils {
    public static void i(String TAG, String context){
        Log.i("level-I----"+TAG,context);
    }
    public static void d(String TAG, String context){
        Log.d("level-D----"+TAG,context);
    }
    public static void e(String TAG, String context) { Log.e("level-E----"+TAG,context); }
    public static void w(String TAG, String context){
        Log.w("level-W----"+TAG,context);
    }
}
