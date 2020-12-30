package com.nian.wan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class MyZuJiBean {

    /**
     * msg : [{"cover":"70","game_name":"剑仙sdk版222","features":"剑仙游戏，又贱又仙","game_type_name":"战略","update_time":"2017-05-12","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9b149059b6.png","id":"5","play_num":"0","game_type_id":"1"},{"cover":"82","game_name":"挂挂三国","features":"八种能力截然不同的名将主角，亲身体会吕布、貂蝉等人气将领的三国人生","game_type_name":"战略","update_time":"2017-05-11","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-24/58fdc0f5b5ba9.png","id":"10","play_num":"0","game_type_id":"1"}]
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
         * cover : 70
         * game_name : 剑仙sdk版222
         * features : 剑仙游戏，又贱又仙
         * game_type_name : 战略
         * update_time : 2017-05-12
         * icon : http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9b149059b6.png
         * id : 5
         * play_num : 0
         * game_type_id : 1
         */
        private String cover;
        private String game_name;
        private String features;
        private String game_type_name;
        private String update_time;
        private String icon;
        private String id;
        private String play_num;
        private String game_type_id;

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public void setGame_type_name(String game_type_name) {
            this.game_type_name = game_type_name;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPlay_num(String play_num) {
            this.play_num = play_num;
        }

        public void setGame_type_id(String game_type_id) {
            this.game_type_id = game_type_id;
        }

        public String getCover() {
            return cover;
        }

        public String getGame_name() {
            return game_name;
        }

        public String getFeatures() {
            return features;
        }

        public String getGame_type_name() {
            return game_type_name;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getPlay_num() {
            return play_num;
        }

        public String getGame_type_id() {
            return game_type_id;
        }
    }
}
