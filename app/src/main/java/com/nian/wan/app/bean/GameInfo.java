package com.nian.wan.app.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;

@Table(name="GameInfo")
public class GameInfo implements Serializable {
	
	@Column(name="Status")
	public int btnStatus; 		//按钮状态
	
	@Column(name="id",isId=true,autoGen=false)
	public int id;				//游戏ID

	@Column(name="GameUrl")
	public String GameUrl;		//游戏链接（手游下载链接/H5游戏链接）

	@Column(name="name")
	public String name;			//游戏名称

	@Column(name="iconurl")
	public String iconurl;		//图标链接

	@Column(name="size")
	public String size;			//游戏大小

	@Column(name="position")
	public int position;

	@Column(name="progress")
	public long	progress;		//当前进度

	@Column(name="zsize")
	public long	zsize;			//总大小

	@Column(name="zhong")
	public int zhong;

	@Column(name="lishi")
	public int lishi;

	@Column(name="tishi")
	public int tishi;			//是否自动提示过了   5提示过了

	@Column(name="or")
	public int or;				//是否需要弹出安装框   1可以提示   2不提示

	public String spent;

	@Column(name="playNum")
	public String playNum;  	//在玩人数/下载次数

	public int gift;   			//是否有礼包  0无 1有

	@Column(name="rating")
	public Float rating;    	//推荐指数

	@Column(name="sanlei")

	public ArrayList<String> jietu = new ArrayList<String>();  //游戏截图

	@Column(name="features")
	public String features;   	//一句游戏描述

	public String introduce;
	@Column(name="fanli")		//一段游戏介绍

	public String fanli;  		//游戏返利

	public String kaifu;  		//开测公测

	public String time;   		//时间

	@Column(name="Collection")
	public int Collection;	 			//是否收藏	 2未收藏   1已收藏

	@Column(name="anzhuang")
	public int anzhuang;  				//自动安装

	@Column(name="packname")
	public String packname;				//包名

	@Column(name="type")
	public String type;  				//游戏类型

	@Column(name="discount")
	public String discount;				//绑币折扣

	@Column(name="giftId")
	public String giftId;  				//礼包id

	@Column(name="canDownload")
	public int canDownload;  			//是否能下载  1可以下载 0不可以下载

	@Column(name="sdk_version")
	public int sdk_version;  			//游戏类型  1 安卓手游 2 IOS手游 3 H5游戏

	@Column(name="first_discount")
	public String first_discount;  		//首冲折扣 10不需要显示

	@Column(name="continue_discount")
	public String continue_discount;  	//续充折扣 10不需要显示

	@Column(name="record_id")
	public String record_id;  			//删除游戏用的id

	@Column(name="is_high_level_game")
	public String is_high_level_game;  //是否精品

	@Column(name="bg_img")
	public String bg_img;  				//游戏背景图片


}
