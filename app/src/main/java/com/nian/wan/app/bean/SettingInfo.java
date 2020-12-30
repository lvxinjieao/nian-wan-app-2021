package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="SettingInfo")
public class SettingInfo {
	@Column(name="id",isId=true,autoGen=false)
	public int id;
	

	@Column(name="wlti")
	public int wlti;    //网络提示     1 提示    2 不提示
	
	@Column(name="delete")
	public int delete;  //下载后是否删除安装包 			1 删除    2 不删除
	
	@Column(name="tan")
	public int tan;    //下载后是否自动弹出安装页面			1 弹出    2 不弹出
	
}
