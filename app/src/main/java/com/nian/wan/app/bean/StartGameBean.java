package com.nian.wan.app.bean;

/**
 * Created by Administrator on 2017/5/11.
 */

public class StartGameBean {

    /**
     * msg : {"url":"http://newh5.vlcms.com/mobile.php?s=/Game/open_game/game_id/1/token/MTQ5NTA4ODkwM5ndsGqaZY_biWidzXnOn52EibaXes7am5G5tJ6aZbimmKevb4Cff7SJsHrch7tgrZqi1N16ztqbmbq0mpyJr5qD0ZtqmXqxmw.html"}
     * return_code : 1
     * status : 1
     */
    private MsgEntity msg;
    private int return_code;
    private int status;

    public void setMsg(MsgEntity msg) {
        this.msg = msg;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MsgEntity getMsg() {
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
         * url : http://newh5.vlcms.com/mobile.php?s=/Game/open_game/game_id/1/token/MTQ5NTA4ODkwM5ndsGqaZY_biWidzXnOn52EibaXes7am5G5tJ6aZbimmKevb4Cff7SJsHrch7tgrZqi1N16ztqbmbq0mpyJr5qD0ZtqmXqxmw.html
         */
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
