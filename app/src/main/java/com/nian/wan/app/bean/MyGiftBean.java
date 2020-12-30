package com.nian.wan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public class MyGiftBean {

    /**
     * game_name : 大主宰aaa
     * icon : http://h5sy.ceshi.vlcms.com/Uploads/Picture/2018-09-14/5b9b70676d9e8.jpg
     * game_id : 50
     * gbnum : 2
     * sdk_version : 3
     * play_detail_url : /app.php/GamePage/detail/game_id/50.html
     * gblist : [{"id":"14","game_id":"50","game_name":"大主宰aaa","server_id":"","server_name":"","gift_id":"25","gift_name":"新手礼包ab","status":"0","novice":"11","user_id":"28","user_account":"zhaojingks","user_nickname":"","create_time":"1536736983","desribe":"礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容","icon":"75","end_time":"1538440800","relation_game_id":"50","relation_game_name":"大主宰aaa","giftbag_version":"3","gift_detail_url":"/app.php/Gift/giftdetail/gift_id/25.html"},{"id":"8","game_id":"50","game_name":"大主宰aaa","server_id":"","server_name":"","gift_id":"30","gift_name":"一级礼包11ab","status":"0","novice":"cf0626425bbdb89c1e01790cc452d0","user_id":"28","user_account":"zhaojingks","user_nickname":"","create_time":"1536649849","desribe":"礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容","icon":"75","end_time":"1538485200","relation_game_id":"50","relation_game_name":"大主宰aaa","giftbag_version":"3","gift_detail_url":"/app.php/Gift/giftdetail/gift_id/30.html"}]
     */

    private String game_name;
    private String icon;
    private String game_id;
    private String gbnum;
    private int sdk_version;
    private String play_detail_url;
    private List<GblistBean> gblist;

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getGbnum() {
        return gbnum;
    }

    public void setGbnum(String gbnum) {
        this.gbnum = gbnum;
    }

    public int getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(int sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getPlay_detail_url() {
        return play_detail_url;
    }

    public void setPlay_detail_url(String play_detail_url) {
        this.play_detail_url = play_detail_url;
    }

    public List<GblistBean> getGblist() {
        return gblist;
    }

    public void setGblist(List<GblistBean> gblist) {
        this.gblist = gblist;
    }

    public static class GblistBean {
        /**
         * id : 14
         * game_id : 50
         * game_name : 大主宰aaa
         * server_id :
         * server_name :
         * gift_id : 25
         * gift_name : 新手礼包ab
         * status : 0
         * novice : 11
         * user_id : 28
         * user_account : zhaojingks
         * user_nickname :
         * create_time : 1536736983
         * desribe : 礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容礼包内容
         * icon : 75
         * end_time : 1538440800
         * relation_game_id : 50
         * relation_game_name : 大主宰aaa
         * giftbag_version : 3
         * gift_detail_url : /app.php/Gift/giftdetail/gift_id/25.html
         */

        private String id;
        private String game_id;
        private String game_name;
        private String server_id;
        private String server_name;
        private String gift_id;
        private String gift_name;
        private String status;
        private String novice;
        private String user_id;
        private String user_account;
        private String user_nickname;
        private String create_time;
        private String desribe;
        private String icon;
        private String end_time;
        private String relation_game_id;
        private String relation_game_name;
        private String giftbag_version;
        private String gift_detail_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getServer_id() {
            return server_id;
        }

        public void setServer_id(String server_id) {
            this.server_id = server_id;
        }

        public String getServer_name() {
            return server_name;
        }

        public void setServer_name(String server_name) {
            this.server_name = server_name;
        }

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }

        public String getGift_name() {
            return gift_name;
        }

        public void setGift_name(String gift_name) {
            this.gift_name = gift_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNovice() {
            return novice;
        }

        public void setNovice(String novice) {
            this.novice = novice;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_account() {
            return user_account;
        }

        public void setUser_account(String user_account) {
            this.user_account = user_account;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDesribe() {
            return desribe;
        }

        public void setDesribe(String desribe) {
            this.desribe = desribe;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getRelation_game_id() {
            return relation_game_id;
        }

        public void setRelation_game_id(String relation_game_id) {
            this.relation_game_id = relation_game_id;
        }

        public String getRelation_game_name() {
            return relation_game_name;
        }

        public void setRelation_game_name(String relation_game_name) {
            this.relation_game_name = relation_game_name;
        }

        public String getGiftbag_version() {
            return giftbag_version;
        }

        public void setGiftbag_version(String giftbag_version) {
            this.giftbag_version = giftbag_version;
        }

        public String getGift_detail_url() {
            return gift_detail_url;
        }

        public void setGift_detail_url(String gift_detail_url) {
            this.gift_detail_url = gift_detail_url;
        }
    }
}
