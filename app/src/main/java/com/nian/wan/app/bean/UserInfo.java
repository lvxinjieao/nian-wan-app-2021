package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by LeBron on 2016/10/14.
 */

@Table(name="UserInfoDB")
public class UserInfo {

    @Column(name="id",isId=true,autoGen=false)
    public int id;                                  //用户ID

    @Column(name="icon")
    public String icon;                             //用户头像

    @Column(name="nickname")
    public String nickname;                          //用户昵称

    @Column(name="token")
    public String token;                          //用户token

    @Column(name="userid")
    public String userid;                          //用户id

    @Column(name="phone")
    public String phone;                          //用户手机号

    @Column(name="Ptb")
    public String Ptb;                          //平台币

    @Column(name="login")
    public int login;                               //登录方式   1手机号登录 2普通账号登录 3第三方登录

}
