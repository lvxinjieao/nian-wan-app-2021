package com.nian.wan.app.bean;

import java.util.ArrayList;

/**
 * 我的收藏
 * Created by Administrator on 2017/5/9.
 */

public class ShouCang {

    /**
     * status : 1
     * return_code : 1
     * msg : {"one_month":[{"id":"7","game_id":"7","game_name":"猎魔传说","game_type_name":"动作冒险","game_type_id":"4","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-24/58fdba9d1a21a.png","play_num":"7"},{"id":"6","game_id":"5","game_name":"剑仙sdk版","game_type_name":"战略","game_type_id":"1","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9b149059b6.png","play_num":"56"}],"three_month":[{"id":"2","game_id":"6","game_name":"剑仙无sdk","game_type_name":"战略","game_type_id":"1","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9c8e00c6ac.png","play_num":"31"}],"before_three_month":[{"id":"1","game_id":"5","game_name":"剑仙sdk版","game_type_name":"战略","game_type_id":"1","icon":"http://newh5.vlcms.com/Uploads/Picture/2017-04-21/58f9b149059b6.png","play_num":"56"}]}
     */

    private int status;
    private int return_code;
    private MsgBean msg;

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

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        private ArrayList<OneMonthBean> one_month;
        private ArrayList<OneMonthBean> three_month;
        private ArrayList<OneMonthBean> before_three_month;

        public ArrayList<OneMonthBean> getOne_month() {
            return one_month;
        }

        public void setOne_month(ArrayList<OneMonthBean> one_month) {
            this.one_month = one_month;
        }

        public ArrayList<OneMonthBean> getThree_month() {
            return three_month;
        }

        public void setThree_month(ArrayList<OneMonthBean> three_month) {
            this.three_month = three_month;
        }

        public ArrayList<OneMonthBean> getBefore_three_month() {
            return before_three_month;
        }

        public void setBefore_three_month(ArrayList<OneMonthBean> before_three_month) {
            this.before_three_month = before_three_month;
        }

        public static class OneMonthBean {
            /**
             * id : 7
             * game_id : 7
             * game_name : 猎魔传说
             * game_type_name : 动作冒险
             * game_type_id : 4
             * icon : http://newh5.vlcms.com/Uploads/Picture/2017-04-24/58fdba9d1a21a.png
             * play_num : 7
             */

            private String id;
            private String game_id;
            private String game_name;
            private String game_type_name;
            private String game_type_id;
            private String icon;
            private String play_num;

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

            public String getGame_type_name() {
                return game_type_name;
            }

            public void setGame_type_name(String game_type_name) {
                this.game_type_name = game_type_name;
            }

            public String getGame_type_id() {
                return game_type_id;
            }

            public void setGame_type_id(String game_type_id) {
                this.game_type_id = game_type_id;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPlay_num() {
                return play_num;
            }

            public void setPlay_num(String play_num) {
                this.play_num = play_num;
            }
        }
    }
}
