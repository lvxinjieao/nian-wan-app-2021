package com.nian.wan.app.bean;

/**
 * 描述：商城规则
 * 作者：钮家齐
 * 时间: 2018-02-04 15:40
 */
public class RulesBean {

    /**
     * id : 6
     * title : 积分如何获取呢?
     * content : 1、每日签到 进入游戏中心“个人”界面或者积分商城界面，点击签到，即可获得对应的积分奖励。 2、游戏充值 在溪谷游戏平台任意游戏内每充值1元即可获得10积分。 3、显示活动 每隔一段时间都会开放积分获取的限时活动，小伙伴们别错过哦~
     */

    private String id;
    private String title;
    private String content;

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
}
