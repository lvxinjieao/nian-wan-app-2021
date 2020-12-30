package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 描述：下载需要的数据bean
 */
@Table(name="DownDataBean")
public class DownDataBean {

    @Column(name="Status")
    public int btnStatus;       //按钮状态

    @Column(name="id",isId=true,autoGen=false)
    public int id;              //游戏ID

    @Column(name="DownUrl")
    public String DownUrl;      //下载链接

    @Column(name="packageName")
    public String packageName;  //已安装游戏包名
}
