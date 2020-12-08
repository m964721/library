package com.demo.application.mvp.imp;

import com.demo.application.mvp.base.BaseModeToView;

public interface ViewImp extends BaseModeToView {
    /**
     * 位置请求成功后回调
     * @param isSucceed：请求位置是否成功
     * @param data：如果成功，则返回对应的位置信息
     */
    void onPositionCallback(boolean isSucceed, String data);
}
