package com.app.toolboxlibrary;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;


/**
 * Created by PPH on 16/10/26.
 */

public class IntentDataUtils {

    /**
     * 解析界面跳转携带的数据
     * @param data getIntent需要解析的对象
     * @param stringType 为所需要解析的字段名
     * @return
     */
    public static String getStringData(Intent data, String stringType) {
        if (null != data) {
            if (data.hasExtra(stringType)) {
                return data.getExtras().getString(stringType);
            }
        }
        return "";
    }
    /**
     * 解析界面跳转携带的数据
     * @param data getIntent需要解析的对象
     * @param stringType 为所需要解析的字段名
     * @return
     */
    public static int getIntData(Intent data, String stringType) {
        if (null != data) {
            if (data.hasExtra(stringType)) {
                return data.getExtras().getInt(stringType);
            }
        }
        return 0;
    }
    /**
     * 解析Serializable类型的数据
     * @param intent 传入需要解析的intent
     * @param type 解析字段
     * @return
     */
    public static Serializable getSerializableData(Intent intent , String type){
        Serializable serializable = null ;
        if(null != intent){
            if(intent.hasExtra(type)){
                Bundle bd = intent.getExtras();
                serializable = bd.getSerializable(type);
            }
        }
        return serializable ;
    }

}
