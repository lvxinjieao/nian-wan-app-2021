package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/12/13
 * @Description: 游戏内礼包
 * @Modify By:
 * @ModifyDate:
 */
public class IntoGameGiftBean {


    private String game_id;
    private String game_name;
    private String gift_id;
    private String start_time;
    private String end_time;
    private String giftbag_name;
    private String desribe;
    private String icon;
    private int novice_all;
    private int novice_surplus;
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
