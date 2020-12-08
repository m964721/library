package com.app.toolboxlibrary;

import android.content.Context;
import android.util.DisplayMetrics;

//dp px sp 转换工具
public class TransformUtil {
    /** * 根据手机的分辨率从dp 的单位 转成为px(像素) */ 
    public static int dip2px(Context context, float dpValue) { 
            final float scale = context.getResources().getDisplayMetrics().density; 
            return (int) (dpValue * scale + 0.5f); 
    } 

    /** * 根据手机的分辨率从px(像素) 的单位 转成为dp */ 
    public static int px2dip(Context context, float pxValue) { 
            final float scale = context.getResources().getDisplayMetrics().density; 
            return (int) (pxValue / scale + 0.5f); 
    } 
    
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     */  
    public static int sp2px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }

    /**
     *
     * @说明：px转换为sp
     * @Parameters pxValue 需要转换的px值
     * @return     无
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * px与dp转换的系数
     */
    public static float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}   
