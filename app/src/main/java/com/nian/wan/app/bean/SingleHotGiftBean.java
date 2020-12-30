package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/12/26
 * @Description: 我的礼包-热门礼包实体类
 * @Modify By:
 * @ModifyDate:
 */
public class SingleHotGiftBean {

    /**
     * game_id : 6
     * game_name : 武林外传1(安卓版)
     * relation_game_name : 武林外传1
     * relation_game_id : 6
     * gift_id : 37
     * start_time : 1536573060
     * end_time : 1537718400
     * giftbag_name : 第三礼包
     * desribe :
     * icon : http://h5sy.ceshi.vlcms.com/Uploads/Picture/2018-07-13/5b484355a5e12.jpg
     * sdk_version : 1
     * gift_detail_url : /app.php/Gift/giftdetail/gift_id/37.html
     * novice_all : 3
     * novice_surplus : 2
     * novice_rate : 66
     * play_detail_url : /app.php/GamePage/detail/game_id/6.html
     */

    private String game_id;
    private String game_name;
    private String relation_game_name;
    private String relation_game_id;
    private String gift_id;
    private String start_time;
    private String end_time;
    private String giftbag_name;
    private String desribe;
    private String icon;
    private String sdk_version;
    private String gift_detail_url;
    private int novice_all;
    private int novice_surplus;
    private int novice_rate;
    private String play_detail_url;

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

    public String getRelation_game_name() {
        return relation_game_name;
    }

    public void setRelation_game_name(String relation_game_name) {
        this.relation_game_name = relation_game_name;
    }

    public String getRelation_game_id() {
        return relation_game_id;
    }

    public void setRelation_game_id(String relation_game_id) {
        this.relation_game_id = relation_game_id;
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

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
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
}
