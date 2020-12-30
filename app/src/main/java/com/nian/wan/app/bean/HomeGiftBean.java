package com.nian.wan.app.bean;

import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/29
 * @Description: 首页礼包实体类
 * @Modify By:
 * @ModifyDate:
 */
public class HomeGiftBean {

    /**
     * game_name : 宠物精灵
     * icon : http://testh55.vlcms.com/Uploads/Picture/2017-11-14/5a0aa81d302d0.png
     * game_id : 67
     * gbnum : 1
     * play_detail_url : /app.php?s=/GamePage/detail/game_id/67.html
     * gblist : [{"game_id":"67","giftbag_name":"集结礼包","gift_id":"4","desribe":"稀有碎片盒子*30、大体力瓶*2、普通便当*5","gift_detail_url":"/app.php?s=/Gift/giftdetail/gift_id/4.html","novice_all":989,"novice_surplus":981,"received":1}]
     */

    private String game_name;
    private String icon;
    private String game_id;
    private String gbnum;
    private String play_detail_url;
    private List<GblistBean> gblist;

    public boolean isShowAll = false;

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
         * game_id : 67
         * giftbag_name : 集结礼包
         * gift_id : 4
         * desribe : 稀有碎片盒子*30、大体力瓶*2、普通便当*5
         * gift_detail_url : /app.php?s=/Gift/giftdetail/gift_id/4.html
         * novice_all : 989
         * novice_surplus : 981
         * received : 1
         */

        private String game_id;
        private String giftbag_name;
        private String gift_id;
        private String desribe;
        private String gift_detail_url;
        private int novice_all;
        private int novice_surplus;
        private int received;

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public String getGiftbag_name() {
            return giftbag_name;
        }

        public void setGiftbag_name(String giftbag_name) {
            this.giftbag_name = giftbag_name;
        }

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }

        public String getDesribe() {
            return desribe;
        }

        public void setDesribe(String desribe) {
            this.desribe = desribe;
        }

        public String getGift_detail_url() {
            return gift_detail_url;
        }

        public void setGift_detail_url(String gift_detail_url) {
            this.gift_detail_url = gift_detail_url;
        }

        public int getNovice_all() {
            return novice_all;
        }

        public void setNovice_all(int novice_all) {
            this.novice_all = novice_all;
        }

        public int getNovice_surplus() {
            return novice_surplus;
        }

        public void setNovice_surplus(int novice_surplus) {
            this.novice_surplus = novice_surplus;
        }

        public int getReceived() {
            return received;
        }

        public void setReceived(int received) {
            this.received = received;
        }
    }
}
