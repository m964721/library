package com.app.toolboxlibrary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * FastJSON工具类
 */
public class FastJsonUtils {

    //json数据转为bean对象
    public static Object jsonToBean(String jsonData, Class T) {
        if (!StringUtils.isStringToNUll(jsonData)) {
            return JSON.parseObject(jsonData, T);
        }
        return null ;
    }

    //bean对象转为JSON对象;
    public static String beanToJSON(Object beanClass) {
        if(null != beanClass){
            return JSON.toJSONString(beanClass);
        }
        return null;
    }

    //返回JSON数据中的JSONArray对象
    public static JSONArray backJSONArray(JSONObject jsonObject, String key) throws Exception {
        JSONArray array = null;
        if (null != jsonObject && !StringUtils.isStringToNUll(key)) {
            array = jsonObject.getJSONArray(key);
        }
        return array;
    }

    //将JSON数组转换为java对象的集合
    public static List backList(String jsonData, Class T) throws Exception {
        if (!StringUtils.isStringToNUll(jsonData)) {
            return JSON.parseArray(jsonData, T);
        }
        return null;
    }

    //返回JSON数据中的JSONArray对象
    public static String listToJSONArray(List list) {
        if(null!=list &&list.size()>0){
            String array =  JSON.toJSONString(list);
            return array;
        }
        return "" ;
    }

}
