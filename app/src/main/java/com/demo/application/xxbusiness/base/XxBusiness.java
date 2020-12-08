package com.demo.application.xxbusiness.base;

import android.content.Context;

import com.demo.application.networklibrary.utils.HttpCacheUtil;
import com.demo.application.networklibrary.utils.NetStatus;
import com.app.toolboxlibrary.LogUtil;

/**
 * Created by jun on 17/2/22.
 * 业务层总入口：根据服务类型不同调用对应的服务库
 */

public class XxBusiness {
    private static XxBusiness instance;

    public XxBusiness(){
        init();
    }

    public static XxBusiness getInstance(){
        if ( instance == null ) {
            instance = new XxBusiness();
        }
        return instance;
    }

    public void init(){

    }


    /**
     * 清除缓存
     */
    public void clearCache(Context mContext){
        HttpCacheUtil.getHttpCacheUtil().clearSaveFile(mContext);
        getMyUserInfo(mContext).clearCache();
    }

    /**
     * 显示提示信息
     * @param context
     * @param msg
     */
    public static void showToast(Context context,String msg){
        LogUtil.showToast(context,msg);
    }

    /**
     * 获取实名信息
     */
    public XxLocalUserInfo getMyUserInfo(Context context){
        return XxLocalUserInfo.getInstance(context);
    }


    /**
     * 配置系统位置信息
     * @param context
     * @param longitude
     * @param latitude
     */
    public void configLocationInfo(Context context,String longitude,String latitude){
        getMyUserInfo(context).setLongitude(longitude);
        getMyUserInfo(context).setLatitude(latitude);
    }

    /**
     * 检查网络状态
     * @param context
     * @return
     */
    public static boolean checkNetStatus(Context context){
        return NetStatus.checkNet(context);
    }

}
