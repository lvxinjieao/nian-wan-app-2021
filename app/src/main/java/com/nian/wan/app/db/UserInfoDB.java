package com.nian.wan.app.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 用户信息数据库实体类
 */
@Table(name = "UserInfoDB")
public class UserInfoDB {

    //用户ID
    @Column(name = "id", isId = true, autoGen = false)
    public int id;

    //用户uid
    @Column(name = "uid")
    public String uid;

    //用户账户
    @Column(name = "account")
    public String account;

    //用户token
    @Column(name = "token")
    public String token;

    //用户头像
    @Column(name = "portrait")
    public String portrait;
}
