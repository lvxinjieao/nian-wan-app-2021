package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/12/20
 * @Description: 设置开服通知实体类
 * @Modify By:
 * @ModifyDate:
 */
public class SetOpenServerPushBean {


    /**
     * code : 200
     * msg : 操作成功
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
