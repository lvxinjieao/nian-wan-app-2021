package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 搜索历史
 * Created by Administrator on 2017/3/15.
 */
@Table(name="SreachBean")
public class SreachBean {

    @Column(name="id",isId=true,autoGen=false)
    public int id;

    @Column(name="name")
    public String name;
}
