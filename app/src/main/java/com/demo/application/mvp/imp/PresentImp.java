package com.demo.application.mvp.imp;

import android.content.Context;

import com.demo.application.networklibrary.model.MessageInfo;
import com.demo.application.mvp.base.BaseModeToView;
import com.demo.application.mvp.base.BasePresenter;
import com.demo.application.location.LocationCallback;
import com.demo.application.location.LocationUtils;

import java.lang.ref.WeakReference;

public class PresentImp extends BasePresenter {

    public PresentImp(ViewImp viewImp) {
        this.baseModel = new ModeImp();
        this.weakReference = new WeakReference<BaseModeToView>(viewImp);
    }

    //网络请求
    public void sendRequest(String token ,String application, boolean isNeedLoading, MessageInfo... params) {
        ViewImp viewImp = (ViewImp) weakReference.get();
        baseModel.initCallData(token,viewImp, application, isNeedLoading, params);
        viewImp = null;
    }

    //销毁部分数据
    public void onDestroy( Context context ) {
        if (null != baseModel) {
            baseModel.callCancel();
        }
        LocationUtils.getInstance().destroyLocation();
    }


    //定位方法
    public void startLocation( Context context ){
        LocationUtils.getInstance().initLocation( context , new LocationCallback() {
            @Override
            public void onPositionCallback( boolean isSucceed, Object data ) {
                ViewImp viewImp = (ViewImp) weakReference.get();
                viewImp.onPositionCallback(isSucceed,(String)data);
                viewImp = null;
            }
        });
    }

}
