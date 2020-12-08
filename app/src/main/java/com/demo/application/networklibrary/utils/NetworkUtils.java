package com.demo.application.networklibrary.utils;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */

public class NetworkUtils {
    /**
     * 通过反射机制调用对应的函数名称
     * @param methodName
     */
    public static boolean invokeMethod(String methodName, Context context, Object... args){
        if ( null == context || null == methodName || "".equals(methodName)) {
            return false;
        }

        boolean ret = false;

        try {
            Method m1;
            if ( null == args || args.length == 0) {
                m1 = context.getClass().getDeclaredMethod(methodName);
                m1.setAccessible(true);
                m1.invoke(context);
            } else {
                int length = args.length;
                Class[] argsClass = new Class[length];
                for (int i = 0 ; i < length; i ++) {
                    argsClass[i] = Object.class;
                }
                m1 = context.getClass().getDeclaredMethod(methodName,argsClass);
                m1.setAccessible(true);//私有方法也可以调用
                m1.invoke(context, args);
            }
            ret = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 根据包名获取activity实例
     *
     * @param name
     * @return
     */
    public static Class getClassInstanceByName(String packageName,String name) {
        Class instance = null;
        try {
            instance = Class.forName(packageName+ "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
