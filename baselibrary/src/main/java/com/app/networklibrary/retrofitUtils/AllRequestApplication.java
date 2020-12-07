package com.app.networklibrary.retrofitUtils;

import com.app.networklibrary.config.NetworkConfig;
import com.app.networklibrary.model.MessageInfo;
import com.app.toolboxlibrary.LogUtil;

import java.util.HashMap;

import lombok.Getter;
import retrofit2.Call;

/**
 * 负责分发网络请求
 * 需要并行的自行研究，
 */

@Getter
public class AllRequestApplication {
    private String newLogin = "user-center-client/login/in";//登陆
    private String pokerToToken = "userthird/poker";
    private String login = "user/login";//登陆
    private String qiNiuUrl = "oss/qiniu";//七牛地址
    private String homeBanner = "article/home/show";
    private String typeArticleList = "article/list";//资讯
    private String typeActivityList = "activity/list";//活动列表
    private String unreadCount = "app/message/unread/count";//未读
    private String blessingWallList = "birthday/cake/list";//生日祝福
    private String searchList = "article/search";//搜索
    private String getDetails = "getDetails";//获取资讯详情
    private String getActivityDetails = "getActivityDetails";//获取活动详情
    private String userInfo = "user/detail";//获取用户信息
    private String doMain = "user/domain" ;//获取租户信息
    private String getAppIp = "getAppIp" ;//获取ip
    private String weatherToday = "weather/today";//天气
    private String hotNews = "article/recommendation";//热门资讯
    private String IndexBanner = "cms/IndexBanner";//热门资讯分类
    private String IndexArticle = "cms/IndexArticle";//热门资讯列表
    private String cmsDetails = "cmsDetails";//资讯详情
    private String cmsLogin = "cmsLogin";//cms登陆
    private static volatile AllRequestApplication singleton = null;

    private AllRequestApplication() {}

    public static AllRequestApplication getInstance() {
        if (singleton == null) {
            synchronized (AllRequestApplication.class) {
                if (singleton == null) {
                    singleton = new AllRequestApplication();
                }
            }
        }
        return singleton;
    }

    //返回call对象
    public Call<String> backCall(String token, String application, MessageInfo... params) {
        Call<String> call;
        AllRequestApi allRequestApi = new RetrofitService( NetworkConfig.getHost(), NetworkConfig.getDoMain() ).onCreateAllRequestApi();
        LogUtil.showLog("backCall application",application);
        if (newLogin.equals(application)) {
            LogUtil.showLog("backCall application","1");
            allRequestApi = new RetrofitService( NetworkConfig.LoginUrl, NetworkConfig.getDoMain() )
                    .onCreateAllRequestApi() ;
            call = allRequestApi.postLoginRequest(AssembleRequestBody.jsonPostData(params));
        } else if (login.equals(application)) {
            LogUtil.showLog("backCall application","2");
            call = allRequestApi.postRequest(login,token, AssembleRequestBody.jsonPostData(params));
        } else if(getDetails.equals(application) ||
                getAppIp.equals(application)||
                weatherToday.equals(application)||
                getActivityDetails.equals(application)

        ){
            LogUtil.showLog("backCall application","3");
            call = allRequestApi.getRequest(
                    (String) AssembleRequestBody.backValue("url",params),
                    token,
                    new HashMap<String, Object>()
            );
        }else if(IndexBanner.equals(application)||
                IndexArticle.equals(application)
        ){
            LogUtil.showLog("backCall application","4");
            allRequestApi = new RetrofitService( NetworkConfig.PDUrl, NetworkConfig.getDoMain() )
                    .onCreateAllRequestApi() ;
            call = allRequestApi.getRequest(
                    application,
                    (String) AssembleRequestBody.backValue("token",params),
                    AssembleRequestBody.backMap(params)
            );
        }else if(cmsLogin.equals(application)){
            LogUtil.showLog("backCall application","5");
            allRequestApi = new RetrofitService( NetworkConfig.PDUrl, NetworkConfig.getDoMain() )
                    .onCreateAllRequestApi() ;
            call = allRequestApi.userLogin(AssembleRequestBody.jsonPostData(params));
        }else if(cmsDetails.equals(application)){
            LogUtil.showLog("backCall application","6");
            allRequestApi = new RetrofitService( NetworkConfig.PDUrl, NetworkConfig.getDoMain() )
                    .onCreateAllRequestApi() ;
            call = allRequestApi.articleId(
                    (String) AssembleRequestBody.backValue("token",params),
                    (String) AssembleRequestBody.backValue("articleId",params)
            );
        } else {
            LogUtil.showLog("backCall application","7");
            call = allRequestApi.getRequest(
                    application,
                    token,
                    AssembleRequestBody.backMap(params)
            );
        }
        return call;
    }

}
