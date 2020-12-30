package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/27
 * @Description:
 * @Modify By: 用户信息实体类
 * @ModifyDate:
 */
public class UserInfoBean {


    /**
     * code : 200
     * msg : 登录成功
     * data : {"user_id":"100015","account":"yyhyyh","head_icon":"http://testh55.vlcms.com/Public/Home/images/banner1-am.png","token":"MTUxNjAwODUyM5ndsGqaZY_biWidzXnOn52EibrOfaitmpGUtKqciZdhfc7VopyLpdGWjp3RedJgcQ"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 100015
         * account : yyhyyh
         * head_icon : http://testh55.vlcms.com/Public/Home/images/banner1-am.png
         * token : MTUxNjAwODUyM5ndsGqaZY_biWidzXnOn52EibrOfaitmpGUtKqciZdhfc7VopyLpdGWjp3RedJgcQ
         */

        private String user_id;
        private String account;
        private String head_icon;
        private String token;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getHead_icon() {
            return head_icon;
        }

        public void setHead_icon(String head_icon) {
            this.head_icon = head_icon;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
