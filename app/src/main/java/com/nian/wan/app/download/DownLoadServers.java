package com.nian.wan.app.download;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

public class DownLoadServers extends Service {

	private static DownloadManager downloadManager;

	public static DownloadManager getDownloadManager(Activity appContext) {
		if (!DownLoadServers.isServiceRunning(appContext)) {
            Intent downloadSvr = new Intent("mc.download.service.action");
            downloadSvr.setPackage(appContext.getPackageName());
            appContext.startService(downloadSvr);
        }
		if (downloadManager == null) {
			downloadManager = DownloadManager.getInstance();
		}
		return downloadManager;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public static boolean isServiceRunning(Context context) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
        = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (serviceList == null || serviceList.size() == 0) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(DownLoadServers.class.getName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
