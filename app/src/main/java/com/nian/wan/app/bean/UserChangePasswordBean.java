package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/12/14
 * @Description: 用户更改密码
 * @Modify By:
 * @ModifyDate:
 */
public class UserChangePasswordBean {


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
