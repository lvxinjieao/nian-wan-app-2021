package com.nian.wan.app.bean;

import java.util.List;

/**
 * 绑币
 * Created by Administrator on 2017/5/8.
 */

public class BangBiBean {

    /**
     * status : 1
     * return_code : 1
     * msg : [{"id":"1","game_name":"街球联盟","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"2","game_name":"水晶之歌","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"3","game_name":"妖萌战姬","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"4","game_name":"地下城与冒险","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"5","game_name":"剑仙sdk版","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"6","game_name":"剑仙无sdk","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"7","game_name":"猎魔传说","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"8","game_name":"宠物小精灵","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"9","game_name":"少女咖啡枪","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"},{"id":"10","game_name":"挂挂三国","bind_balance":null,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png"}]
     */

    private int status;
    private int return_code;
    private List<MsgBean> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * id : 1
         * game_name : 街球联盟
         * bind_balance : null
         * icon : http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png
         */

        private String id;
        private String game_name;
        private String bind_balance;
        private String icon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getBind_balance() {
            return bind_balance;
        }

        public void setBind_balance(String bind_balance) {
            this.bind_balance = bind_balance;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
