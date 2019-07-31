package com.grechur.wanandroid.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

//import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.utils.UserInfoTools;
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
        setNightMode();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化夜间模式
     */
    private void setNightMode() {
        boolean nightMode = UserInfoTools.isNightMode(this);
        AppCompatDelegate.setDefaultNightMode(nightMode ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }


}
