package com.nian.wan.app.download;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nian.wan.app.bean.DownloadInfo;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.bean.SettingInfo;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.FileUtils;
import com.nian.wan.app.utils.Utils;
import com.arialyy.aria.core.Aria;

import org.xutils.DbManager;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.Executor;


public class DownloadManager extends Service {

    public static final int DOWNLOAD_NOT = 0;//未下载
    public static final int DOWNLOAD_WAITING = 1;//等待
    public static final int DOWNLOAD_LOADING = 2;//下载中
    public static final int DOWNLOAD_SUCCESS = 3;//下载成功未安装
    public static final int DOWNLOAD_FAILED = 4;//下载失败
    public static final int DOWNLOAD_INSTALL = 5;//已安装
    public static final int DOWNLOAD_PAUSE = 6;//暂停

    public int PoolSize;
    private final Executor exec = new PriorityExecutor(1, true);//成员常量
    private List<GameInfo> all;
    private static List<DownloadObserver> mObservers = new LinkedList<DownloadObserver>();
    private static List<DownloadnumObserver> mnumObservers = new LinkedList<DownloadnumObserver>();
    private static Map<String, DownloadInfo> mDownloadInfos = new HashMap<String, DownloadInfo>();
    private static Activity mContext;
    private static DownloadManager instance;
    private static DbManager db;
    private static List<GameInfo> downloadingAppList;
    private static List<GameInfo> historyAppList;

    public static DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }
        if (db == null) {
            db = DatabaseOpenHelper.getInstance();
        }
        return instance;
    }


    public static List getDownloadingAppList() {
        try {
            downloadingAppList = db.selector(GameInfo.class).where("Status", "=", 2).findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return downloadingAppList;
    }


    public File getApkFile(String packageName) {
        String dir = FileUtils.getDownloadDir().getAbsolutePath();
        return new File(dir, packageName + ".apk");
    }

    public File getApkFile1(String packageName) {
        String dir = FileUtils.getDownloadDir().getAbsolutePath();
        return new File(dir, packageName + ".apk.tmp");
    }


    public void down(final GameInfo app) {
        try {
            Aria.download(this)
//                    .load(app.DownUrl)
                    .load("http://nianwan.cn/Uploads/SourcePack/20190726100742_563.apk")
                    .setFilePath(getApkFile(String.valueOf(app.id)).getAbsolutePath())
                    .resetState()
                    .start();
        } catch (Exception e) {
            Log.e("DownloadManager", "Aria下载异常:" + e.toString());
        }
    }

    /**
     * 安裝apk
     */
    public void install(final GameInfo bean) {
        try {
            File apkFile = getApkFile(String.valueOf(bean.id));
            if (!apkFile.exists()) {
                Log.e("文件不存在", "文件不存在");
                bean.btnStatus = DOWNLOAD_NOT;
                db.saveOrUpdate(bean);
                notifyDownloadStateChanged(bean);
                return;
            }
            Utils.installApp(Utils.getContext(), apkFile);
            String dir = FileUtils.getDownloadDir().getAbsolutePath();
            String getPackageName = Utils.getPackageName(x.app(), dir + "/" + bean.id + ".apk");
            // 改变状态
            bean.packname = getPackageName;
            bean.btnStatus = DOWNLOAD_INSTALL;
            db.saveOrUpdate(bean);
            notifyDownloadStateChanged(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断是否安装  并且判断安装过后是否删除安装包
     *
     * @param bean
     */
    public void isInstall(GameInfo bean) {

        try {
            File apkFile = getApkFile(String.valueOf(bean.id));
            File apkFile1 = getApkFile1(String.valueOf(bean.id));
            SettingInfo setting = db.findById(SettingInfo.class, 1);
            if (Utils.isInstall(Utils.getContext(), bean.packname)) {
                //安装了应用
                bean.btnStatus = DOWNLOAD_INSTALL;
                notifyDownloadStateChanged(bean);
                db.saveOrUpdate(bean);
                //判断是否删除安装包
                if (setting != null && setting.delete == 1) {
                    Log.e("执行了删除", "执行了删除");
                    if (apkFile.exists()) {
                        bean.zhong = 0;
                        bean.zsize = 0;
                        db.saveOrUpdate(bean);
                        apkFile.delete();
                    }
                }
            } else {
                //未安装应用
                if (apkFile.exists()) {
                    //判断是否自动提示安装
                    if (setting != null && setting.tan == 1) {
                        if (bean.tishi != 5) {
                            Log.e("bean", bean.tishi + "");
                            Log.e("bean", bean.toString());
                            bean.tishi = 5;
                            db.saveOrUpdate(bean);
                            install(bean);
                        } else {
                            bean.btnStatus = DOWNLOAD_SUCCESS;
                            notifyDownloadStateChanged(bean);
                            db.saveOrUpdate(bean);
                        }
                    } else {
                        if (bean.btnStatus != DOWNLOAD_SUCCESS && bean.btnStatus != DOWNLOAD_INSTALL) {
                            return;
                        }
                        bean.btnStatus = DOWNLOAD_SUCCESS;
                        notifyDownloadStateChanged(bean);
                        db.saveOrUpdate(bean);
                    }
                } else {
                    if (apkFile1.exists()) {
                        //下载中文件存在
                        if (bean.btnStatus == DownloadManager.DOWNLOAD_PAUSE) {
                            bean.zhong = 1;
                            bean.zsize = 0;
                            bean.btnStatus = DownloadManager.DOWNLOAD_PAUSE;
                            notifyDownloadStateChanged(bean);
                            db.saveOrUpdate(bean);
                        }
                    } else {
                        if (bean.btnStatus == DownloadManager.DOWNLOAD_WAITING) {
                            notifyDownloadStateChanged(bean);
                        } else {
                            bean.zhong = 0;
                            bean.zsize = 0;
                            bean.tishi = 0;
                            bean.btnStatus = 0;
                            notifyDownloadStateChanged(bean);
                            db.saveOrUpdate(bean);
                        }
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开应用
     */
    public void open(final GameInfo bean) {
        File apkFile = getApkFile(String.valueOf(bean.id));
        String dir = FileUtils.getDownloadDir().getAbsolutePath();

        if (Utils.isInstall(Utils.getContext(), bean.packname)) {
            Utils.openApp(Utils.getContext(), bean.packname);
        } else {
            if (apkFile.exists()) {
                bean.lishi = 1;
                bean.btnStatus = DOWNLOAD_SUCCESS;
                Log.e("未安装应用", "未安装应用");
                notifyDownloadStateChanged(bean);
                try {
                    db.saveOrUpdate(bean);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            } else {
                Utils.TS("应用不存在");
                bean.lishi = 0;
                bean.zhong = 0;
                bean.zsize = 0;
                bean.btnStatus = 0;
                notifyDownloadStateChanged(bean);
                try {
                    db.saveOrUpdate(bean);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取下载数量
     */
    public int getNum() {
        try {
            List<GameInfo> zhong = db.selector(GameInfo.class).where("zhong", "=", 1).findAll();
            if (zhong == null) {
                notifyDownloadnumChanged(0);
                return 0;
            } else {
                notifyDownloadnumChanged(zhong.size());
                return zhong.size();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 暂停应用
     */
    public void pause(GameInfo bean) {
//        DownloadInfo info = mDownloadInfos.get(String.valueOf(bean.id));
//        Log.e("暂停下载", "");
//        if (info != null) {
//            info.getCancelable().cancel();
//        }
        Aria.download(this).load(bean.GameUrl).stop();
        bean.btnStatus = DownloadManager.DOWNLOAD_PAUSE;
        bean.zhong = 1;
        try {
            db.saveOrUpdate(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除应用
     */
    public void delete(GameInfo bean) {
        pause(bean);
        try {
            Aria.download(this).load(bean.GameUrl).removeRecord();
            File apkFile = getApkFile(String.valueOf(bean.id));
            File apkFile1 = getApkFile1(String.valueOf(bean.id));
            if (apkFile.exists()) {
                apkFile.delete();
            }
            if (apkFile1.exists()) {
                apkFile1.delete();
            }
            bean.zhong = 0;
            bean.zsize = 0;
            bean.btnStatus = 0;
            notifyDownloadStateChanged(bean);
            db.saveOrUpdate(bean);
            getNum();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消下载
     */
    public void cancel(GameInfo bean) {
        DownloadInfo info = mDownloadInfos.get(String.valueOf(bean.id));
        if (info == null) {
            return;
        }
        // 暂停应用
        info.getCancelable().cancel();
        // 改变状态
        bean.btnStatus = DOWNLOAD_NOT;
        notifyDownloadStateChanged(bean);
    }

    /**
     * 添加下载数量观察者
     *
     * @param observer
     */
    public void addnumObserver(DownloadnumObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mnumObservers.contains(observer)) mnumObservers.add(observer);
        }
    }

    /**
     * 添加观察者
     *
     * @param observer
     */
    public void addObserver(DownloadObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mObservers.contains(observer)) mObservers.add(observer);
        }
    }

    /**
     * 删除观察者
     *
     * @param observer
     */
    public synchronized void deleteObserver(DownloadObserver observer) {
        mObservers.remove(observer);
    }

    /**
     * 删除下载数量观察者
     *
     * @param observer
     */
    public synchronized void deletenumObserver(DownloadnumObserver observer) {
        mnumObservers.remove(observer);
    }

    /**
     * 通知观察者数据改变
     */
    public void notifyDownloadStateChanged(GameInfo bean) {
        ListIterator<DownloadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext()) {
            DownloadObserver observer = iterator.next();
            observer.onDownloadStateChanged(this, bean);
        }
    }

    /**
     * 通知观察者数据改变
     */
    public void notifyDownloadnumChanged(int i) {
        ListIterator<DownloadnumObserver> iterator = mnumObservers.listIterator();
        while (iterator.hasNext()) {
            DownloadnumObserver observer = iterator.next();
            observer.onDownloadnumChanged(i);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
