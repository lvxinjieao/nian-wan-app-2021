package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/27
 * @Description: 最近在玩
 * @Modify By:
 * @ModifyDate:
 */
public class RecentlyPlayBean {
    /**
     * id : 6
     * game_name : 武林外传1
     * icon : http://h5sy.ceshi.vlcms.com/Uploads/Picture/2018-07-13/5b484355a5e12.jpg
     * bind_balance : 0.00
     * sdk_version : 1
     * play_url : http://h5sy.ceshi.vlcms.com/mobile.php?s=Game/open_game/game_id/6
     * play_detail_url : http://h5sy.ceshi.vlcms.com/mobile.php?s=Game/detail/game_id/6
     */

    private String id;
    private String game_name;
    private String icon;
    private String bind_balance;
    private String sdk_version;
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

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
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
