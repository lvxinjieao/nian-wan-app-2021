package com.nian.wan.app.bean;


import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/12/21
 * @Description: 用户余额实体类
 * @Modify By:
 * @ModifyDate:
 */
public class UserBalanceBean {


    /**
     * shop_point : 10497970
     * balance : 20.00
     * binddata : [{"id":"167","game_name":"我的世界","icon":"http://testh55.vlcms.com/Uploads/Picture/2017-11-23/5a16750d1e9dd.png","bind_balance":"94.09","play_url":"http://testh55.vlcms.com/app.php?s=/Game/open_game/game_id/167.html","play_detail_url":"/app.php?s=/GamePage/detail/game_id/167.html"},{"id":"70","game_name":"剑斩江湖","icon":"http://testh55.vlcms.com/Uploads/Picture/2017-11-07/5a011fa4ec7b6.png","bind_balance":"201.00","play_url":"http://testh55.vlcms.com/app.php?s=/Game/open_game/game_id/70.html","play_detail_url":"/app.php?s=/GamePage/detail/game_id/70.html"}]
     */

    private String shop_point;
    private String balance;
    private List<BinddataBean> binddata;

    public String getShop_point() {
        return shop_point;
    }

    public void setShop_point(String shop_point) {
        this.shop_point = shop_point;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<BinddataBean> getBinddata() {
        return binddata;
    }

    public void setBinddata(List<BinddataBean> binddata) {
        this.binddata = binddata;
    }

    public static class BinddataBean {
        /**
         * id : 167
         * game_name : 我的世界
         * icon : http://testh55.vlcms.com/Uploads/Picture/2017-11-23/5a16750d1e9dd.png
         * bind_balance : 94.09
         * play_url : http://testh55.vlcms.com/app.php?s=/Game/open_game/game_id/167.html
         * play_detail_url : /app.php?s=/GamePage/detail/game_id/167.html
         */

        private String id;
        private String game_name;
        private String icon;
        private String bind_balance;
        private String play_url;
        private String play_detail_url;

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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getBind_balance() {
            return bind_balance;
        }

        public void setBind_balance(String bind_balance) {
            this.bind_balance = bind_balance;
        }

        public String getPlay_url() {
            return play_url;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }

        public String getPlay_detail_url() {
            return play_detail_url;
        }

        public void setPlay_detail_url(String play_detail_url) {
            this.play_detail_url = play_detail_url;
        }
    }
}
