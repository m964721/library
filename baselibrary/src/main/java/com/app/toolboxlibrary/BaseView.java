package com.app.toolboxlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PPH on 18/2/27.
 */

public class BaseView {
    /**
     * 获取view id
     * @param context
     * @param resType
     * @param resName
     * @return
     */
    public static int getViewId(Context context, String resType, String resName){
        int id = MResource.getIdByName(context,resType,resName);
        return id;
    }

    public static int[] getViewIds(Context context,String resType, String resName){
        int[] id = MResource.getIdsByName(context,resType,resName);
        return id;
    }

    public static int setStyles(Context context, String styles){
        return getViewId(context,"style",styles);
    }

    //初始化dialog布局
    public static void setDialogView(Context context,String layoutName){
        ((Activity)context).setContentView(getViewId(context,"layout",layoutName));
    }

    //初始化控件
    public static View initWidget(Context context, String widgetId ){
        View view = ((Activity)context).findViewById(getViewId(context,"id",widgetId));
        return view;
    }

    public static View initWidget(Dialog dialog, Context context, String widgetId ){
        View view = dialog.findViewById(getViewId(context,"id",widgetId));
        return view;
    }

    public static View initWidget(View rootview,Context context, String widgetId ){
        View view = rootview.findViewById(getViewId(context,"id",widgetId));
        return view;
    }

    //返回ID
    public static int getWidgetId(Context context,String widgetId ){
        int id = getViewId(context,"id",widgetId);
        return id;
    }

    //返回ID
    public static int getWidgetMipmapId(Context context,String widgetId ){
        int id = getViewId(context,"mipmap",widgetId);
        return id;
    }

    //返回字符串ID
    public static String getWidgetResString(Context context,String resId ){
        int id = getViewId(context,"string",resId);
        return context.getResources().getString(id);
    }

    //返回字符串ID
    public static int getWidgetResStyle(Context context,String resId ){
        int id = getViewId(context,"style",resId);
        return id;
    }

    //返回颜色ID
    public static int getWidgetColorId(Context context,String colorId ){
        int id = getViewId(context,"color",colorId);
        return context.getResources().getColor(id);
    }

    //返回drawable ID
    public static Drawable getWidgetDrawableId(Context context, String drawableId ){
        int id = getViewId(context,"drawable",drawableId);
        return context.getResources().getDrawable(id);
    }

    //返回layout
    public static int initLayout(Context context,String widgetId ){
        return getViewId(context,"layout",widgetId);
    }

    //初始化布局
    public static View getLayoutView(Context context,String  layoutname,ViewGroup rootView){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(BaseView.getViewId(context,"layout",layoutname),rootView);
    }

    /**
     * 返回数组
     * @param context
     * @param attrsId
     * @return
     */
    public static int[] initAttrs(Context context,String attrsId){
        return getViewIds(context,"styleable",attrsId) ;
    }

    /**
     * 返回styleable
     * @param context
     * @param name
     * @return
     */
    public static int getAttrId(Context context ,String name){
        int id =  BaseView.getViewId(context,"styleable",name);
        return id;
    }

    /**
     * 返回styleable
     * @param context
     * @param name
     * @return
     */
    public static int getAnimId(Context context ,String name){
        int id =  BaseView.getViewId(context,"anim",name);
        return id;
    }
}
