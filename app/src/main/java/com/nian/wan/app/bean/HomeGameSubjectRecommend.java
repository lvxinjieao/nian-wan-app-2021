package com.nian.wan.app.bean;

import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/12/4
 * @Description: 首页游戏分类推荐实体类
 * @Modify By:
 * @ModifyDate:
 */
public class HomeGameSubjectRecommend {

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * icon : http://testh55.vlcms.com/Uploads/Picture/2017-11-07/5a011fa4ec7b6.png
         * cover : http://testh55.vlcms.com/Uploads/Picture/2017-10-23/59edbd3d31063.jpg
         * game_name : 剑斩江湖
         * id : 70
         * game_type_id : 1
         * features : 懒人首选，零负担挂机疯狂来袭
         * gift_id : 11
         * game_type_name : 角色扮演
         * game_score : 0
         * play_num : 1001
         * play_detail_url : /app.php?s=/GamePage/detail/game_id/70.html
         * play_url : http://testh55.vlcms.com/app.php?s=/Mobile/Game/open_game/game_id/70.html
         */

        private String icon;
        private String cover;
        private String game_name;
        private String id;
        private String game_type_id;
        private String features;
        private String gift_id;
        private String game_type_name;
        private int game_score;
        private int play_num;
        private String play_detail_url;
        private String play_url;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGame_type_id() {
            return game_type_id;
        }

        public void setGame_type_id(String game_type_id) {
            this.game_type_id = game_type_id;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }

        public String getGame_type_name() {
            return game_type_name;
        }

        public void setGame_type_name(String game_type_name) {
            this.game_type_name = game_type_name;
        }

        public int getGame_score() {
            return game_score;
        }

        public void setGame_score(int game_score) {
            this.game_score = game_score;
        }

        public int getPlay_num() {
            return play_num;
        }

        public void setPlay_num(int play_num) {
            this.play_num = play_num;
        }

        public String getPlay_detail_url() {
            return play_detail_url;
        }

        public void setPlay_detail_url(String play_detail_url) {
            this.play_detail_url = play_detail_url;
        }

        public String getPlay_url() {
            return play_url;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }
    }
}
