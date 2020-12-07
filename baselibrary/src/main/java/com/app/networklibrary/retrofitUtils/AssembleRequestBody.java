package com.app.networklibrary.retrofitUtils;

import com.app.networklibrary.model.MessageInfo;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 拼装你的数据
 */
public class AssembleRequestBody {

    //post请求组装报文
    public static RequestBody assembleRequestBody(MessageInfo... mapParam) {
        RequestBody assembleRequestBody = null;
        FormBody.Builder builder = new FormBody.Builder();
        if (null != mapParam) {
            for (MessageInfo messageInfo : mapParam) {
                builder.add(messageInfo.getKey(), "" + messageInfo.getValue());
            }
            assembleRequestBody = builder.build();
        }
        return assembleRequestBody;
    }

    //post请求组装报文返回string
    public static String jsonPostData(List<MessageInfo> list) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (null != list) {
                for (MessageInfo messageInfo : list) {
                    if (!"requestApplication".equals(messageInfo.getKey())) {
                        jsonObject.put(messageInfo.getKey(), messageInfo.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.showLog("jsonPostData 请求参数：", "" + jsonObject.toString());
        return jsonObject.toString();
    }


    //post请求组装报文返回string
    public static String jsonPostData(MessageInfo... mapParam) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (null != mapParam) {
                for (MessageInfo messageInfo : mapParam) {
                    if (!"requestApplication".equals(messageInfo.getKey())) {
                        jsonObject.put(messageInfo.getKey(), messageInfo.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.showLog("jsonPostData 请求参数：", "" + jsonObject.toString());
        return jsonObject.toString();
    }

    //返回get请求的报问体
    public static String backMapToStringData(MessageInfo... mapParam) {
        String mapToString = "";
        if (null != mapParam) {
            for (MessageInfo messageInfo : mapParam) {
                mapToString = messageInfo.getKey() + "=" + messageInfo.getValue() + "&";
            }
        }
        mapToString = "?" + mapToString;
        LogUtil.showLog("GetData 请求参数：", "mapToString:  " + mapToString);
        return mapToString;
    }

    //get请求返回数据单个
    public static Object backValue(String key, MessageInfo... mapParam) {
        Object object = new Object();
        if (null != mapParam) {
            for (MessageInfo messageInfo : mapParam) {
                LogUtil.showLog("backValue", "object:    " + object);
                if (messageInfo.getKey().equals(key)) {
                    object = messageInfo.getValue();
                }
            }
        }
        return object;
    }

    //返回get请求的报问体map
    public static Map<String, Object> backMap(MessageInfo... mapParam) {
        Map<String, Object> map = new HashMap<>();
        if (null != mapParam) {
            for (MessageInfo messageInfo : mapParam) {
                if (!messageInfo.getKey().equals("token")&&!StringUtils.isStringToNUll("" + messageInfo.getValue())) {
                    map.put(messageInfo.getKey(), messageInfo.getValue());
                }
            }
        }
        return map;
    }


    /**
     * 下面方法根据实际情况自行处理
     * @param mapParam
     * @return
     */
    //图片上传单张
    public static MultipartBody multiPartBody(MessageInfo... mapParam) {
        MultipartBody requestBody = null;
        String imgTempName = "";
        for (MessageInfo messageInfo : mapParam) {
            if (messageInfo.key.equals("file")) {
                imgTempName = (String) messageInfo.value;
            }
        }
        if (null != imgTempName && imgTempName.length() > 0) {
            //判断文件类型
            MediaType imageEnvType = MediaType.parse(judgeType(imgTempName));
            //创建文件参数
            MultipartBody.Builder builderMultipartBody = new MultipartBody.Builder();
            builderMultipartBody.setType(MultipartBody.FORM);
            for (MessageInfo messageInfo : mapParam) {
                if (!messageInfo.key.equals("file")) {
                    builderMultipartBody.addFormDataPart("" + messageInfo.key, "" + messageInfo.value);
                } else {
                    builderMultipartBody.addFormDataPart("" + messageInfo.key,
                            new File(imgTempName).getName(),
                            RequestBody.create(imageEnvType, new File(imgTempName)));
                }
            }
            requestBody = builderMultipartBody.build();
        }
        return requestBody;
    }

    //图片上传多张
    public static MultipartBody listMultiPartBody(List<MessageInfo> mapParam) {
        MultipartBody requestBody = null;
        //创建文件参数
        MultipartBody.Builder builderMultipartBody = new MultipartBody.Builder();
        builderMultipartBody.setType(MultipartBody.FORM);
        for (MessageInfo messageInfo : mapParam) {
            if (!messageInfo.key.equals("file")) {
                builderMultipartBody.addFormDataPart("" + messageInfo.key,
                        "" + messageInfo.value);
            } else {
                String imgTempName = (String) messageInfo.value;
                //判断文件类型
                MediaType imageEnvType = MediaType.parse(judgeType(imgTempName));
                File file = new File(imgTempName) ;
                builderMultipartBody.addFormDataPart("" + messageInfo.key,
                        file.getName(),
                        RequestBody.create(imageEnvType, file));
            }
        }
        requestBody = builderMultipartBody.build();
        return requestBody;
    }


    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    public static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        LogUtil.showLog("judgeType", "contentTypeFor:" + contentTypeFor);
        return contentTypeFor;
    }
}
