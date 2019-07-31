package com.grechur.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefMgr {
	private SharedPreferences pref;

	public PrefMgr(Context ctx) {
		pref = PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public String getString(String key) {
		return pref.getString(key, null);
	}

	public String getString(String key, String defVal) {
		return pref.getString(key, defVal);
	}

	public void putString(String key, String val) {
		pref.edit().putString(key, val).commit();
	}
	public int getInt(String key, int defVal) {
		return pref.getInt(key, defVal);
	}

	public void putLong(String key, long val) {
		pref.edit().putLong(key, val).commit();
	}

	public long getLong(String key, long defVal) {
		return pref.getLong(key, defVal);
	}

	public void putInt(String key, int val) {
		pref.edit().putInt(key, val).commit();
	}

	public float getFloat(String key, float defVal) {
		return pref.getFloat(key, defVal);
	}

	public void putFloat(String key, float val) {
		pref.edit().putFloat(key, val).commit();
	}

	public boolean getBool(String key, boolean defVal) {
		return pref.getBoolean(key, defVal);
	}

	public void putBool(String key, boolean val) {
		pref.edit().putBoolean(key, val).commit();
	}

	// 便于链式调用
	public Editor getEditor() {
		return pref.edit();
	}
}
