package com.nian.wan.app.bean;

import java.io.Serializable;

public class GameMenuBean implements Serializable {

    private String id;
    private String type_name;
    private int type_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getType_image() {
        return type_image;
    }

    public void setType_image(int type_image) {
        this.type_image = type_image;
    }
}
