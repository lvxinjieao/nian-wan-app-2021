package com.nian.wan.app.bean;

import org.xutils.common.Callback.Cancelable;


public class DownloadInfo {

	public String downloadUrl;
	public int	id;
	public String savePath;
	public int		state;			// 下载的状态
	public long		progress;		// 当前的进度
	public long		size;			// 应用的大小
	public String packageName;	// 当前下载的应用的包名
	public Runnable task;			// 用来记录下载任务
	private Cancelable cancelable;
	public Cancelable getCancelable() {
		return cancelable;
	}
	public void setCancelable(Cancelable cancelable) {
		this.cancelable = cancelable;
	}
	
}
