package com.demo.application.networklibrary.okhttp;


import com.demo.application.networklibrary.model.MessageInfo;
import com.app.toolboxlibrary.LogUtil;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 单纯地okhttp使用
 */

public class OkHttpUtils {

    //设置请求
    private static void netOkHttpClient(Request request,
                                        final MyRequestCallBack myRequestCallBack,
                                        final String application) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                .connectTimeout(60, TimeUnit.SECONDS) //设置超时
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                myRequestCallBack.onRequestFailure(application, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String backMessage = response.body().string();
                    LogUtil.showLog("callback", "" + backMessage);
                    myRequestCallBack.onRequestSuccess(application, backMessage);
                } catch (Exception e) {

                }
                call.cancel();
            }
        });

    }

    //返回get请求的报问体
    public static String backMapToStringData(List<MessageInfo> mapParam) {
        String mapToString = "";
        if (null != mapParam && mapParam.size() > 0) {
            for (int i = 0; i < mapParam.size(); i++) {
                if (i > 0) {
                    mapToString = mapToString + "&" + mapParam.get(i).getKey() + "="
                            + mapParam.get(i).getValue();
                } else {
                    mapToString = mapParam.get(i).getKey() + "=" + mapParam.get(i).getValue();
                }
            }
        }

        return mapToString;
    }

    //file文件
    public static void sendFile(String ocrBankCardurl,
                                   String imgTempName,
                                   String api_key,
                                   String api_secret,
                                   MyRequestCallBack myRequestCallBack,
                                   String application
    ) {
        if (null != imgTempName && imgTempName.length() > 0) {
            //判断文件类型
            MediaType imageEnvType = MediaType.parse(judgeType(imgTempName));
            //创建文件参数
            MultipartBody.Builder builderMultipartBody = new MultipartBody.Builder();
            builderMultipartBody.setType(MultipartBody.FORM);
            builderMultipartBody.addFormDataPart("api_key", api_key);
            builderMultipartBody.addFormDataPart("api_secret", api_secret);
            builderMultipartBody.addFormDataPart("image_file", new File(imgTempName).getName(),
                    RequestBody.create(imageEnvType, new File(imgTempName)));
            RequestBody requestBody = builderMultipartBody.build();
            //发出请求参数
            postRequest(ocrBankCardurl, requestBody, myRequestCallBack, application);
        }
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
        return contentTypeFor;
    }


    //Get请求
    public static void getRequest(String getUrl,
                                  MyRequestCallBack myRequestCallBack,
                                  String application
    ) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(getUrl).build();
        //发出请求参数
        netOkHttpClient(request, myRequestCallBack, application);
    }

    //post请求
    public static void postRequest(String postUrl,
                                   RequestBody requestBody,
                                   MyRequestCallBack myRequestCallBack,
                                   String application) {

        Request.Builder builder = new Request.Builder();
        Request request = builder.
                post(requestBody).
                url(postUrl).
                build();
        //发出请求参数
        netOkHttpClient(request, myRequestCallBack, application);
    }

    //post请求组装报文
    public static RequestBody assembleRequestBody(List<MessageInfo> mapParam) {
        RequestBody assembleRequestBody = null;
        FormBody.Builder builder = new FormBody.Builder();
        if (null != mapParam && mapParam.size() > 0) {
            for (MessageInfo requestInfo : mapParam) {
                builder.add(requestInfo.getKey(),(String) requestInfo.getValue());
            }
            assembleRequestBody = builder.build();
        }
        return assembleRequestBody;
    }
}
