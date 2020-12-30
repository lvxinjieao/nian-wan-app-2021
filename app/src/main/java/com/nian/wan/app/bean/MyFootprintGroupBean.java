package com.nian.wan.app.bean;

/**
 * @Description: 我的足迹组元素
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/8 11:41
 * @Modified By:
 * @Modified Date:
 */
public class MyFootprintGroupBean {

    private String id;
    private String date;
    private boolean isChoosed;


    public MyFootprintGroupBean() {
    }

    public MyFootprintGroupBean(String id, String date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    @Override
    public String toString() {
        return "MyFootprintGroupBean{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }
}
