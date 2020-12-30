package com.nian.wan.app.bean;

import java.io.Serializable;

/**
 * Created by Lebron on 2016/9/13.
 * 分享内容
 */
public class ShareInfo implements Serializable {

    public String logoUrl;
    public String title;
    public String text;
    public String shareUrl;

    @Override
    public String toString() {
        return "ShareInfo{" +
                "logoUrl='" + logoUrl + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }
}
