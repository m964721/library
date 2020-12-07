package com.app.xxbusiness.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.app.customviewlibrary.WaterMarkBg;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleView;
import com.app.customviewlibrary.refreshview.layout.BaseFooterView;
import com.app.customviewlibrary.refreshview.layout.BaseHeaderView;
import com.app.customviewlibrary.refreshview.layout.PullRefreshLayout;
import com.app.customviewlibrary.refreshview.view.FootView;
import com.app.customviewlibrary.refreshview.view.HeadView;
import com.app.networklibrary.model.MessageInfo;
import com.app.networklibrary.retrofitUtils.AllRequestApplication;
import com.app.networklibrary.utils.HttpCacheUtil;
import com.app.toolboxlibrary.AppInfoProvider;
import com.app.toolboxlibrary.DateUtil;
import com.app.toolboxlibrary.InfoDialogListener;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.PermissionUtils;
import com.app.toolboxlibrary.PhoneinfoUtils;
import com.app.toolboxlibrary.StringUtils;
import com.gobestsoft.applibrary.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Fragment 自行处理 感觉不爽的自己改AllBaseFragment 这里就不改了
 */
public class AllBaseFragment extends Fragment implements
        View.OnClickListener, View.OnFocusChangeListener {
    private Context fragmentContext;//外部传入的对象
    public static final int MIN_CLICK_DELAY_TIME = 300;
    private long lastClickTime = 0;
    private GetNetsDataToFragment dataToFragment;//数据交换方法
    private NeedOpenPermissionToStartActivity needOpenPermissionToStartActivity;//部分需要权限才能跳转Activity
    private OnAfterReadCache onAfterReadCache;//读取缓存之后处理数据,
    protected Bundle bundle = new Bundle();//数据传递
    protected boolean isReadFinish = false;//读取标志
    protected boolean isRequest = false;//判断当前fragmnet是否执行网络请求
    protected boolean isFirstAdd = false;//判断当前fragmnet是否第一次显示
    protected View rootView;//根视图

    public static final int REFRESHDATA = 0;//刷新数据
    public static final int LOADDATA = 1;//上拉加载更多
    public static final int STOPREFRESH = 2;//停止加载
    public static final int STOPLOAD = 4;//停止加载
    public static final int READDATAEND = 5;//停止加载
    protected boolean isRefresh = false;//下拉还是上拉加载更多,默认上拉加载更多
    protected String saveType = "";//部分需要id去区别不同的数据

    protected PermissionUtils permissionUtils;//权限管理
    public int widthPixels;
    public int heightPixels;
    public double density ;
    AllRequestApplication allRequestApplication  ;

    public PermissionUtils backPer() {
        return new PermissionUtils();
    }

    //调用刷新fragment方法
    public void onRefreshView(Object object) {

    }

    //调用刷新fragment方法
    public void onRefreshView(String type,Object o) {
        onRefreshView(type);
    }

    //调用刷新fragment方法
    public void startReadCache() {

    }


    //网络请求成功处理方法
    public void doAfterRequestSuccess(String application, String resultData) {
        setRequest(true);
        stopRefreshData();
        stopLoadData();
    }

    //网络请求失败处理方法
    public void doAfterRequestFail(String application, Object resultCode, Object data) {
        setRequest(true);
        stopRefreshData();
        stopLoadData();
    }

    //获取用户信息
    public XxLocalUserInfo getUserInfo() {
        return XxLocalUserInfo.getInstance(getActivity());
    }


    //检测是否登录
    protected boolean checkIsLogin() {
        if (!getUserInfo().isLogin()) {
            toJumpActivity(getClassInstanceByName(getAllUiClassNameConfig().LoginActivity
            ), null);
            return false;
        }
        return true;
    }

    /**
     * 防止重复点击
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        long currentTime = DateUtil.getMillis(new Date());
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
            return;
        } else {
            if (checkIsLogin()) {
                lastClickTime = currentTime;
                doClick(view);
            }
        }
    }

    public void doClick(View view) {

    }

    /**
     * 根据包名获取activity实例
     *
     * @param name 文件名
     * @return XXX.XXActivity.Class
     */
    public Class getClassInstanceByName(String name) {
        Class className = AppInfoProvider.getClasses(getActivity(), name);
        return className;
    }

    //获取跳转界面的config对象，
    public AllUiClassNameConfig getAllUiClassNameConfig() {
        return AllUiClassNameConfig.getInstance();
    }

    //跳转Activity
    public void toJumpActivity(Class className) {
        if (null != className) {
            toJumpActivity(className, null);
        }
    }

    //跳转Activity
    public void toJumpActivity(Class className, Bundle bundle) {
        if (null != className) {
            Intent intent = new Intent();
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            intent.setClass(getActivity(), className);
            getActivity().startActivityForResult(intent, XxBusinessConfig.BackToRefreFragment);
        }
    }

    //Fragment嵌套Fragment实现跳转，需要传入外部的context
    public void toJumpActivity(Context context, Class jumpClass) {
        Intent intent = new Intent();
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, jumpClass);
        ((Activity) context).startActivityForResult(intent, XxBusinessConfig.BackToRefreFragment);
    }

    public Context getFragmentContext() {
        return fragmentContext;
    }

    public void setFragmentContext(Context fragmentContext) {
        this.fragmentContext = fragmentContext;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public GetNetsDataToFragment getDataToFragment() {
        if (null == dataToFragment) {
            dataToFragment = new GetNetsDataToFragment() {
                @Override
                public void sendReq(String application, MessageInfo... messageInfos) {

                }
            };
        }
        return dataToFragment;
    }

    public void setDataToFragment(GetNetsDataToFragment dataToFragment) {
        this.dataToFragment = dataToFragment;
    }

    //fragment处理权限检测，交付至activity处理
    public NeedOpenPermissionToStartActivity getNeedOpenPermissionToStartActivity() {
        if (null == needOpenPermissionToStartActivity) {
            needOpenPermissionToStartActivity = new NeedOpenPermissionToStartActivity() {
                @Override
                public void NeedOpenPermissionAndActivityName(String activityName, String[] permissions, int requestCode) {

                }
            };
        }
        return needOpenPermissionToStartActivity;
    }

    public void setNeedOpenPermissionToStartActivity(NeedOpenPermissionToStartActivity needOpenPermissionToStartActivity) {
        this.needOpenPermissionToStartActivity = needOpenPermissionToStartActivity;
    }

    public AllBaseFragment.OnAfterReadCache getOnAfterReadCache() {
        if (null == onAfterReadCache) {
            onAfterReadCache = new OnAfterReadCache() {

                @Override
                public void OnAfterReadCache(int fragmentIndex, String fragmentType) {

                }
            };
        }
        return onAfterReadCache;
    }

    public void setOnAfterReadCache(AllBaseFragment.OnAfterReadCache onAfterReadCache) {
        this.onAfterReadCache = onAfterReadCache;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    //定义回调方法，由内传外使用网络请求
    public interface GetNetsDataToFragment {
        void sendReq( String application, MessageInfo... messageInfos );
    }

    //部分需要开启权限在执行跳转
    public interface NeedOpenPermissionToStartActivity {
        void NeedOpenPermissionAndActivityName(String activityName, String permissions[], int requestCode);
    }

    //读取缓存结束之后
    public interface OnAfterReadCache {
        //对应下标以及传递的数据
        void OnAfterReadCache(int fragmentIndex, String fragmentType);
    }

    /*************************************包含刷新的界面调用******************************************/
    protected PullRefreshLayout listDataPullrefreshlayout;//刷新布局
    private HeadView listDataHeadView;//刷新头部
    private FootView listDataFootView;//刷新尾部
    protected BaseRecycleView listDataRecycleView;//正常显示的listView

    //初始化
    protected void initRefreshView() {
        listDataPullrefreshlayout = rootView.findViewById(R.id.list_data_pullrefreshlayout);
        listDataHeadView = rootView.findViewById(R.id.list_data_headView);
        listDataFootView = rootView.findViewById(R.id.list_data_footView);
        listDataRecycleView = rootView.findViewById(R.id.list_data_recycleView);
        /**
         * 刷新方法
         */
        listDataHeadView.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
            @Override
            public void onRefresh(BaseHeaderView baseHeaderView) {
                isRefresh = true;
                baseHandler.sendEmptyMessage(REFRESHDATA);
            }
        });
        listDataFootView.setOnLoadListener(new BaseFooterView.OnLoadListener() {
            @Override
            public void onLoad(BaseFooterView baseFooterView) {
                isRefresh = false;
                baseHandler.sendEmptyMessage(LOADDATA);
            }
        });
    }

    //刷新数据
    public void startRefreshData() {

    }

    //上拉加载更多
    public void startLoadData() {

    }

    //停止刷新数据
    public void stopRefreshData() {
        baseHandler.sendEmptyMessage(STOPREFRESH);
    }

    //停止上拉加载更多
    public void stopLoadData() {
        baseHandler.sendEmptyMessage(STOPLOAD);
    }

    //配合刷新使用
    private Handler baseHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESHDATA:
                    startRefreshData();
                    break;
                case LOADDATA:
                    startLoadData();
                    break;
                case STOPREFRESH:
                    if (null != listDataHeadView) listDataHeadView.stopRefresh();
                    break;
                case STOPLOAD:
                    if (null != listDataFootView) listDataFootView.stopLoad();
                    break;
                case READDATAEND:
                    String back = (String) msg.obj;
                    readFileDataEnd(back.substring(0, back.indexOf("@")),
                            back.substring(back.indexOf("@") + 1));
                    break;
            }
        }
    };

    //存数据
    protected void saveUserApiData(final Context context,
                                   final String application,
                                   final String data) {
        if (StringUtils.isStringToNUll(data)) {
            return;
        }
        LogUtil.showLog("saveUserApiData", "saveType:" + saveType);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpCacheUtil.getHttpCacheUtil().saveCacheData(context,
                        getUserInfo().getString("auid") + application + saveType
                        , data);
            }
        }).start();
    }

    //读取数据
    protected void readUserApiData(final Context context, final String application) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = HttpCacheUtil.getHttpCacheUtil().readCacheData(context,
                        getUserInfo().getString("auid") + application + saveType);
                Message message = new Message();
                message.what = READDATAEND;
                message.obj = application + saveType + "@" + data;
                baseHandler.sendMessage(message);
            }
        }).start();
    }

    //读取结束,更新数据
    public void readFileDataEnd(String application, String resultData) {
        isReadFinish = true;
        LogUtil.showLog("readFileDataEnd", "application:" + application);
        LogUtil.showLog("readFileDataEnd", "读取数据 : " + resultData);
    }


    protected View watermarkView;//水印view
    private List<String> labels = new ArrayList<>();//水印文字

    //制定的view添加水印
    public void addWaterMark(int idRes) {
        String name = getUserInfo().getString("realName");
        if (!StringUtils.isStringToNUll(name)) {
            if (null == watermarkView) {
                watermarkView = rootView.findViewById(idRes);
                //添加背景图
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    watermarkView.setBackgroundColor(0);
                    labels.add(name);//可以显示多行文字
                    watermarkView.setBackground(new WaterMarkBg(getActivity(), labels, -45, 12));
                }
            }
            watermarkView.setVisibility(View.VISIBLE);
        }
    }


    //初始化权限管理工具
    public void initPermissionUtils(int requestCode) {
        if (null == permissionUtils) {
            permissionUtils = new PermissionUtils();
        }
        permissionUtils.setRequestCode(requestCode);
        permissionUtils.setAfterRequestPermissions(afterRequestPermission);
    }

    //权限回调监听事件
    public PermissionUtils.AfterRequestPermissions afterRequestPermission
            = new PermissionUtils.AfterRequestPermissions() {
        @Override
        public void OpenNeedPermission(int openRequestCode) {
            afterPermissionOpen(openRequestCode);

        }

        @Override
        public void CloseNeedPermission(int openRequestCode) {
            afterPermissionClose(openRequestCode);
        }
    };

    //在权限打开之后后续操作
    public void afterPermissionOpen(int openRequestCode) {

    }

    //未授权操作
    public void afterPermissionClose(int openRequestCode) {

    }

    //权限处理
    public void onPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (null != permissionUtils) {
            permissionUtils.afterRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
        }
    }


    //dialog监听,传递参数，那个dialog需要
    public InfoDialogListener backInfoDialogListener(final int listenerFrom) {
        InfoDialogListener infoDialogListener = new InfoDialogListener() {
            @Override
            public void onLeftButtonClicked() {
                onLeftClicked(listenerFrom);
            }

            @Override
            public void onRightButtonClicked() {
                onRightClicked(listenerFrom);
            }

            @Override
            public void onDialogItemClicked(int otherClickedFlag) {
                onOtherClicked(otherClickedFlag, listenerFrom);
            }
        };
        return infoDialogListener;
    }


    public void onLeftClicked(int listenerFrom) {

    }


    public void onRightClicked(int listenerFrom) {

    }


    public void onOtherClicked(int otherClickedFlag, int listenerFrom) {

    }


    /***
     *
     * @说明：拍照返回处理，
     * bitmap：拍取照片转为bitmap对象 ，便于后续上传操作转化数据；
     * requestCode:调用相机，传入的参数
     * picUrl:图片路径
     */
    public void showPicFromCamera(int requestCode, Bitmap bitmap, String picUrl) {

    }

    //能否返回
    public boolean canGoBack(){
        return false ;
    }

    //执行返回
    public void goBack(){

    }

    public void initWindowsSize(){
        widthPixels = PhoneinfoUtils.getWindowsWidth(getActivity());
        heightPixels = PhoneinfoUtils.getWindowsHight(getActivity());
        density = PhoneinfoUtils.getWindowDensity(getActivity());
    }

    public void setViewOnClickAndFocus(View viewOnClickAndFocus){
        if(null != viewOnClickAndFocus){
            viewOnClickAndFocus.setOnClickListener(this);
            viewOnClickAndFocus.setOnFocusChangeListener(this);
            viewOnClickAndFocus.setFocusable(true);
        }
    }

    //返回所有接口请求url的对象
    public AllRequestApplication backAllRequest(){
        if(null == allRequestApplication){
            allRequestApplication = AllRequestApplication.getInstance();
        }
        return allRequestApplication ;
    }

    @Override
    public void onDestroy() {
        baseHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}


