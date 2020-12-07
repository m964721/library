package com.app.networklibrary.retrofitUtils;

import com.app.networklibrary.config.NetStatusCode;
import com.app.toolboxlibrary.LogUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PPX
 * on 2016/10/20.
 * 重写callback，组装数据
 */

public abstract class RetrofitCallback<M> implements Callback<M> {

    public abstract void onSuccess(Map<String,Object> backData);

    public abstract void onFailure(int code, String msg);

    public abstract void onThrowable(Throwable t);

    public abstract void onFinish();

    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        String url = response.raw().request().url().toString() ;
        if (response.isSuccessful()) {
            Map<String,Object> backOther = new HashMap<>();
            backOther.put(NetStatusCode.requestUrl,url);
            backOther.put(NetStatusCode.dataKey,response.body());
            onSuccess(backOther);
        } else {
            onFailure(response.code(), response.errorBody().toString());
        }
        onFinish();
    }

    @Override
    public void onFailure(Call<M> call, Throwable t) {
        onThrowable(t);
        onFinish();
    }
}
