package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by 齐天大圣 on 2016/10/8.
 */
@Table(name="TopTu")
public class TopTu {


    @Column(name="id",isId=true,autoGen=false)
    public int id;

    @Column(name="url")
    public String url;

    @Column(name="name")
    public String name;

    @Column(name="wang_url")
    public String wang_url;

    @Override
    public String toString() {
        return "TopTu{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", wang_url='" + wang_url + '\'' +
                '}';
    }
}
