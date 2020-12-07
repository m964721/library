package com.app.mvp.imp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.app.networklibrary.config.NetStatusCode;
import com.app.networklibrary.model.MessageInfo;
import com.app.networklibrary.retrofitUtils.AllRequestApplication;
import com.app.networklibrary.retrofitUtils.RetrofitCallback;
import com.app.toolboxlibrary.JsonUtils;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.StringUtils;
import com.app.mvp.base.BaseModeToView;
import com.app.mvp.base.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.amap.api.mapcore2d.q.t;

//继承M，具体操作
public class ModeImp implements BaseModel {

    private BaseModeToView baseModeToView;//数据交互接口
    private static List<Call> callList = new ArrayList<>();//存放网络请求call对象\

    //组装网络请求数据
    public void initCallData(String appToken, BaseModeToView baseModeToView,
                             String application, boolean isNeedLoading, MessageInfo... params) {
        this.baseModeToView = baseModeToView;
        if (isNeedLoading) {
            baseModeToView.showProgressDialog();
        }
        Call<String> call = AllRequestApplication.getInstance().backCall(appToken, application, params);
        sendCallToRequest(call,application);
    }
    //发送网络请求
    private void sendCallToRequest(final Call<String> call , final String application) {
        if (null == call) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                call.enqueue(new RetrofitCallback<String>() {
                    @Override
                    public void onSuccess(Map<String, Object> backOther) {
                        if (null != backOther) {
                            backOther.put("application", application);
                        }
                        Message msg = new Message();
                        msg.what = NetStatusCode.NETREQUESTSUCCESS;
                        msg.obj = backOther;
                        baseHandler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Map<String, Object> map = new HashMap<>() ;
                        map.put("application", application);
                        map.put("msg",msg);
                        map.put("code",code);
                        Message message = new Message();
                        message.what = NetStatusCode.NETREQUESTFailure;
                        message.obj = map;
                        baseHandler.sendMessage(message);
                        map = null ;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        Map<String, Object> map = new HashMap<>() ;
                        map.put("application", application);
                        map.put("msg",t);
                        Message messageT = new Message();
                        messageT.what = NetStatusCode.NETREQUESTThrowable;
                        messageT.obj = map;
                        baseHandler.sendMessage(messageT);
                        map = null ;
                    }

                    @Override
                    public void onFinish() {
                        Message messageF = new Message();
                        messageF.what = NetStatusCode.NETREQUESTFinish;
                        baseHandler.sendMessage(messageF);
                    }
                });
                addCalls(call);
            }
        }.start();
    }

    //处理请求数据
    private Handler baseHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NetStatusCode.NETREQUESTSUCCESS:
                    requestSuccessFocusProcessing(msg.obj);
                    break;
                case NetStatusCode.NETREQUESTFailure:
                    requestFail(msg.obj);
                    break;
                case NetStatusCode.NETREQUESTThrowable:
                    handleThrowble(msg.obj);
                    break;
                case NetStatusCode.NETREQUESTFinish:
                    baseModeToView.dismissProgressDialog();
                    break;
                default:
                    break;
            }
        }
    };


    //处理返回数据
    private void requestSuccessFocusProcessing(Object successData) {

        try {
            Map<String, Object> backDataMap = (Map<String, Object>) successData;
            String application = (String) backDataMap.get(NetStatusCode.applicationKey);//请求applicaton参数
            String backData = (String) backDataMap.get(NetStatusCode.dataKey);//返回数据
            String requestUrl = (String) backDataMap.get(NetStatusCode.requestUrl);//返回的请求地址
            LogUtil.showLog("requestUrl",""+requestUrl);
            if (!StringUtils.isStringToNUll(backData)) {
                JSONObject jsonObject = new JSONObject(backData);
                if (null != jsonObject) {
                    //获取ip定位
                    boolean backAppIp = "getAppIp".equals(application)&&"1".equals(jsonObject.getString("status")) ;
                    String code = JsonUtils.getValueFromJSONObject(jsonObject, NetStatusCode.codeKey);
                    if (NetStatusCode.SUCCESSSTATUS.equals(code)||
                            NetStatusCode.SUCCESS200.equals(code)||
                            backAppIp
                    ) {
                        if (initShowToast(application)) {
                            baseModeToView.showToast(JsonUtils.getValueFromJSONObject(jsonObject, NetStatusCode.netToastMessageKey));
                        }
                        baseModeToView.doAfterRequestSuccess(application,
                                backAppIp?jsonObject.toString():JsonUtils.getValueFromJSONObject(jsonObject, NetStatusCode.dataKey)
                        );
                    } else {
                        baseModeToView.showToast(JsonUtils.getValueFromJSONObject(jsonObject, NetStatusCode.netToastMessageKey));
                        baseModeToView.doAfterRequestFail(application, code, jsonObject.toString());
                        if (NetStatusCode.ToLoginStatus.equals(code)) {
                            baseModeToView.enterLoginPageActivity();
                        }
                    }
                } else {
                    baseModeToView.showToast("请求失败!");
                }
            } else {
                baseModeToView.showToast("请求失败!");
            }
            backDataMap = null ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //处理请求失败
    void requestFail(Object failData){
        Map<String, Object> backDataMap = (Map<String, Object>) failData;
        baseModeToView.doAfterRequestFail(
                ""+backDataMap.get("application"),
                backDataMap.get("code"),
                ""+backDataMap.get("msg")
        );
        baseModeToView.showToast("请求失败!");
        backDataMap = null ;
    }

    /****
     * 各种异常情况处理
     * @param
     * @param
     */
    private void handleThrowble(Object ThrowableData) {
        String respDesc = "";
        String respCode = "";
        Map<String, Object> backDataMap = (Map<String, Object>) ThrowableData;
        Throwable t = (Throwable) backDataMap.get("msg");
        String application = ""+backDataMap.get("application");
        if (t instanceof java.net.SocketTimeoutException) {
            respCode = NetStatusCode.NET_TIMEOUT_STATUS;
            respDesc = NetStatusCode.NET_TimeOut;
        } else if (t instanceof java.net.UnknownHostException || t instanceof FileNotFoundException) {
            respCode = NetStatusCode.NET_UNKNOWN_HOST_STATUS;
            respDesc = NetStatusCode.Net_UnKnow_Host;
        } else {
            respCode = NetStatusCode.NET_UNKNOWN_ERROR_STATUS;
            respDesc = t.getMessage();
        }
        baseModeToView.showToast("网络异常!");
        baseModeToView.doAfterRequestFail(application, respCode, respDesc);
        backDataMap = null ;
    }


    /****
     * 添加请求
     * @param call
     */
    private void addCalls(Call call) {
        if (null == callList) {
            callList = new ArrayList<>();
        }
        callList.add(call);
    }

    /***
     * 取消请求
     */
    public void callCancel() {
        if (null != callList && callList.size() > 0) {
            for (Call call : callList) {
                if (null !=call && !call.isCanceled())
                    call.cancel();
            }
            callList.clear();
        }
    }

    //过滤部分需要弹窗提示
    private String needShowTost[] = {

    };

    //部分接口（更新用户数据的）是否需要弹窗
    private boolean initShowToast(String application) {
        boolean isNeed = false;
        for (String needString : needShowTost) {
            if (-1 != application.indexOf(needString)) {
                isNeed = true;
            }
        }
        return isNeed;
    }

}
