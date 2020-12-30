package com.nian.wan.app.download;

import com.nian.wan.app.bean.GameInfo;


public interface DownloadObserver {

	/**
	 * 当状态改变时的回调
	 * 
	 * @param manager
	 * @param info
	 */
	void onDownloadStateChanged(DownloadManager manager, GameInfo info);

	/**
	 * 当进度改变时的回调
	 * 
	 * @param manager
	 * @param info
	 */
	//void onDownloadProgressChanged(DownloadManager manager, AppInfo info);
}
