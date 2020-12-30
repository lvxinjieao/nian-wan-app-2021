package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 描述：储存下载时游戏展示信息
 * 作者：钮家齐
 * 时间: 2018-08-13 11:57
 */
@Table(name="MyDownDateBean")
public class MyDownDateBean {

    @Column(name="id",isId=true,autoGen=false)
    public int id;              //游戏ID

    @Column(name="iconurl")
    public String iconurl;      //图标链接

    @Column(name="name")
    public String name;         //游戏名称

    @Column(name="size")
    public String size;         //游戏大小

    @Column(name="playNum")
    public String playNum;      //在玩次数

    @Column(name="fanli")
    public String fanli;        //游戏返利

    @Column(name="type")
    public String type;         //游戏类型

    @Column(name="discount")
    public String discount;	        //折扣

    @Column(name="canDownload")
    public int canDownload;         //是否能下载  1可以下载 0不可以下载

    @Column(name="features")
    public String features;     //一句游戏描述

    @Column(name="rating")
    public Float rating;        //推荐指数

    @Column(name="gift")
    public int gift;            //是否有礼包  0无 1有

    @Column(name="record_id")
    public String record_id;            //用于后台标识删除用的id
}
