package com.demo.application.mvp.base;

import java.lang.ref.WeakReference;

//P中包含v和m，通过P实现数据交换
public class BasePresenter {
    protected BaseModel baseModel ;
    protected WeakReference<BaseModeToView> weakReference ;//弱引用
}
