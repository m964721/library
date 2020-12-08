package com.demo.application.location;


/**
 * Created by jun on 17/2/22.
 * 业务层回调方法
 */

public interface LocationCallback {

    /**
     * 位置请求成功后回调
     * @param isSucceed：请求位置是否成功
     * @param data：如果成功，则返回对应的位置信息
     */
    void onPositionCallback(boolean isSucceed, Object data);
}
