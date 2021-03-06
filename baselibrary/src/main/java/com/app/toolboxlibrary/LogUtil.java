package com.app.toolboxlibrary;

import android.content.Context;
import android.util.Log;

/**
 * 日志工具包
 */
public class LogUtil {

    /**
     * 打印普通信息
     *
     * @param tag
     * @param msg
     */

    public static boolean logDebug = true;
    private static String TAG = "LogUtil";

    public static void setLogDebug(boolean logdebug) {
        LogUtil.logDebug = logdebug;
    }

    public static void printInfo(String msg) {
        if (logDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void printInfo(String tag, String msg) {
        if (logDebug) {
            Log.i(tag, msg);
        }
    }


    public static void showLog(String msg) {
        if (logDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void showLog(String TAG, String msg) {
        if (logDebug && null != msg) {
            Log.i(TAG, msg);
        }
    }

    //Toast弹窗
    public static void showToast(final Context mContext, final String content) {
        if (null != content && !"".equals(content)) {
            CustomToast.showToast(mContext, content, 1500);
        }
    }

}
