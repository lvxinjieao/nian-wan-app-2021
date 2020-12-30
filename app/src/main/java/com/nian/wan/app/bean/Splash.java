package com.nian.wan.app.bean;

import java.io.Serializable;

public class Splash implements Serializable {

    private static final long serialVersionUID = 7382351359868556980L;//这里需要写死 序列化Id

    public int id;
    public String url;      //大图 url
    public String path;     //图片的存储地址

    public Splash(String url, String path) {
        this.url = url;
        this.path = path;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Splash{" +
                "url='" + url + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
