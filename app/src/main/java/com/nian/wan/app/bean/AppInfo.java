package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.ArrayList;

/**
 * Created by 齐天大圣 on 2016/10/8.
 */
@Table(name="AppInfo")
public class AppInfo {

    @Column(name="id",isId=true,autoGen=false)
    public int id;

    @Column(name="name")
    public String name;

    @Column(name="icon_url")
    public String icon_url;

    @Column(name="play_url")
    public String play_url;

    @Column(name="ren")
    public String ren;

    @Column(name="type")
    public String type;

    @Column(name="type_id")
    public String typeid;

    @Column(name="yijuhua")
    public String yijuhua;

    public float tui;

    public int Collection;                                      //是否收藏   1是    2否

    @Column(name="ping")
    public int ping;                                      //屏幕方向   1横    2竖

    @Column(name="gametype")
    public int gametype;                                      //游戏类型（1.精品 2.热门 3.最新）


    public ArrayList<String> list = new ArrayList<String>();


    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", play_url='" + play_url + '\'' +
                ", ren='" + ren + '\'' +
                ", type='" + type + '\'' +
                ", yijuhua='" + yijuhua + '\'' +
                ", tui=" + tui +
                ", Collection=" + Collection +
                ", ping=" + ping +
                ", list=" + list +
                '}';
    }
}
