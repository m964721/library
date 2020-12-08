package com.demo.application.networklibrary.okhttp;

import java.io.IOException;

public abstract class MyRequestCallBack {
    /**
     * 请求成功
     */
    public abstract void onRequestSuccess(String application,String backData);

    /**
     * 连接服务器失败，或者请求异常（发送请求失败）
     */
    public abstract void onRequestFailure(String application,IOException e);
}
