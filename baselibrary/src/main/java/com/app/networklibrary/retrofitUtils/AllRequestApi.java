package com.app.networklibrary.retrofitUtils;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 这里声明你请求的具体路径xxx/xxxx/xxx
 * 第一个下表位置不要出现/
 * 不会的自行百度
 */
public interface AllRequestApi {

    //GET请求
    @GET
    @Headers({"Content-type:application/json"})
    Call<String> getRequest(
            @Url String url,
            @Header("token") String accessToken,
            @QueryMap Map<String, Object> map);

    //POST请求
    @POST
    @Headers({"Content-type:application/json"})
    Call<String> postRequest(
            @Url String url,
            @Header("token") String accessToken,
            @Body String params
            );

    //POST请求
    @POST("/user-center-client/login/in")
    @Headers({"Content-type:application/json"})
    Call<String> postLoginRequest(
            @Body String params
    );

    //资讯详情
    @GET("cms/article/{articleId}?nativeFlag=1")
    @Headers({"Content-type:application/json"})
    Call<String> articleId(@Header("token") String accessToken , @Path("articleId") String articleId);

    //登录
    @POST("app/login")
    @Headers({"Content-type:application/json"})
    Call<String> userLogin(@Body String param);

}
