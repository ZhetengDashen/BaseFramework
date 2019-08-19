package com.baseeasy.commonlibrary.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.baseeasy.commonlibrary.dao.greendao.DaoMaster;
import com.baseeasy.commonlibrary.dao.greendao.HouseholdImageDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * 作者：MagicLon
 * 时间：2018/4/11 011
 * 邮箱：1348149485@qq.com
 * 描述：数据库升级时使用
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    Class[] daoArray = {HouseholdImageDao.class};

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, daoArray);
    }
}
