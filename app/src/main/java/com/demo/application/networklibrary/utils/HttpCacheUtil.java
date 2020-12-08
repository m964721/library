package com.demo.application.networklibrary.utils;

import android.content.Context;

import com.app.toolboxlibrary.FileUtiles;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 缓存http数据
 */
public class HttpCacheUtil {

    private static final String fileName = "/user/";//文件报名
    private static volatile HttpCacheUtil httpCacheUtil = null;//单例模式

    //单例模式
    public static HttpCacheUtil getHttpCacheUtil() {
        if (null == httpCacheUtil) {
            synchronized (HttpCacheUtil.class) {
                if (null == httpCacheUtil) {
                    httpCacheUtil = new HttpCacheUtil();
                }
            }
        }
        return httpCacheUtil;
    }

    //删除缓存
    public void clearSaveFile(Context context){
        if(null == context){
            return;
        }
        String directoryCacheFile = context.getCacheDir().getPath() + fileName;
        FileUtiles.deleteSaveFile(context,directoryCacheFile);
    }

    /**
     * 数据写入本地缓存
     *
     * @param context
     * @param application
     * @param data
     */
    public void saveCacheData(Context context, String application, String data) {
        if (null == context || StringUtils.isStringToNUll(application) || StringUtils.isStringToNUll(data)) {
            return;
        }
        String directoryCacheFile = context.getCacheDir().getPath() + fileName;
        File cacheFile = new File(directoryCacheFile);
        if (!cacheFile.exists()) {
            // 创建文件夹
            cacheFile.mkdir();
        }
        String cacheFileName = application + ".txt";
        LogUtil.showLog("saveCacheData","存入文件名 : "+ cacheFileName);
        File cacheDataFile = new File(directoryCacheFile + cacheFileName);
        try {
            FileOutputStream fileout = new FileOutputStream(cacheDataFile);
            Writer writer = new OutputStreamWriter(fileout);
            writer.write(data);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     *
     * @param context
     * @param application
     * @return
     */
    public String readCacheData( Context context, String application ) {
        if (null == context || StringUtils.isStringToNUll(application)) {
            return null;
        }
        String oldData = "";
        String directoryCacheFile = context.getCacheDir().getPath() + fileName;
        LogUtil.showLog("readCacheData","传入文件名 : "+application);
        String cacheFileName = application + ".txt";
        LogUtil.showLog("readCacheData","文件格式 : "+cacheFileName);
        File cacheDataFile = new File(directoryCacheFile + cacheFileName);
        if(!cacheDataFile.exists()){
            return null ;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(cacheDataFile);
            int length = fileInputStream.available();
            byte[] buffer = new byte[length];
            fileInputStream.read(buffer);
            oldData = new String(buffer, "UTF-8");
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oldData;
    }

}
