package com.app.networklibrary.config;

/**
 * Created by jun on 17/2/9.
 */

public class NetStatusCode {
    public static final String NET_SUCCESS_STATUS = "0" ;
    public static final String NET_TIMEOUT_STATUS = "-1";
    public static final String NET_UNKNOWN_HOST_STATUS = "-2";
    public static final String NET_UNKNOWN_ERROR_STATUS = "-3";
    public static final String NET_ANOMALY_STATUS = "-4";
    public static final String NET_WRONG_PARAM_STATUS = "-5";

    public static final String NET_CheckNet = "请检查您的网络配置" ;
    public static final String NET_JsonFail = "数据解析异常" ;
    public static final String NET_TimeOut = "网络超时" ;
    public static final String Net_UnKnow_Host = "网络异常，请重试" ;

    //定义关于网络请求回调的数值和定位
    public static final int NETREQUESTSUCCESS = 0x01 ;
    public static final int NETREQUESTFailure = 0x02 ;
    public static final int NETREQUESTThrowable = 0x03 ;
    public static final int NETREQUESTFinish = 0x04 ;
    public static final int LOCATIONSUCCESS = 0x05 ;

    //解析数据
    public static String codeKey = "code";//解析返回状态码的key
    public static String netToastMessageKey = "msg";//接口返回数据，状态码
    public static String dataKey = "data";//接口返回数据，
    public static String SUCCESSSTATUS = "0";//接口返回状态码
    public static String SUCCESS200 = "200";//接口返回状态码
    public static String ToLoginStatus = "401" ;//跳转登录刷新token


    public static String requestUrl = "requestUrl";//接口请求的完整url
    public static String backData = "backData" ;//解析返回数据
    public static String applicationKey = "application" ;//接口名字

}
