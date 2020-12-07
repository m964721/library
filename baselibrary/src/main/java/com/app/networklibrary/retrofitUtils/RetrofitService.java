package com.app.networklibrary.retrofitUtils;

/**
 * Created by PPX on 16/10/21.
 * 继承，重写
 */

public class RetrofitService extends BaseService {

    public RetrofitService(String netUrl,String domain) {
        setUrl(netUrl);
        setDomain(domain);
    }

    public AllRequestApi onCreateAllRequestApi() {
        return baseJsonRetrofit().create(AllRequestApi.class);
    }

}