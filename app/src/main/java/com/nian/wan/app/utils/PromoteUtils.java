package com.nian.wan.app.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 描述：渠道信息相关工具类
 */
public class PromoteUtils {

    private String promote_id;  //渠道ID
    private String promote_account;  //渠道名字
    public String getPromoteId() {
        return promote_id;
    }
    public String getPromoteAccount() {
        return promote_account;
    }

    public PromoteUtils() {
        promote_id = "0";
        promote_account = "自然注册";
        String json = getStr();
        if(!TextUtils.isEmpty(json)){
            initJson(json);
        }
    }


    /**
     * 初始化
     */
    private void initJson(String json) {
        try {
            JSONObject js = new JSONObject(json);
            promote_id = js.getString("promote_id");
            promote_account = js.getString("promote_account");
        } catch (JSONException e) {
            promote_id = "0";
            promote_account = "自然注册";
        }
    }

    /**
     * 返回文件中字符串
     */
    private String getStr() {
        InputStream is = null;
        BufferedReader reader = null;
        String result = "";
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            String path = "/META-INF/mch.properties";
            is = this.getClass().getResourceAsStream(path);
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            result = "";
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }
}
