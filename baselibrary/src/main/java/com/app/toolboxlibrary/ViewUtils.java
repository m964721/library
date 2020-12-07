package com.app.toolboxlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

public class ViewUtils {

    public static int LineraLayoutFlag = 1;//外层是线性布局
    public static int RelativeLayoutFlag = 2;//外层是相对布局
    public static int FrameLayoutFlag = 3;//外层是相对布局
    //获取系统状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    //设置Activity的topmargin，在全屏状态下设置
    //设置顶部边距
    public static void initBarMargin(Context context, View view, int viewFlag) {
        int marginTop = ViewUtils.getStatusBarHeight(context);
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (marginTop == 0) {
                params.topMargin = 30;
            } else {
                params.topMargin = marginTop;
            }
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (marginTop == 0) {
                params.topMargin = 30;
            } else {
                params.topMargin = marginTop;
            }
            view.setLayoutParams(params);
        }else if (RelativeLayoutFlag == viewFlag) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            if (marginTop == 0) {
                params.topMargin = 30;
            } else {
                params.topMargin = marginTop;
            }
            view.setLayoutParams(params);
        }
    }

    /****************************************以下方法传入的单位都是px或者屏幕百分比***************************************************************************/
    //设置view大小
    public static void initViewSize(Context context,
                                    View view,
                                    int width,
                                    int height,
                                    int viewFlag
    ) {
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.height = height;
            params.width = width;
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.height = height;
            params.width = width;
            view.setLayoutParams(params);
        }else if (FrameLayoutFlag == viewFlag) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.height = height;
            params.width = width;
            view.setLayoutParams(params);
        }
    }


    //设置view高度
    public static void initHeightPXSize(Context context, View view, int viewFlag, int size) {
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.height = size;
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.height = size;
            view.setLayoutParams(params);
        }else if (FrameLayoutFlag == viewFlag) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.height = size;
            view.setLayoutParams(params);
        }
    }

    //设置view宽度
    public static void initWidthPXSize(Context context, View view, int viewFlag, int size) {
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = size;
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.width = size;
            view.setLayoutParams(params);
        }else if (FrameLayoutFlag == viewFlag) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.width = size;
            view.setLayoutParams(params);
        }
    }

    //设置宽度
    public static int getShapeWigth(Activity context) {
        float density = TransformUtil.density(context);
        return (int) (1 * density);
    }

    /**
     * 设置dialog底部显示铺满屏幕，
     *
     * @param dialog
     */
    public static void fillToshow(Dialog dialog) {
        if (null == dialog) {
            return;
        }
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    //设置view底部距离
    public static void setBottomMargin(Context context, View view, int viewFlag, int size) {
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.bottomMargin = size;
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.bottomMargin = size;
            view.setLayoutParams(params);
        }else if(FrameLayoutFlag == viewFlag){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.bottomMargin = size;
            view.setLayoutParams(params);
        }
    }

    //设置view顶部距离
    public static void setTopMargin(Context context, View view, int viewFlag, int size) {
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.topMargin = size;
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.topMargin = size;
            view.setLayoutParams(params);
        } else if(FrameLayoutFlag == viewFlag){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.topMargin = size;
            view.setLayoutParams(params);
        }
    }

    //设置view左边距
    public static void setLeftMargin(Context context, View view, int viewFlag, int size) {
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = size;
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = size;
            view.setLayoutParams(params);
        } else if(FrameLayoutFlag == viewFlag){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = size;
            view.setLayoutParams(params);
        }
    }

    //设置view右边距
    public static void setRightMargin(Context context, View view, int viewFlag, int size) {
        if (LineraLayoutFlag == viewFlag) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.rightMargin = size;
            view.setLayoutParams(params);
        } else if (RelativeLayoutFlag == viewFlag) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.rightMargin = size;
            view.setLayoutParams(params);
        } else if(FrameLayoutFlag == viewFlag){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.rightMargin = size;
            view.setLayoutParams(params);
        }
    }

    /**
     * 修改背景shape
     */
    public static void changeShapeSolidColor(View groupRankLayout,
                                             int solidColor) {
        GradientDrawable mGroupDrawable = (GradientDrawable) groupRankLayout.getBackground();
        /*设置边框颜色和宽度*/
//        mGroupDrawable.setStroke(strokWidth, strokeColor);
        /*设置整体背景颜色*/
        mGroupDrawable.setColor(solidColor);
        /*设置圆角*/
//        mGroupDrawable.setCornerRadius();
    }
}
