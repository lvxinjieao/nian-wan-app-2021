package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/28
 * @Description: 首页热门、最新上架、推荐游戏实体类
 * @Modify By:
 * @ModifyDate:
 */
public class HomeGameBean {

    /**
     * icon : http://testh55.vlcms.com/Uploads/Picture/2017-12-20/5a39fd857abd0.png
     * cover : http://testh55.vlcms.com/Uploads/Picture/2017-12-20/5a39fdbbc4082.jpg
     * game_name : 二蛋
     * id : 208
     * game_type_id : 5
     * features : 最好玩的游戏
     * gift_id : 0
     * play_count : 5034
     * is_unicode : null
     * unicode_num : null
     * game_type_name : 休闲益智
     * game_score : 0
     * play_num : 5034
     * play_detail_url : /app.php?s=/GamePage/detail/game_id/208.html
     * collect_num : 0
     * play_url : http://testh55.vlcms.com/mobile.php?s=Game/open_game/game_id/208
     * game_gift : 0
     */

    private String icon;
    private String cover;
    private String game_name;
    private String id;
    private String game_type_id;
    private String features;
    private String gift_id;
    private String play_count;
    private Object is_unicode;
    private Object unicode_num;
    private String game_type_name;
    private int game_score;
    private int play_num;
    private String play_detail_url;
    private int collect_num;
    private String play_url;
    private int game_gift;

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

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    public Object getIs_unicode() {
        return is_unicode;
    }

    public void setIs_unicode(Object is_unicode) {
        this.is_unicode = is_unicode;
    }

    public Object getUnicode_num() {
        return unicode_num;
    }

    public void setUnicode_num(Object unicode_num) {
        this.unicode_num = unicode_num;
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

    public int getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(int collect_num) {
        this.collect_num = collect_num;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public int getGame_gift() {
        return game_gift;
    }

    public void setGame_gift(int game_gift) {
        this.game_gift = game_gift;
    }
}
