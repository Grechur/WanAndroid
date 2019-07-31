package com.grechur.wanandroid.utils;

import android.content.Context;
import android.text.TextUtils;

// 登录状态和账户信息统一放到这儿处理吧，便于管理
public class AccountMgr extends PrefMgr {

	public AccountMgr(Context ctx) {
		super(ctx);
	}

	// 清除所有登录和账户信息
	public void clear() {
		getEditor().clear().commit();
	}

	public String getVal(String key) {
		return getString(key);
	}

	public String getVal(String key, String defVal) {
		return getString(key, defVal);
	}
}
