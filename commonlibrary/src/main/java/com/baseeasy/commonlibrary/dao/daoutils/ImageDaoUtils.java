package com.baseeasy.commonlibrary.dao.daoutils;

import android.content.Context;


import com.baseeasy.commonlibrary.dao.entity.HouseholdImage;
import com.baseeasy.commonlibrary.dao.greendaoimage.DaoMaster;
import com.baseeasy.commonlibrary.dao.greendaoimage.DaoSession;
import com.baseeasy.commonlibrary.dao.greendaoimage.HouseholdImageDao;
import com.baseeasy.commonlibrary.dao.helper.MySQLiteOpenHelper;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author：MagicLon
 * @date：2019/8/13 013
 * email：1348149485@qq.com
 * detail：
 */
public class ImageDaoUtils {
    private  volatile static ImageDaoUtils daoUtils;
    private Context context;
    private DaoSession daoSession;

    public ImageDaoUtils(Context context) {
        this.context = context;
        MySQLiteOpenHelper devOpenHelper = new MySQLiteOpenHelper(context.getApplicationContext(), "householdsurvey.db", null);
        Database database = devOpenHelper.getWritableDb();
        DaoMaster.createAllTables(database, true);
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession(IdentityScopeType.None);
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public static ImageDaoUtils getInstance(Context context) {
        if (daoUtils == null) {
            synchronized (ImageDaoUtils.class) {
                if (daoUtils == null) {
                    daoUtils = new ImageDaoUtils(context);
                }
            }
        }
        return daoUtils;
    }

    public void insertImage(HouseholdImage... entities){
        HouseholdImageDao imageEntityDao=daoSession.getHouseholdImageDao();
        imageEntityDao.insertInTx(entities);
    }

    public void insertImage(List<HouseholdImage> entities){
        HouseholdImageDao imageEntityDao=daoSession.getHouseholdImageDao();
        imageEntityDao.insertInTx(entities);
    }

}
