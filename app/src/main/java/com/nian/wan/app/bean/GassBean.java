package com.nian.wan.app.bean;

/**
 * Created by Administrator on 2017/5/15.
 * 猜你喜欢和大家都在玩（随机四条数据）
 */

public class GassBean {

    /**
     * game_id : 10
     * icon : http://h5sy.ceshi.vlcms.com/Uploads/Picture/2018-07-13/5b484355a5e12.jpg
     * game_name : 武林外传1
     * game_type_id : 2
     * sdk_version : 2
     */

    private String game_id;
    private String icon;
    private String game_name;
    private String game_type_id;
    private String sdk_version;

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getGame_type_id() {
        return game_type_id;
    }

    public void setGame_type_id(String game_type_id) {
        this.game_type_id = game_type_id;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }
}
