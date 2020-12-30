package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/12/15
 * @Description: 用户获取验证码
 * @Modify By:
 * @ModifyDate:
 */
public class UserVerificationCodeBean {


    /**
     * code : 200
     * msg : success
     * data : 892169
     */

    private int code;
    private String msg;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
