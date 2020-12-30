package com.nian.wan.app.bean;

/**
 * @Author: XiaYuShi
 * @Date: 2017/11/28
 * @Description: 首页活动-活动实体类
 * @Modify By:
 * @ModifyDate:
 */
public class HomeActivitiesBean {

    /**
     * id : 62
     * type : app_huodong
     * category_id : 78
     * title : 年度盛典活动说明
     * description :
     * belong_game : 我的世界
     * line_time : 1517304628
     * start_time : 2018-01-30 17:30
     * end_time : 永久
     * cover : http://testh55.vlcms.com/Uploads/Picture/2018-01-30/5a6fee4bcbf60.png
     * url : http://testh55.vlcms.com/mobile.php/Article/detail/id/62
     * article_status : 1
     */

    private String id;
    private String type;
    private String category_id;
    private String title;
    private String description;
    private String belong_game;
    private String line_time;
    private String start_time;
    private String end_time;
    private String cover;
    private String url;
    private int article_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLine_time() {
        return line_time;
    }

    public void setLine_time(String line_time) {
        this.line_time = line_time;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getArticle_status() {
        return article_status;
    }

    public void setArticle_status(int article_status) {
        this.article_status = article_status;
    }
}
