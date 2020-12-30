package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description: 已开服实体类
 * @Modify By:
 * @ModifyDate:
 */
public class AlreadyOpenServerBean {

    /**
     * game_id : 8
     * game_name : 边境之旅
     * icon : http://testh55.vlcms.com/Uploads/Picture/2018-01-19/5a619148b7119.png
     * server_name : 初出茅庐
     * start_time : 1517562000
     * pastTime : 已开服2天
     * play_detail_url : /app.php?s=/GamePage/detail/game_id/8.html
     * play_url : http://testh55.vlcms.com/mobile.php?s=Game/open_game/game_id/8
     */

    private String game_id;
    private String game_name;
    private String icon;
    private String server_name;
    private String start_time;
    private String pastTime;
    private String play_detail_url;
    private String play_url;
    private int sdk_version;
    private int xia_status;

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

    public String getPastTime() {
        return pastTime;
    }

    public void setPastTime(String pastTime) {
        this.pastTime = pastTime;
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
