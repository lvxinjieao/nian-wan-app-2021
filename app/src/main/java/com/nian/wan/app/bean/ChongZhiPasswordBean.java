package com.nian.wan.app.bean;

/**
 * Created by Administrator on 2017/5/20.
 */

public class ChongZhiPasswordBean {

    /**
     * msg : 修改成功
     * return_code : success
     * status : 1
     */
    private String msg;
    private String return_code;
    private int status;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public int getStatus() {
        return status;
    }
}
