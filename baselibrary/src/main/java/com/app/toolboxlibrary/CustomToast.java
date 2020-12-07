package com.app.toolboxlibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by PPH on 17/10/13.
 */

public class CustomToast {
    private static Toast mToast;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    /**
     * 系统Toast弹窗
     *
     * @param mContext 当前Activity对象
     * @param text     显示toast内容
     * @param duration 显示时间
     */
    public static void showToast(Context mContext, String text, long duration) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.BOTTOM, 0, 500);
        mToast.show();
        mHandler.postDelayed(r, duration);
    }

}