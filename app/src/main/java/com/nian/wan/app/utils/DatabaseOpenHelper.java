package com.nian.wan.app.utils;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

public class DatabaseOpenHelper {

    public static final String DB_NAME = "nianwan.db";    //设置数据库名称
    public static final int DB_VERSION = 1;               //设置数据库版本

    static DbManager.DaoConfig daoConfig;

    private static DbManager dbManager;

    public static DbManager getInstance() {
        if (dbManager == null) {
            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper();
        }
        return dbManager;
    }

    private DatabaseOpenHelper() {
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(DB_NAME)
                    .setDbVersion(DB_VERSION)
                    .setDbDir(FileUtils.getPath())                  //设置数据库存放路径
                    .setAllowTransaction(true)                      //设置全部开启事务
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {

                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            try {
                                db.addColumn(AboutUsDBean.class, "test");
                            } catch (DbException e) {
                                Log.e("test", "数据库更新失败");
                                e.printStackTrace();
                            }
                        }
                    });
        }
        dbManager = x.getDb(daoConfig);
    }

}
