package com.nian.wan.app.bean;

/**
 * Created by Administrator on 2017/5/11.
 */

public class GiftDetBean {

    /**
     * msg : {"server_name":"","call_api":"0","wnovice":2,"create_time":"1494471757","level":null,"tong_server":"0","ip":null,"end_time":"0","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","sort":"0","server_id":"0","game_name":"街球联盟","giftbag_type":"1","start_time":"1494471720","allcount_novice":3,"digest":"","id":"16","desribe":"街球礼包1","game_id":"1","giftbag_name":"街球礼包1","status":"1"}
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
         * server_name :
         * call_api : 0
         * wnovice : 2
         * create_time : 1494471757
         * level : null
         * tong_server : 0
         * ip : null
         * end_time : 0
         * icon : http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png
         * sort : 0
         * server_id : 0
         * game_name : 街球联盟
         * giftbag_type : 1
         * start_time : 1494471720
         * allcount_novice : 3
         * digest :
         * id : 16
         * desribe : 街球礼包1
         * game_id : 1
         * giftbag_name : 街球礼包1
         * status : 1
         */
        private String server_name;
        private String call_api;
        private int wnovice;
        private String create_time;
        private String level;
        private String tong_server;
        private String ip;
        private String end_time;
        private String icon;
        private String sort;
        private String server_id;
        private String game_name;
        private String giftbag_type;
        private String start_time;
        private int allcount_novice;
        private String digest;
        private String id;
        private String desribe;
        private String game_id;
        private String giftbag_name;
        private String status;

        public void setServer_name(String server_name) {
            this.server_name = server_name;
        }

        public void setCall_api(String call_api) {
            this.call_api = call_api;
        }

        public void setWnovice(int wnovice) {
            this.wnovice = wnovice;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setTong_server(String tong_server) {
            this.tong_server = tong_server;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setServer_id(String server_id) {
            this.server_id = server_id;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public void setGiftbag_type(String giftbag_type) {
            this.giftbag_type = giftbag_type;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setAllcount_novice(int allcount_novice) {
            this.allcount_novice = allcount_novice;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setDesribe(String desribe) {
            this.desribe = desribe;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public void setGiftbag_name(String giftbag_name) {
            this.giftbag_name = giftbag_name;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServer_name() {
            return server_name;
        }

        public String getCall_api() {
            return call_api;
        }

        public int getWnovice() {
            return wnovice;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getLevel() {
            return level;
        }

        public String getTong_server() {
            return tong_server;
        }

        public String getIp() {
            return ip;
        }

        public String getEnd_time() {
            return end_time;
        }

        public String getIcon() {
            return icon;
        }

        public String getSort() {
            return sort;
        }

        public String getServer_id() {
            return server_id;
        }

        public String getGame_name() {
            return game_name;
        }

        public String getGiftbag_type() {
            return giftbag_type;
        }

        public String getStart_time() {
            return start_time;
        }

        public int getAllcount_novice() {
            return allcount_novice;
        }

        public String getDigest() {
            return digest;
        }

        public String getId() {
            return id;
        }

        public String getDesribe() {
            return desribe;
        }

        public String getGame_id() {
            return game_id;
        }

        public String getGiftbag_name() {
            return giftbag_name;
        }

        public String getStatus() {
            return status;
        }
    }
}
