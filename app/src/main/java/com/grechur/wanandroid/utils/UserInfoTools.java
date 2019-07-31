package com.grechur.wanandroid.utils;

import android.content.Context;

import com.grechur.wanandroid.base.WanApplication;
import com.grechur.wanandroid.ui.AboutUsActivity;

/**
 * Created by zz on 2018/8/17.
 */

public class UserInfoTools {
    public static boolean isNightMode(Context context) {
        AccountMgr accountMgr = new AccountMgr(context);
        return accountMgr.getBool("isNight",false);
    }

    public static void setNightMode(Context context, boolean b) {
        AccountMgr accountMgr = new AccountMgr(context);
        accountMgr.putBool("isNight",b);
    }

    public static void setChangeNightMode(Context context, boolean b) {
        AccountMgr accountMgr = new AccountMgr(context);
        accountMgr.putBool("isNight",b);
    }
}
