package com.nian.wan.app.download;

import android.util.Log;

import com.nian.wan.app.bean.DownDataBean;
import com.nian.wan.app.bean.GameDetailBean;
import com.nian.wan.app.bean.GameInfo;
import com.nian.wan.app.bean.MyDownDateBean;
import com.nian.wan.app.utils.DatabaseOpenHelper;
import com.nian.wan.app.utils.FileUtils;
import com.nian.wan.app.utils.Utils;
import com.arialyy.aria.core.Aria;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;

/**
 * 描述：下载管理
 * 作者：钮家齐
 * 时间: 2018-08-11 10:48
 */

public class DownManager {

    private static DownManager instance;
    private static DbManager db;

    public static DownManager getInstance() {
        if (instance == null) {
            instance = new DownManager();
        }
        if(db ==null){
            db = DatabaseOpenHelper.getInstance();
        }
        return instance;
    }

    public File getApkFile(String packageName) {
        String dir = FileUtils.getDownloadDir().getAbsolutePath();
        return new File(dir, packageName + ".apk");
    }

    public File getApkFile1(String packageName) {
        String dir = FileUtils.getDownloadDir().getAbsolutePath();
        return new File(dir, packageName + ".apk.tmp");
    }

    /**
     * 获取下载数据
     */
    public DownDataBean getDownBean(int id) {
        try {
            DownDataBean byId = db.findById(DownDataBean.class, id);
            return byId;
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载游戏
     */
    public void down(final DownDataBean app) {
        try {
            db.saveOrUpdate(app);
            Aria.download(this)
                    .load(app.DownUrl)
                    .setFilePath(getApkFile(String.valueOf(app.id)).getAbsolutePath())
                    .resetState()
                    .start();
            Log.e("DownloadManager","Aria执行了下载");
        }catch (Exception e){
            Log.e("DownloadManager","Aria下载异常:"+e.toString());
        }
    }


    /**
     * 安装应用
     */
    public void install(final DownDataBean bean) {
        try {
            File apkFile = getApkFile(String.valueOf(bean.id));
            if (!apkFile.exists()) {
                Log.e("文件不存在","文件不存在");
                return;
            }
            Utils.installApp(Utils.getContext(), apkFile);
            String dir = FileUtils.getDownloadDir().getAbsolutePath();
            String getPackageName = Utils.getPackageName(x.app(), dir + "/" + bean.id + ".apk");
            // 改变状态
            bean.packageName = getPackageName;
            db.saveOrUpdate(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }




    /**
     * 打开应用
     */
    public DownDataBean open(final DownDataBean bean) {
        try {
            File apkFile = getApkFile(String.valueOf(bean.id));
            String dir = FileUtils.getDownloadDir().getAbsolutePath();
            if (Utils.isInstall(Utils.getContext(), bean.packageName)) {
                bean.btnStatus = 8;         //状态设为  已安装
                db.saveOrUpdate(bean);
                Utils.openApp(Utils.getContext(), bean.packageName);
            } else {
                if (apkFile.exists()) {
                    bean.btnStatus = 1;         //状态设为  下载成功未安装
                    Log.e("未安装应用", "未安装应用");
                    db.saveOrUpdate(bean);
                    return bean;
                } else {
                    Utils.TS("应用不存在");
                    Aria.download(this).load(bean.DownUrl).cancel();
                    bean.btnStatus = -2;
                    db.delete(bean);
                    db.deleteById(MyDownDateBean.class,bean.id);
                    return bean;
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * 下载完成的   真实状态
     */
    public DownDataBean RealState(int id){
        DownDataBean downBean = DownManager.getInstance().getDownBean(id);
        try {
            //判断是否安装过了
            if(downBean!=null&&downBean.DownUrl!=null&&downBean.packageName!=null&&Utils.isInstall(Utils.getContext(), downBean.packageName)){
                //已安装   UI按钮设为打开
                downBean.btnStatus = 8;
                db.saveOrUpdate(downBean);
                return downBean;
            }else{
                File apkFile = getApkFile(String.valueOf(id));
                if(apkFile.exists()){
                    if(downBean!=null&&downBean.btnStatus==2){
                        return downBean;
                    }
                    //此时下载完成但未安装
                    downBean.btnStatus = 1;
                    db.saveOrUpdate(downBean);
                    return downBean;
                }else{
                    //此时下载文件不存在  并且游戏也未安装  视为未下载
                    if(downBean!=null&&downBean.DownUrl!=null){
                        Aria.download(this).load(downBean.DownUrl).cancel();
                        downBean.btnStatus = -2;
                        db.delete(downBean);
                        db.deleteById(MyDownDateBean.class,id);
                    }
                    return downBean;
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return downBean;
    }

    /**
     * 下载失败，保存状态为未下载
     */
    public DownDataBean setState(int id){
        DownDataBean downBean = DownManager.getInstance().getDownBean(id);
        if (downBean!=null){
            try {
                downBean.btnStatus = -2;
                db.saveOrUpdate(downBean);
                return downBean;
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return downBean;
    }



    /**
     * 复制游戏信息存入数据库
     * @param gameInfo
     */
    public void copy(GameInfo gameInfo) {
        try {
            if(gameInfo!=null){
                MyDownDateBean myDownDateBean = new MyDownDateBean();
                myDownDateBean.id = gameInfo.id;
                myDownDateBean.iconurl = gameInfo.iconurl;
                myDownDateBean.size = gameInfo.size;
                myDownDateBean.playNum = gameInfo.playNum;
                myDownDateBean.fanli = gameInfo.fanli;
                myDownDateBean.type = gameInfo.type;
                myDownDateBean.discount = gameInfo.discount;
                myDownDateBean.name = gameInfo.name;
                myDownDateBean.canDownload = gameInfo.canDownload;
                myDownDateBean.record_id = gameInfo.record_id;
                db.saveOrUpdate(myDownDateBean);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除下载文件
     */
    public void Delete(int id){
        File apkFile = getApkFile(String.valueOf(id));
        if(apkFile.exists()){
            apkFile.delete();
        }
    }

    public void copy2(GameDetailBean gameInfo,String record_id) {
        try {
            if(gameInfo!=null){
                MyDownDateBean myDownDateBean = new MyDownDateBean();
                myDownDateBean.id = Integer.parseInt(gameInfo.id);
                myDownDateBean.iconurl = gameInfo.icon;
                myDownDateBean.size = gameInfo.game_size;
                myDownDateBean.playNum = gameInfo.play_num;
                myDownDateBean.fanli = gameInfo.ratio;
                myDownDateBean.type = gameInfo.game_type_name;
                myDownDateBean.discount = gameInfo.discount;
                myDownDateBean.name = gameInfo.game_name;
                myDownDateBean.canDownload = gameInfo.xia_status;
                myDownDateBean.record_id = record_id;
                db.saveOrUpdate(myDownDateBean);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
