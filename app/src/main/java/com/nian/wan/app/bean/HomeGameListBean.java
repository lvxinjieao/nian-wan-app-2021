package com.nian.wan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class HomeGameListBean {

    /**
     * msg : [{"cover":"http://newh5.vlcms.com","game_name":"街球联盟","game_type_name":"战略","features":"篮球超级巨星、\u201c闪电侠\u201d韦德唯一代言手游！","screen_type":"2","recommend_status":"1","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","id":"1","play_num":23},{"cover":"http://newh5.vlcms.com","game_name":"妖萌战姬","game_type_name":"角色扮演","features":"《妖萌战姬》是一款画面非常精美清新，强烈日系立绘风格的美少女冒险类rpg游戏","screen_type":"2","recommend_status":"1","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99ac80e28f.png","id":"3","play_num":15},{"cover":"http://newh5.vlcms.com","game_name":"剑仙无sdk","game_type_name":"战略","features":"专属王者的竞技手游","screen_type":"2","recommend_status":"1","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9c8e00c6ac.png","id":"6","play_num":39},{"cover":"http://newh5.vlcms.com","game_name":"猎魔传说","game_type_name":"动作冒险","features":"全职业AOE手游\u2014\u2014《猎魔传说》2016倾情奉献","screen_type":"2","recommend_status":"1","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-24/58fdba9d1a21a.png","id":"7","play_num":13}]
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
         * cover : http://newh5.vlcms.com
         * game_name : 街球联盟
         * game_type_name : 战略
         * features : 篮球超级巨星、“闪电侠”韦德唯一代言手游！
         * screen_type : 2
         * recommend_status : 1
         * icon : http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png
         * id : 1
         * play_num : 23
         */
        private String cover;
        private String game_name;
        private String game_type_name;
        private String features;
        private String screen_type;
        private String recommend_status;
        private String icon;
        private String id;
        private int play_num;

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public void setGame_type_name(String game_type_name) {
            this.game_type_name = game_type_name;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public void setScreen_type(String screen_type) {
            this.screen_type = screen_type;
        }

        public void setRecommend_status(String recommend_status) {
            this.recommend_status = recommend_status;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPlay_num(int play_num) {
            this.play_num = play_num;
        }

        public String getCover() {
            return cover;
        }

        public String getGame_name() {
            return game_name;
        }

        public String getGame_type_name() {
            return game_type_name;
        }

        public String getFeatures() {
            return features;
        }

        public String getScreen_type() {
            return screen_type;
        }

        public String getRecommend_status() {
            return recommend_status;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public int getPlay_num() {
            return play_num;
        }
    }
}
