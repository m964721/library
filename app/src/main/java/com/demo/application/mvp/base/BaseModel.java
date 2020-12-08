package com.demo.application.mvp.base;

import com.demo.application.networklibrary.model.MessageInfo;

public interface BaseModel {
    void callCancel();//停止请求
    void initCallData(String token, BaseModeToView baseModeToView, String application,
                      boolean isNeedLoading, final MessageInfo... params);//请求数据
}
