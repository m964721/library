package com.app.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.networklibrary.utils.NetStatus;
import com.app.toolboxlibrary.FastJsonUtils;
import com.app.toolboxlibrary.LogUtil;


/**
 * Created by ppx on 17/3/10.
 */

public class LocationUtils {
    private static Context mContext;
    private static volatile LocationUtils instance = null ;
    private LocationCallback mCallback ;

    //高德信息
    private AMapLocationClient locationClient = null;

    /**
     * 单例模式
     * @return
     */
    public static LocationUtils getInstance() {
        if (null == instance) {
            synchronized (LocationUtils.class) {
               if(null == instance) instance = new LocationUtils();
            }
        }
        return instance;
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void initLocation(Context context , LocationCallback callback) {
        mContext = context;
        mCallback = callback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化client
                locationClient = new AMapLocationClient(mContext);
                //设置定位参数
                locationClient.setLocationOption(getDefaultOption());
                // 设置定位监听
                locationClient.setLocationListener(locationListener);
                //有网络且已经授权
                int isPermitted = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
                if (isPermitted == PackageManager.PERMISSION_GRANTED && NetStatus.checkNet(mContext)) {
                    locationClient.startLocation();
                } else {
                    mCallback.onPositionCallback(false, null);
                }
            }
        }).start();

    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiActiveScan(false); //设置是否强制刷新WIFI，默认为true，强制刷新。
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                Message message = new Message() ;
                message.what = 0 ;
                message.obj = loc;
                locationHandler.sendMessage(message);
            } else {
                Message message = new Message() ;
                message.what = 1 ;
                locationHandler.sendMessage(message);
            }
        }
    };

    /**
     * 停止定位
     */
    public void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     * @since 2.8.0
     */
    public void destroyLocation() {
        if (null != locationClient) {
            /*
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
        }
    }

    /**
     * 根据定位结果处理信息
     */
    private void getLocationStr(AMapLocation baseMapLocation) {
        if (null == baseMapLocation) {
            return;
        }
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (baseMapLocation.getErrorCode() == 0) {
            LogUtil.showLog("定位信息", "定位信息:" + baseMapLocation.toString());
            LocationInfo xxLocationInfo = new LocationInfo();
            String province = baseMapLocation.getProvince();//省份或者直辖市
            String city = baseMapLocation.getCity();//城市
            String district = baseMapLocation.getDistrict();//分区
            String longitude = baseMapLocation.getLongitude() + "";//经度
            String latitude = baseMapLocation.getLatitude() + "";//维度
            String cityCode = baseMapLocation.getCityCode();//城市编码
            String desc = baseMapLocation.getExtras().getString("desc");//位置
            xxLocationInfo.setCity(city);
            xxLocationInfo.setCityCode(cityCode);
            xxLocationInfo.setDesc(desc);
            xxLocationInfo.setDistrict(district);
            xxLocationInfo.setLatitude(latitude);
            xxLocationInfo.setLongitude(longitude);
            xxLocationInfo.setProvince(province);
            xxLocationInfo.setSuccess(true);
            String backData = FastJsonUtils.beanToJSON(xxLocationInfo);
            mCallback.onPositionCallback(true, backData);
            LogUtil.showLog("定位信息", "" + "经    度    : " +
                    baseMapLocation.getLongitude() + "\n"
                    + "纬    度    : " + baseMapLocation.getLatitude());
        } else {
            //定位失败
            LogUtil.showLog("定位失败", "" + baseMapLocation.getErrorCode() + "\n" +
                    baseMapLocation.getErrorInfo() + "\n" + baseMapLocation.getLocationDetail());
            mCallback.onPositionCallback(false, null);
        }
        stopLocation();
    }

    //定位handler
    @SuppressLint("HandlerLeak")
    Handler locationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //解析定位结果
                    getLocationStr((AMapLocation) msg.obj);
                    break;
                case 1:
                    mCallback.onPositionCallback(false, "");
                    break;
                default:
                    break;
            }
        }
    };
}
