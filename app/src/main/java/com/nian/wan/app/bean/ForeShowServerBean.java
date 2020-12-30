package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description: 预告开服实体类
 * @Modify By:
 * @ModifyDate:
 */
public class ForeShowServerBean {

        /**
         * server_id : 10
         * game_id : 163
         * game_name : 百战沙城
         * icon : http://testh55.vlcms.com/Uploads/Picture/2017-11-16/5a0d46dcd7c3f.png
         * server_name : 142区
         * start_time : 1511884800
         * start_date : 明日00:00
         * play_url : http://testh55.vlcms.com/app.php?s=/Mobile/Game/open_game/game_id/163.html
         * isnotice : 0
         */

        private String server_id;
        private String game_id;
        private String game_name;
        private String icon;
        private String server_name;
        private String start_time;
        private String start_date;
        private String play_url;
        private int isnotice;

        public String getServer_id() {
            return server_id;
        }

        public void setServer_id(String server_id) {
            this.server_id = server_id;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getServer_name() {
            return server_name;
        }

        public void setServer_name(String server_name) {
            this.server_name = server_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getPlay_url() {
            return play_url;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }

        public int getIsnotice() {
            return isnotice;
        }

        public void setIsnotice(int isnotice) {
            this.isnotice = isnotice;
        }
}
