package com.app.xxbusiness.base;

import android.content.Context;

import com.app.networklibrary.config.AppData;

/**
 * Created by jun on 17/5/15.
 */

public class XxLocalUserInfo {
    private static Context mContext;
    private static XxLocalUserInfo instance;

    public static XxLocalUserInfo getInstance(Context context) {
        if (null == instance) {
            mContext = context;
            instance = new XxLocalUserInfo();
        }

        return instance;
    }

    public AppData getAppData() {
        return AppData.getInstance(mContext);
    }

    /**
     * 判断是否登录过
     *
     * @return
     */
    public boolean isLogin() {
        return getAppData().isLogin();
    }

    public void setLogin(boolean isLogin) {
        getAppData().setLogin(isLogin);
    }

    public boolean getAutoLogin() {
        return getAppData().isAutoLogin();
    }

    public void setAutoLogin(boolean autoLoginFlag) {
        getAppData().setAutoLogin(autoLoginFlag);
    }

    public void setLongitude(String longitude) {
        getAppData().setLongitude(longitude);
    }

    public void setLatitude(String latitude) {
        getAppData().setLatitude(latitude);
    }

    public String getLongitude() {
        return getAppData().getLongitude();
    }

    public String getLatitude() {
        return getAppData().getLatitude();
    }

    public String getToken() {
        return getAppData().getToken();
    }


    public String getID() {
        return getAppData().getId();
    }

    public String getString(String key) {
        return getString( key, "" );
    }

    public String getString(String key, String object) {
        return (String)getAppData().getObject(key, object);
    }

    public Object getObject(String key, Object object) {
        return getAppData().getObject(key, object);
    }

    public void saveObject(String key, Object object) {
        getAppData().saveObject(key, object);
    }


    /*
     * 清除缓存
     */
    public void clearCache() {
        getAppData().clearCache();
    }

}
