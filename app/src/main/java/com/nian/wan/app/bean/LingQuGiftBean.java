package com.nian.wan.app.bean;

/**
 * Created by Administrator on 2017/5/10.
 */

public class LingQuGiftBean {

    /**
     * msg : {"novice":"123456"}
     * return_code : 1
     * status : 1
     */
    private MsgEntity msg;
    private String return_code;
    private int status;

    public void setMsg(MsgEntity msg) {
        this.msg = msg;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MsgEntity getMsg() {
        return msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public int getStatus() {
        return status;
    }

    public class MsgEntity {
        /**
         * novice : 123456
         */
        private String novice;

        public void setNovice(String novice) {
            this.novice = novice;
        }

        public String getNovice() {
            return novice;
        }
    }
}
