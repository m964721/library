package com.app.mvp.base;

import com.app.networklibrary.model.MessageInfo;

import java.util.List;

public interface BaseModel {
    void callCancel();//停止请求
    void initCallData(String token, BaseModeToView baseModeToView, String application,
                      boolean isNeedLoading, final MessageInfo... params);//请求数据
}
