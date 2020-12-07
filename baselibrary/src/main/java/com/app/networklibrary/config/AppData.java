package com.app.networklibrary.config;

import android.content.Context;

import com.app.toolboxlibrary.PreferenceUtil;

/**
 *
 */
public class AppData {

    private static Context context;
    private boolean login = false; // 是否登录
    private boolean isAutoLogin = false; // 检查是否是自动登录
    private String longitude;//经纬度信息
    private String latitude;//经纬度信息
    private String location_address;//定位地址
    private String token = "";//用户token

    private static AppData instance = null;

    private AppData() {

    }

    public static AppData getInstance(Context mcontext) {
        context = mcontext;
        if (instance == null)
            instance = new AppData();
        return instance;
    }

    public boolean isLogin() {
        login = PreferenceUtil.getInstance(context).getBoolean("PPHlogin",
                false);
        return login;
    }

    public void setLogin(boolean login) {
        PreferenceUtil.getInstance(context).saveBoolean("PPHlogin", login);
        this.login = login;
    }

    public boolean isAutoLogin() {
        isAutoLogin = PreferenceUtil.getInstance(context).getBoolean(
                "isAutoLogin", false);
        return isAutoLogin;
    }

    public void setAutoLogin(boolean isAutoLogin) {
        PreferenceUtil.getInstance(context).saveBoolean("isAutoLogin",
                isAutoLogin);
        this.isAutoLogin = isAutoLogin;

    }
    public String getLongitude() {
        longitude = PreferenceUtil.getInstance(context).getString(
                "PPHlongitude", "0");
        return longitude;
    }

    public void setLongitude(String longitude) {
        PreferenceUtil.getInstance(context).saveString("PPHlongitude", longitude);
        this.longitude = longitude;
    }

    public String getLatitude() {
        latitude = PreferenceUtil.getInstance(context).getString("PPHlatitude", "0");
        return latitude;
    }

    public void setLatitude(String latitude) {
        PreferenceUtil.getInstance(context).saveString("PPHlatitude", latitude);
        this.latitude = latitude;
    }

    public String getLocation_address() {
        location_address = PreferenceUtil.getInstance(context).getString("location_address", "");
        return location_address;
    }

    public void setLocation_address(String location_address) {
        PreferenceUtil.getInstance(context).saveString("location_address", location_address);
        this.location_address = location_address;
    }

    public String getToken() {
        token = PreferenceUtil.getInstance(context).getString("token", "");
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        PreferenceUtil.getInstance(context).saveString("token", token);
    }

    public String getId() {
        return PreferenceUtil.getInstance(context).getString("auid","");

    }

    public void setId(int id) {
        PreferenceUtil.getInstance(context).saveInt("auid", id);
    }


    public Object getObject(String key, Object object) {
        return  PreferenceUtil.getInstance(context).getObject(key,object) ;
    }

    public void saveObject(String key, Object object) {
       PreferenceUtil.getInstance(context).saveObject(key,object);
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        PreferenceUtil.getInstance(context).clearCache();
    }
}
