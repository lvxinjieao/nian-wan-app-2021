package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2018/1/12
 * @Description: 用户直接进行签到实体类
 * @Modify By:
 * @ModifyDate:
 */
public class UserSignBean {


    /**
     * code : 200
     * msg : success
     * data :
     */

    private int code;
    private String msg;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
