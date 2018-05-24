package com.grechur.wanandroid.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.grechur.wanandroid.model.entity.DaoMaster;
import com.grechur.wanandroid.model.entity.DaoSession;

/**
 * Created by zz on 2018/5/24.
 */

public class GreenDaoHelper {
    private static DaoMaster.DevOpenHelper mHelper;
    private static SQLiteDatabase mDb;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    /**
     * 设置greenDao
     */
    public static void initDatabase(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context, "wan_db", null);
        mDb = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
    public static SQLiteDatabase getDb() {
        return mDb;
    }

}
