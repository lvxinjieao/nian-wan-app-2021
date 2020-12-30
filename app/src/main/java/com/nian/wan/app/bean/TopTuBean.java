package com.nian.wan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class TopTuBean {

    /**
     * msg : [{"data":"http://newh5.vlcms.com/Uploads/Picture/2017-05-15/591909dd12372.jpg","title":"23423","url":"4324"}]
     * return_code : 1
     * status : 1
     */
    private List<MsgEntity> msg;
    private int return_code;
    private int status;

    public void setMsg(List<MsgEntity> msg) {
        this.msg = msg;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MsgEntity> getMsg() {
        return msg;
    }

    public int getReturn_code() {
        return return_code;
    }

    public int getStatus() {
        return status;
    }

    public class MsgEntity {
        /**
         * data : http://newh5.vlcms.com/Uploads/Picture/2017-05-15/591909dd12372.jpg
         * title : 23423
         * url : 4324
         */
        private String data;
        private String title;
        private String url;

        public void setData(String data) {
            this.data = data;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getData() {
            return data;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
    }
}
