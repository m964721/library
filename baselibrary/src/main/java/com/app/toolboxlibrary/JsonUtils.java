package com.app.toolboxlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * json解析工具 org.json.JSONObject
 */
public class JsonUtils {

    //格式化json
    public static JSONObject stringToJson(String jsonData) throws JSONException {
        JSONObject jsonObject = null;
        if (!StringUtils.isStringToNUll(jsonData)) {
            jsonObject = new JSONObject(jsonData);
        }
        return jsonObject;
    }

    //返回jsonarray对象
    public static JSONArray backJSONArrayValue(JSONObject jsonObject, String key)
            throws JSONException {
        if (null != jsonObject) {
            if (jsonObject.has(key)) {
                return jsonObject.getJSONArray(key);
            }
        }
        return new JSONArray();
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String key) throws JSONException {
        JSONObject result = null;
        if (null != jsonObject && jsonObject.has(key)) {
            if (("" + jsonObject.get(key)).startsWith("{")
                    && ("" + jsonObject.get(key)).endsWith("}")) {
                result = jsonObject.getJSONObject(key);
            }
        }
        return result;
    }

    public static String getValueFromJSONObject(JSONObject jsonObject,
                                                String key) throws JSONException {
        String result = "";
        if (null != jsonObject && jsonObject.has(key)) {
            result = jsonObject.getString(key);
        }
        return result;
    }

    public static int getIntValueFromJSONObject(JSONObject jsonObject,
                                                String key) throws JSONException {
        int result = 0;
        if (null != jsonObject && jsonObject.has(key)) {
            result = jsonObject.getInt(key);
        }
        return result;
    }

    public static boolean getBooleanValueFromJSONObject(JSONObject jsonObject,
                                                        String key) throws JSONException {
        boolean result = false;
        if (null != jsonObject && jsonObject.has(key)) {
            result = jsonObject.getBoolean(key);
        }
        return result;
    }

    public static Object getObjectValueFromJSONObject(JSONObject jsonObject,
                                                      String key) throws JSONException {
        Object result = false;
        if (null != jsonObject && jsonObject.has(key)) {
            result = jsonObject.get(key);
        }
        return result;
    }

    // map转换为json字符串
    public static String hashMapToJson(HashMap map) {
        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
            Entry e = (Entry) it.next();
            string += "\"" + e.getKey() + "\":";
            string += "\"" + e.getValue() + "\",";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        return string;
    }

    /**
     * jsonarry remove 方法 支持 4.3及以下
     *
     * @param jsonObj
     * @param index
     * @return
     */
    public static JSONArray remove(JSONArray jsonObj, int index) {
        JSONArray mJsonArray = new JSONArray();

        if (index < 0) {
            return mJsonArray;
        }
        if (index > jsonObj.length()) {
            return mJsonArray;
        }

        for (int i = 0; i < index; i++) {
            try {
                mJsonArray.put(jsonObj.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = index + 1; i < jsonObj.length(); i++) {
            try {
                mJsonArray.put(jsonObj.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mJsonArray;
    }

    /**
     * 根据JSONArray删除相同字段名的内容
     *
     * @return 返回修改后的数据
     * @throws JSONException ： 解析异常
     * @说明：
     * @Parameters JSONArray destArray：目标数据 String fieldName：比较的字段名称 String
     * byValue ： 比较的字段内容
     */
    @SuppressLint("NewApi")
    public static JSONArray filterByJsonArrayFieldValue(JSONArray destArray,
                                                        String fieldName, String byValue) throws JSONException {
        for (int k = 0; k < destArray.length(); ) {
            // 0: 本地，1: 文件夹h5，2:第三方页面
            if (destArray.getJSONObject(k).getString(fieldName).equals(byValue)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    destArray.remove(k);
                } else {
                    destArray = JsonUtils.remove(destArray, k);
                }
                //
            } else {
                k++;
            }
        }
        return destArray;
    }

    /**
     * 根据字段名称来筛选，剔除掉相同的字段
     *
     * @return 修改后的数据
     * @说明：
     * @Parameters JSONArray destArray： 最终的目标数据 String fieldName ： 要比较的字段名称
     * JSONArray deleteArray ： 要删除的数据
     */
    @SuppressLint("NewApi")
    public static JSONArray deleteByJsonArrayField(JSONArray destArray,
                                                   String fieldName, JSONArray deleteArray) throws JSONException {
        for (int i = 0; i < deleteArray.length(); i++) {
            // 获取name ,然后在 txt里面匹配，然后删除
            JSONObject jode = deleteArray.getJSONObject(i);
            String name = jode.getString(fieldName);
            // 通过for 循环删除 不需要的 ，这个多重循环有点笨
            for (int j = 0; j < destArray.length(); j++) {
                JSONObject jo_ver = destArray.getJSONObject(j);
                if (jo_ver.getString(fieldName).equals(name)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        destArray.remove(j);
                    } else {
                        destArray = JsonUtils.remove(destArray, j);
                    }

                    LogUtil.showLog("TAG", "删除的是" + jo_ver.getString(fieldName));
                    break; // 找到就结束操作
                }
            }

        }
        return destArray;
    }


    /**
     * @return jsonArray
     * @说明：
     * @Parameters JSONObject 需要解析的json对象,key 需要解析的字段;
     */
    public static JSONArray getJSONArray(JSONObject jsonObject,
                                         String key) {
        JSONArray jsonArray = null;
        try {
            if (null != jsonObject && jsonObject.has(key) && null != jsonObject.getJSONArray(key)) {
                jsonArray = jsonObject.getJSONArray(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonArray = new JSONArray();
        }
        return jsonArray;
    }

    /**
     * @param context
     * @param fileName
     * @return //获取assets目录下的json文件数据
     */
    public static String getAssetsData(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String JsonData = stringBuilder.toString();
        return JsonData;
    }
}
