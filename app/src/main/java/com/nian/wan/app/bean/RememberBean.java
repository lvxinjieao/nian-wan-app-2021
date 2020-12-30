package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/5/8.
 */
@Table(name = "RememberBean")
public class RememberBean {

    @Column(name = "id", isId = true, autoGen = false)
    public int id;

    @Column(name = "account")
    public String account;

    @Column(name = "password")
    public String password;

}
