package com.nian.wan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class GameGiftBeam {

    /**
     * msg : {"gift":[{"all":14,"gift_id":"3","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","end_time":"0","play_count":"6","giftbag_type":"2","game_type_name":"战略","start_time":"1492759980","game_name":"街球联盟","wei":3,"desribe":"金币*10万、中级经验药水*15、钻石*200","game_id":"1","giftbag_name":"五一礼包"},{"all":3,"gift_id":"21","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","end_time":"0","play_count":"6","giftbag_type":"1","game_type_name":"战略","start_time":"1494473280","game_name":"街球联盟","wei":1,"desribe":"大礼包","game_id":"1","giftbag_name":"礼包5"}],"game":{"game_name":"街球联盟","game_type_name":"战略","is_collect":0,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","play_num":18,"gift_count":"2"}}
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
         * gift : [{"all":14,"gift_id":"3","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","end_time":"0","play_count":"6","giftbag_type":"2","game_type_name":"战略","start_time":"1492759980","game_name":"街球联盟","wei":3,"desribe":"金币*10万、中级经验药水*15、钻石*200","game_id":"1","giftbag_name":"五一礼包"},{"all":3,"gift_id":"21","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","end_time":"0","play_count":"6","giftbag_type":"1","game_type_name":"战略","start_time":"1494473280","game_name":"街球联盟","wei":1,"desribe":"大礼包","game_id":"1","giftbag_name":"礼包5"}]
         * game : {"game_name":"街球联盟","game_type_name":"战略","is_collect":0,"icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png","play_num":18,"gift_count":"2"}
         */
        private List<GiftEntity> gift;
        private GameEntity game;

        public void setGift(List<GiftEntity> gift) {
            this.gift = gift;
        }

        public void setGame(GameEntity game) {
            this.game = game;
        }

        public List<GiftEntity> getGift() {
            return gift;
        }

        public GameEntity getGame() {
            return game;
        }

        public class GiftEntity {
            /**
             * all : 14
             * gift_id : 3
             * icon : http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png
             * end_time : 0
             * play_count : 6
             * giftbag_type : 2
             * game_type_name : 战略
             * start_time : 1492759980
             * game_name : 街球联盟
             * wei : 3
             * desribe : 金币*10万、中级经验药水*15、钻石*200
             * game_id : 1
             * giftbag_name : 五一礼包
             */
            private int all;
            private String gift_id;
            private String icon;
            private String end_time;
            private String play_count;
            private String giftbag_type;
            private String game_type_name;
            private String start_time;
            private String game_name;
            private int wei;
            private String desribe;
            private String game_id;
            private String giftbag_name;

            public void setAll(int all) {
                this.all = all;
            }

            public void setGift_id(String gift_id) {
                this.gift_id = gift_id;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public void setPlay_count(String play_count) {
                this.play_count = play_count;
            }

            public void setGiftbag_type(String giftbag_type) {
                this.giftbag_type = giftbag_type;
            }

            public void setGame_type_name(String game_type_name) {
                this.game_type_name = game_type_name;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public void setGame_name(String game_name) {
                this.game_name = game_name;
            }

            public void setWei(int wei) {
                this.wei = wei;
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

            public int getAll() {
                return all;
            }

            public String getGift_id() {
                return gift_id;
            }

            public String getIcon() {
                return icon;
            }

            public String getEnd_time() {
                return end_time;
            }

            public String getPlay_count() {
                return play_count;
            }

            public String getGiftbag_type() {
                return giftbag_type;
            }

            public String getGame_type_name() {
                return game_type_name;
            }

            public String getStart_time() {
                return start_time;
            }

            public String getGame_name() {
                return game_name;
            }

            public int getWei() {
                return wei;
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
        }

        public class GameEntity {
            /**
             * game_name : 街球联盟
             * game_type_name : 战略
             * is_collect : 0
             * icon : http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f99a39be973.png
             * play_num : 18
             * gift_count : 2
             */
            private String game_name;
            private String game_type_name;
            private int is_collect;
            private String icon;
            private int play_num;
            private String gift_count;

            public void setGame_name(String game_name) {
                this.game_name = game_name;
            }

            public void setGame_type_name(String game_type_name) {
                this.game_type_name = game_type_name;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setPlay_num(int play_num) {
                this.play_num = play_num;
            }

            public void setGift_count(String gift_count) {
                this.gift_count = gift_count;
            }

            public String getGame_name() {
                return game_name;
            }

            public String getGame_type_name() {
                return game_type_name;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public String getIcon() {
                return icon;
            }

            public int getPlay_num() {
                return play_num;
            }

            public String getGift_count() {
                return gift_count;
            }
        }
    }
}
