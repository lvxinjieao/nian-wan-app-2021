package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/15
 * @Description: 首页活动-公告实体类
 * @Modify By:
 * @ModifyDate:
 */
public class GamePublishBean {

    //公告实体类
    private String publishTitle;
    //公告日期
    private String publishDate;
    //是否已读公告
    private String isReadPublish;

    public GamePublishBean() {
    }

    public GamePublishBean(String publishTitle, String publishDate, String isReadPublish) {
        this.publishTitle = publishTitle;
        this.publishDate = publishDate;
        this.isReadPublish = isReadPublish;
    }

    public String getPublishTitle() {
        return publishTitle;
    }

    public void setPublishTitle(String publishTitle) {
        this.publishTitle = publishTitle;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getIsReadPublish() {
        return isReadPublish;
    }

    public void setIsReadPublish(String isReadPublish) {
        this.isReadPublish = isReadPublish;
    }

    @Override
    public String toString() {
        return "GamePublishBean{" +
                "publishTitle='" + publishTitle + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", isReadPublish='" + isReadPublish + '\'' +
                '}';
    }
}
