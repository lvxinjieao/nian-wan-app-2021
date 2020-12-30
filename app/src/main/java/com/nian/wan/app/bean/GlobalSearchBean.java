package com.nian.wan.app.bean;

import java.util.List;

/**
 * @Author: XiaYuShi
 * @Date: 2017/12/18
 * @Description:
 * @Modify By:
 * @ModifyDate:
 */
public class GlobalSearchBean {

    private List<GameBean> game;
    private List<SamllgameBean> samllgame;
    private List<ArticleBean> article;
    private List<GiftBean> gift;

    public List<GameBean> getGame() {
        return game;
    }

    public void setGame(List<GameBean> game) {
        this.game = game;
    }

    public List<SamllgameBean> getSamllgame() {
        return samllgame;
    }

    public void setSamllgame(List<SamllgameBean> samllgame) {
        this.samllgame = samllgame;
    }

    public List<ArticleBean> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleBean> article) {
        this.article = article;
    }

    public List<GiftBean> getGift() {
        return gift;
    }

    public void setGift(List<GiftBean> gift) {
        this.gift = gift;
    }

    public static class GameBean {
        /**
         * icon : http://testh55.vlcms.com/Uploads/Picture/2018-04-03/5ac2e5c3bfba5.png
         * cover : http://testh55.vlcms.com/Uploads/Picture/2018-04-03/5ac2e5cd9e618.jpg
         * game_name : 猎魔传说
         * id : 1
         * game_type_id : 1
         * features : 全职业AOE手游，2017倾情奉献！！
         * game_type_name : 动作的
         * game_score : 0
         * play_num : 616
         * play_detail_url : /app.php?s=/GamePage/detail/game_id/1.html
         * play_url : http://testh55.vlcms.com/mobile.php?s=Game/open_game/game_id/1
         */

        private String icon;
        private String cover;
        private String game_name;
        private String id;
        private String game_type_id;
        private String features;
        private String game_type_name;
        private int game_score;
        private int play_num;
        private String play_detail_url;
        private String play_url;
        private int xia_status;
        private String ratio;
        private int sdk_version;
        private String game_size;

        public String getGame_size() {
            return game_size;
        }

        public void setGame_size(String game_size) {
            this.game_size = game_size;
        }

        public int getSdk_version() {
            return sdk_version;
        }

        public void setSdk_version(int sdk_version) {
            this.sdk_version = sdk_version;
        }


        public int getXia_status() {
            return xia_status;
        }

        public void setXia_status(int xia_status) {
            this.xia_status = xia_status;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

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

    public static class SamllgameBean {
        /**
         * id : 5
         * game_name : 猎魔传说
         * scan_num : 0
         * icon : http://testh55.vlcms.com/Uploads/Picture/2018-04-03/5ac36fa8555eb.jpg
         * qrcodeurl : https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGC7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyc3NmRmNxYnBjZmwxMjlzX05yY2sAAgQJ4RhbAwSAOyYA
         */

        private String id;
        private String game_name;
        private String scan_num;
        private String icon;
        private String qrcodeurl;

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

        public String getScan_num() {
            return scan_num;
        }

        public void setScan_num(String scan_num) {
            this.scan_num = scan_num;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getQrcodeurl() {
            return qrcodeurl;
        }

        public void setQrcodeurl(String qrcodeurl) {
            this.qrcodeurl = qrcodeurl;
        }
    }

    public static class ArticleBean {
        /**
         * id : 23
         * title : 测试公告三
         * content : 测试公告三测试公告三测试公告三
         * cover_id :
         * description :
         * belong_game : 猎魔传说
         * hdtype : 活动
         * article_detail_url : http://testh55.vlcms.com/mobile.php/Article/detail/id/23
         */

        private String id;
        private String title;
        private String content;
        private String cover_id;
        private String description;
        private String belong_game;
        private String hdtype;
        private String article_detail_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCover_id() {
            return cover_id;
        }

        public void setCover_id(String cover_id) {
            this.cover_id = cover_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBelong_game() {
            return belong_game;
        }

        public void setBelong_game(String belong_game) {
            this.belong_game = belong_game;
        }

        public String getHdtype() {
            return hdtype;
        }

        public void setHdtype(String hdtype) {
            this.hdtype = hdtype;
        }

        public String getArticle_detail_url() {
            return article_detail_url;
        }

        public void setArticle_detail_url(String article_detail_url) {
            this.article_detail_url = article_detail_url;
        }
    }

    public static class GiftBean {
        /**
         * game_id : 1
         * game_name : 猎魔传说
         * gift_id : 26
         * start_time : 1528276020
         * end_time : 0
         * giftbag_name : 超级礼包
         * desribe : 大礼包
         * icon : http://testh55.vlcms.com/Uploads/Picture/2018-04-03/5ac2e5c3bfba5.png
         * gift_detail_url : /app.php?s=/Gift/giftdetail/gift_id/26.html
         * novice_all : 1
         * novice_surplus : 1
         * novice_rate : 100
         * play_detail_url : /app.php?s=/GamePage/detail/game_id/1.html
         * received : 0
         */

        private String game_id;
        private String game_name;
        private String gift_id;
        private String start_time;
        private String end_time;
        private String giftbag_name;
        private String desribe;
        private String icon;
        private String gift_detail_url;
        private int novice_all;
        private int novice_surplus;
        private int novice_rate;
        private String play_detail_url;
        private int received;

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

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getGiftbag_name() {
            return giftbag_name;
        }

        public void setGiftbag_name(String giftbag_name) {
            this.giftbag_name = giftbag_name;
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

        public int getNovice_rate() {
            return novice_rate;
        }

        public void setNovice_rate(int novice_rate) {
            this.novice_rate = novice_rate;
        }

        public String getPlay_detail_url() {
            return play_detail_url;
        }

        public void setPlay_detail_url(String play_detail_url) {
            this.play_detail_url = play_detail_url;
        }

        public int getReceived() {
            return received;
        }

        public void setReceived(int received) {
            this.received = received;
        }
    }
}
