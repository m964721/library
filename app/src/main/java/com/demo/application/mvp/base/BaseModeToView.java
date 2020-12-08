package com.demo.application.mvp.base;

//数据回传，由p传至v
public interface BaseModeToView {
    //请求失败
    void doAfterRequestFail(String application, Object resultCode, String data);

    //请求成功
    void doAfterRequestSuccess(String application, String resultData);

    //弹窗
    void showProgressDialog();

    //关闭
    void dismissProgressDialog();

    //弹窗
    void showToast(String showData);

    //异常
    void enterLoginPageActivity();
}
