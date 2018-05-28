package com.grechur.wanandroid.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

//import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.utils.Utils;

/**
 * Created by zz on 2018/5/22.
 */

public class WanApplication extends Application{
    private static WanApplication app;
    public static Context getAppContext() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        app = this;
        GreenDaoHelper.initDatabase(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
