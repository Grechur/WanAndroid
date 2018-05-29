package com.grechur.wanandroid.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.grechur.wanandroid.model.entity.home.DaoMaster;
import com.grechur.wanandroid.model.entity.home.DaoSession;
import com.grechur.wanandroid.model.entity.home.History;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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


    public static Observable<Boolean> insertHistory(History history){
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                if(history!=null){
                    Long id = mDaoSession.getHistoryDao().insertOrReplace(history);
                    if(id==history.id){
                        e.onNext(true);
                    }else {
                        e.onNext(false);
                    }
                    e.onComplete();
                }else{
                    e.onNext(false);
                    e.onComplete();
                }
            }
        });
    }

    public static Observable<List<History>> queryHistory(){

        return Observable.create(new ObservableOnSubscribe<List<History>>() {
            @Override
            public void subscribe(ObservableEmitter<List<History>> e) throws Exception {
                List<History> historyList = mDaoSession.getHistoryDao().queryBuilder().list();
                if(historyList!=null&&historyList.size()>0){
                    e.onNext(historyList);
                    e.onComplete();
                }else{
                    e.onError(new Throwable("数据库中没数据"));
                }
            }
        });

    }




}
