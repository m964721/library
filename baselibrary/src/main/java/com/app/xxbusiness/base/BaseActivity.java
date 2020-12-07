package com.app.xxbusiness.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.app.networklibrary.config.NetworkConfig;
import com.app.networklibrary.dialog.NetWork_Loading;
import com.app.networklibrary.model.MessageInfo;
import com.app.networklibrary.retrofitUtils.AllRequestApplication;
import com.app.toolboxlibrary.AppInfoProvider;
import com.app.toolboxlibrary.IPUtils;
import com.app.toolboxlibrary.ImageViewUtils;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.StringUtils;
import com.app.mvp.imp.PresentImp;
import com.app.mvp.imp.ViewImp;
import com.app.download.AppApkUpdateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by PPH on 18/2/27.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class BaseActivity extends FragmentActivity implements ViewImp {
    PresentImp presentimp;//数据交互
    private NetWork_Loading progressDialog;//提示loding窗
    protected static List<Activity> activities = new ArrayList<>();//存放启动的Activity
    public BaseActivity CTX;
    public String showHintData = "";//loading提示窗的文字
    private AppApkUpdateUtil appApkUpdateUtil;//apk下载util
    AllRequestApplication allRequestApplication ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CTX = this;
        activities.add(this);
        presentimp = new PresentImp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != activities && activities.size() > 0) {
            activities.remove(CTX);
        }
        if(null != appApkUpdateUtil){
            appApkUpdateUtil.onDestroy();
        }
        allRequestApplication = null ;
        System.gc();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onActivityStatusChange(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onActivityStatusChange(false);
    }

    /**
     * 执行和业务层交互,不带isNeedLoading，默认有加载框，
     * 带参数的根据所传字段isNeedLoading，判断是否需要加载窗
     *
     * @param application
     * @param params
     */
    public void noNeedLoadRequest(String application, MessageInfo... params) {
        initCallData(application, false, params);
    }

    public void needLoadRequest(String application, MessageInfo... params) {
        initCallData(application, true, params);
    }

    //初始化网络请求数据
    private void initCallData(String application, boolean isNeedLoading, MessageInfo... params) {
        if (isWifiCanUse())
            presentimp.sendRequest(getMyUserInfo().getToken(), application, isNeedLoading, params);
    }


    //校验网络
    private boolean isWifiCanUse() {
        if (IPUtils.isWifiProxy(CTX)) {
            showToast("网络环境不安全,请稍后重试!");
            return false;
        } else {
            return true;
        }
    }

    /***
     * 接受数据成功返回后处理
     * @param application
     * @param resultData
     */
    public void doAfterRequestSuccess(String application, String resultData) {
        LogUtil.showLog("doAfterRequestSuccess","application:"+application+"\n 请求数据:"+resultData);
        if (backAllRequest().getNewLogin() == application) {
            JSONObject json = null;
            try {
                json = new JSONObject(resultData);
                if(null != json ){
                    String url = json.getString("url");
                    if(!StringUtils.isStringToNUll(""+url)){
                       String[] list = url.split("=");
                       String poker = list[1] ;
                       int index = poker.indexOf("&");
                       if(index>-1){
                           poker = poker.substring(0,index) ;
                           needLoadRequest(backAllRequest().getPokerToToken(),
                                   new MessageInfo("poker",poker)
                                   );
                       }
                       //?poker=${poker}
                       LogUtil.printInfo("poker",poker);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(backAllRequest().getPokerToToken().equals(application)){
            getMyUserInfo().setLogin(true);
            getMyUserInfo().saveObject("token",resultData);
            getQiNiuUrl();
        } else if (backAllRequest().getLogin().equals(application)) {
            //更新用户信息
            saveUserInfo(resultData);
            getMyUserInfo().setLogin(true);
            getQiNiuUrl();
        }else if(backAllRequest().getQiNiuUrl().equals(application)){
            getMyUserInfo().saveObject("getQiniuUrl", resultData);
            ImageViewUtils.getInstance().setUrl(resultData);
        }
    }

    //接口请求失败
    public void doAfterRequestFail(String application, Object resultCode, String data) {

    }

    /**
     * @return 无
     * @说明：各种异常操作 在这个基类里面统一处理
     */
    public void enterLoginPageActivity() {
        Class LoginPage = getClassToJump(getAllUiClassNameConfig().LoginActivity);
        String cl_name = "" + LoginPage.getName();
        String this_name = this.getClass().getName();
        if (!this_name.equals(cl_name)) {
            Bundle bundle = new Bundle();
            bundle.putString(XxBusinessConfig.AllStringJumpKey, XxBusinessConfig.AllStringJumpVaule);
            startBaseActivity(LoginPage, bundle, XxBusinessConfig.ABNORMAL_TO_LOGIN);
        }
    }

    /**
     * 显示提示框
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (!StringUtils.isStringToNUll(msg)) {
            XxBusiness.showToast(CTX, msg);
        }
    }

    /**
     * 请求显示的loading
     */
    public void showProgressDialog() {
        if (null == progressDialog) {
            progressDialog = new NetWork_Loading(this);
        }
        progressDialog.show();
        if (!StringUtils.isStringToNUll(showHintData)) {
            progressDialog.setShowHintTv(showHintData);
        }
    }

    /**
     * 关闭
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /***
     * 取消请求
     */
    protected void preDestroy() {
        if (null != presentimp) {
            presentimp.onDestroy(CTX);
        }
    }

    /**
     * 退出App关闭所有界面
     */
    public void exitClose() {
        try {
            for (int i = 0; i < activities.size(); i++) {
                if (null != activities.get(i)) {
                    activities.get(i).finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityStatusChange(boolean b) {
    }

    /***
     * 处理位置信息获取成功返回
     * @param isSucceed
     * @param data
     */
    @Override
    public void onPositionCallback(boolean isSucceed, String data) {

    }

    /**
     * 获取本地存储的个人信息
     */
    public XxLocalUserInfo getMyUserInfo() {
        return XxBusiness.getInstance().getMyUserInfo(CTX);
    }

    /**
     * 清除缓存
     */
    public void clearUserInfoCache() {
        XxBusiness.getInstance().clearCache(CTX);
    }


    /**
     * 开始定位
     */
    public void startBaseLocation(boolean isNeedLocation) {
        if (isNeedLocation) {
            if (null != presentimp) {
                presentimp.startLocation(CTX);
            }
        }
    }


    /**
     * 启动视图
     *
     * @param className 跳转界面
     */
    public void startBaseActivity(Class<?> className) {
        if (null != className) {
            startBaseActivity(className, null);
        }

    }

    /**
     * @param className 跳转界面
     * @param bundle    传递参数
     */
    public void startBaseActivity(Class<?> className, Bundle bundle) {
        if (null != className) {
            startBaseActivity(className, bundle, XxBusinessConfig.WILL_BE_CLOSED);
        }
    }

    /**
     * @param className   跳转界面
     * @param bundle      传递参数
     * @param requestCode 不适用默认的requestCode
     */
    public void startBaseActivity(Class<?> className, Bundle bundle, int requestCode) {
        if (null != className) {
            Intent intent = new Intent(CTX, className);
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != appApkUpdateUtil) {
            appApkUpdateUtil.onActivityResult(requestCode, resultCode, data);
        }
        if (XxBusinessConfig.WILL_BE_CLOSED == resultCode) {
            String thisInstanceName = this.getClass().getName();
            Class activity = getClassToJump(getAllUiClassNameConfig().MainActivity);
            if (!thisInstanceName.equals(activity.getName())) {
                //非首页关闭
                setResult(XxBusinessConfig.WILL_BE_CLOSED, data);
                finish();
            }
        }else if(XxBusinessConfig.ABNORMAL_AFTER_LOGIN == resultCode){
            afterUserLoginToRefreshUi();
        }
    }

    //根据Activity文件名获取activity实例
    public Class getClassToJump(String classNameByAndroid) {
        Class classname = AppInfoProvider.getClasses(this, classNameByAndroid);
        return classname;
    }

    //获取跳转界面的config对象，
    public AllUiClassNameConfig getAllUiClassNameConfig() {
        return AllUiClassNameConfig.getInstance();
    }


    /**
     * 初始化下载apk工具类
     *
     * @param url      下载地址
     * @param fileName 文件存放目录(文件夹的名字)
     */
    public void initAppApkUpdateUtil(String fileName, String url ,boolean must) {
        appApkUpdateUtil = new AppApkUpdateUtil.Builder()
                .setApkUrl(url)
                .setApkFilePath(fileName)
                .setMust(must)
                .setOnDownResult(onDownResult)
                .build(CTX);

        appApkUpdateUtil.showDownDialog();
    }

    AppApkUpdateUtil.OnDownResult onDownResult = new AppApkUpdateUtil.OnDownResult() {
        @Override
        public void OnDownResult(boolean isDownLoad) {

        }

        @Override
        public void OnInstallResult(boolean isInstall) {

        }
    };

    //返回所有接口请求url的对象
    public AllRequestApplication backAllRequest(){
        if(null == allRequestApplication){
            allRequestApplication = AllRequestApplication.getInstance();
        }
        return allRequestApplication ;
    }

    //获取天气
    public void getTodayWeather(String currentCity){
        noNeedLoadRequest(
                backAllRequest().getWeatherToday(),
                new MessageInfo("url", "weather/today/"+currentCity
                )
        );
    }

    //获取IP
    public void getAppIp(){
        noNeedLoadRequest(
                backAllRequest().getGetAppIp(),
                new MessageInfo("url",
                        "https://restapi.amap.com/v3/ip?output=json&key=f861eec5dab6498d1a1a6ceff795fb7e"
                )
        );
    }

    //获取七牛云地址
    public void getQiNiuUrl(){
        noNeedLoadRequest(
                backAllRequest().getQiNiuUrl()
        );
    }

    //登录
    public void toLogin(String phone,String pw){
        needLoadRequest(backAllRequest().getNewLogin(),
                new MessageInfo("account",phone),
                new MessageInfo("password",pw),
                new MessageInfo("redirectUrl",NetworkConfig.getDoMain()),
                new MessageInfo("environmentPrefix",NetworkConfig.EnvironmentPrefix),
                new MessageInfo("serviceId","3")
        );
    }

    //登陆党建cms
    public void toLoginCMS(){
        noNeedLoadRequest(backAllRequest().getCmsLogin(),
                new MessageInfo("account","jl004"),
                new MessageInfo("password","123456")
                );
    }

    //存储信息
    public void saveUserInfo(String resultData) {
        try {
            if (!StringUtils.isStringToNUll(resultData)) {
                JSONObject userInfo = new JSONObject(resultData);
                saveInfo(userInfo, "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //保存数据
    private void saveInfo(JSONObject userInfo, String saveKey) throws JSONException {
        if (null != userInfo) {
            Iterator<String> userInfoIterator = userInfo.keys();
            while (userInfoIterator.hasNext()) {
                // 获得key
                String key = userInfoIterator.next();
                String value = userInfo.getString(key);
                getMyUserInfo().saveObject(saveKey + key, value);
            }
        }
    }

    public void afterUserLoginToRefreshUi(){

    }
}
