package com.nian.wan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SearchGameBean {

    /**
     * msg : [{"cover":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9b149059b6.png","game_type_name":"战略","game_name":"剑仙sdk版222","features":"剑仙游戏，又贱又仙","screen_type":"2","icon":"54","id":"5","play_num":"71"},{"cover":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9c8e00c6ac.png","game_type_name":"战略","game_name":"剑仙无sdk","features":"专属王者的竞技手游","screen_type":"2","icon":"60","id":"6","play_num":"37"}]
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
         * cover : http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9b149059b6.png
         * game_type_name : 战略
         * game_name : 剑仙sdk版222
         * features : 剑仙游戏，又贱又仙
         * screen_type : 2
         * icon : 54
         * id : 5
         * play_num : 71
         */
        private String cover;
        private String game_type_name;
        private String game_name;
        private String features;
        private String screen_type;
        private String icon;
        private String id;
        private String play_num;

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setGame_type_name(String game_type_name) {
            this.game_type_name = game_type_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public void setScreen_type(String screen_type) {
            this.screen_type = screen_type;
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

        public String getCover() {
            return cover;
        }

        public String getGame_type_name() {
            return game_type_name;
        }

        public String getGame_name() {
            return game_name;
        }

        public String getFeatures() {
            return features;
        }

        public String getScreen_type() {
            return screen_type;
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
    }
}
