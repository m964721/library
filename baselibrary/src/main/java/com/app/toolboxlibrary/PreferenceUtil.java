package com.app.toolboxlibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * 首选项工具类
 * 
 * @author
 * 
 */
public class PreferenceUtil {

	private String PREFERENCE_NAME = "appUser" ;

	private static PreferenceUtil preferenceUtil ;

	private SharedPreferences shareditorPreferences ;

	private Editor editor;

	private PreferenceUtil(Context context) {
		init(context);
	}

	public void init(Context context) {
		if (shareditorPreferences == null || editor == null) {
			try {
				shareditorPreferences = context.getSharedPreferences(
						PREFERENCE_NAME, Context.MODE_PRIVATE);
				editor = shareditorPreferences.edit();
			} catch (Exception e) {
			}
		}
	}

	public static PreferenceUtil getInstance(Context context) {
		if (preferenceUtil == null) {
			preferenceUtil = new PreferenceUtil(context);
		}
		return preferenceUtil;
	}

	public void saveLong(String key, long l) {
		editor.putLong(key, l);
		editor.commit();
	}

	public long getLong(String key, long defaultlong) {
		return shareditorPreferences.getLong(key, defaultlong);
	}

	public void saveBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key, boolean defaultboolean) {
		return shareditorPreferences.getBoolean(key, defaultboolean);
	}

	public void saveInt(String key, int value) {
		if (editor != null) {
			editor.putInt(key, value);
			editor.commit();
		}
	}

	public int getInt(String key, int defaultInt) {
		return shareditorPreferences.getInt(key, defaultInt);
	}

	public String getString(String key, String defaultInt) {
		return shareditorPreferences.getString(key, defaultInt);
	}

	public void saveString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}
	public Object getObject(String key, Object object) {
		if(object instanceof  String){
			return	getString(key,(String)object);
		}else if(object instanceof Integer){
			return getInt(key,(Integer)object);
		}else if(object instanceof Boolean){
			return	getBoolean(key,(Boolean) object);
		}else if(object instanceof Long){
			return getLong(key,(Long) object);
		}
		return null ;
	}

	public void saveObject(String key, Object object) {
		if(object instanceof  String){
			saveString(key,(String)object);
		}else if(object instanceof Integer){
			saveInt(key,(Integer)object);
		}else if(object instanceof Boolean){
			saveBoolean(key,(Boolean) object);
		}else if(object instanceof Long){
			saveLong(key,(Long) object);
		}
	}

	public void remove(String key) {
		editor.remove(key);
		editor.commit();
	}

	public static boolean checkIsFirstLogin(Context context, String activityname){

		preferenceUtil= PreferenceUtil.getInstance(context);
		boolean  isfirstin = preferenceUtil.getBoolean(activityname, true);
		if(isfirstin){	// 如果是第一次登陆，则保存为false
			preferenceUtil.saveBoolean(activityname, false);
			LogUtil.showLog(activityname, " first come");

		}else {
			LogUtil.showLog(activityname, "not first come");

		}
		return  isfirstin;
	}

	/**
	 * 清除缓存
	 */
	public void clearCache(){
		editor.clear();
		editor.commit();
	}
}
