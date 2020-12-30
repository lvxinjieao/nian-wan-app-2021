package com.nian.wan.app.bean;

import java.io.Serializable;

public class GameGroupBean implements Serializable {

    private String icon;                    //游戏图标
    private String cover;                   //游戏封面
    private String relation_game_id;        //关联游戏ID
    private String game_name;               //游戏名称
    private String features;                //特色 专题
    private int game_score;                 //游戏评分
    private String dow_status;              //下载状态(0:关闭,1:开启)
    private String sdk_version;             //下载状态(0:关闭,1:开启)
    private String pack_name;               //安卓原包包名
    private String game_size;               //游戏大小
    private String discount;                //折扣
    private String ratio;                   //返利比例
    private String isannios;                //关联游戏个数
    private String continue_discount;       //续充
    private String title;                   //活动标题
    private String game_type_name;          //类型名称 角色
    private String real_game_score;         //游戏评分
    private String collect_num;             //收藏次数
    private String play_detail_url;         //游戏详情
    private String play_num;                //游戏玩家人数显示   null 未设置  其他  代表基数
    private String play_url;                //打开游戏
    private int gift_id;                    //礼包
    private int xia_status;                 //是否可以下载


    private String open_server_time;//开服时间（时间戳）
    private String Server_name;//服名称

    public String getOpen_server_time() {
        return open_server_time;
    }

    public void setOpen_server_time(String open_server_time) {
        this.open_server_time = open_server_time;
    }

    public String getServer_name() {
        return Server_name;
    }

    public void setServer_name(String server_name) {
        Server_name = server_name;
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

    public String getRelation_game_id() {
        return relation_game_id;
    }

    public void setRelation_game_id(String relation_game_id) {
        this.relation_game_id = relation_game_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public int getGame_score() {
        return game_score;
    }

    public void setGame_score(int game_score) {
        this.game_score = game_score;
    }

    public String getDow_status() {
        return dow_status;
    }

    public void setDow_status(String dow_status) {
        this.dow_status = dow_status;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getPack_name() {
        return pack_name;
    }

    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }

    public String getGame_size() {
        return game_size;
    }

    public void setGame_size(String game_size) {
        this.game_size = game_size;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getIsannios() {
        return isannios;
    }

    public void setIsannios(String isannios) {
        this.isannios = isannios;
    }

    public String getContinue_discount() {
        return continue_discount;
    }

    public void setContinue_discount(String continue_discount) {
        this.continue_discount = continue_discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGame_type_name() {
        return game_type_name;
    }

    public void setGame_type_name(String game_type_name) {
        this.game_type_name = game_type_name;
    }

    public String getReal_game_score() {
        return real_game_score;
    }

    public void setReal_game_score(String real_game_score) {
        this.real_game_score = real_game_score;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public String getPlay_detail_url() {
        return play_detail_url;
    }

    public void setPlay_detail_url(String play_detail_url) {
        this.play_detail_url = play_detail_url;
    }

    public String getPlay_num() {
        return play_num;
    }

    public void setPlay_num(String play_num) {
        this.play_num = play_num;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public int getGift_id() {
        return gift_id;
    }

    public void setGift_id(int gift_id) {
        this.gift_id = gift_id;
    }

    public int getXia_status() {
        return xia_status;
    }

    public void setXia_status(int xia_status) {
        this.xia_status = xia_status;
    }
}
