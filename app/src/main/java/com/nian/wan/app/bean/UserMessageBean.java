package com.nian.wan.app.bean;


/**
 * @Author: XiaYuShi
 * @Date: 2018/1/2
 * @Description: 用户消息实体类
 * @Modify By:
 * @ModifyDate:
 */
public class UserMessageBean {


    /**
     * id : 2
     * user_id : 292
     * game_id : 70
     * content : dssaadadada
     * type : 3
     * status : 1
     * create_time : 112121212
     * read_time : null
     * icon : http://www.myac.com/Uploads/Picture/2017-11-07/5a011fa4ec7b6.png
     */

    private String id;
    private String user_id;
    private String game_id;
    private String content;
    private String type;
    private String status;
    private String create_time;
    private Object read_time;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Object getRead_time() {
        return read_time;
    }

    public void setRead_time(Object read_time) {
        this.read_time = read_time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
