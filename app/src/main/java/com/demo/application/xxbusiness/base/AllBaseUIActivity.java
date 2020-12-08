package com.demo.application.xxbusiness.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import com.app.customviewlibrary.WaterMarkBg;
import com.demo.application.networklibrary.utils.HttpCacheUtil;
import com.app.toolboxlibrary.DateUtil;
import com.app.toolboxlibrary.FileUtiles;
import com.app.dialoglibrary.InfoDialogListener;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.PermissionUtils;
import com.app.toolboxlibrary.PhoneinfoUtils;
import com.app.toolboxlibrary.StringUtils;
import com.gobestsoft.applibrary.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AllBaseUIActivity extends BaseActivity implements
        View.OnClickListener , View.OnFocusChangeListener {
    public static final int READ_DATA_END = 5;//读取缓存结束
    protected PermissionUtils permissionUtils;//权限管理
    protected InputMethodManager imm = null; // 隐藏键盘
    protected String imgTempName = "";//存放路径
    public static final int MIN_CLICK_DELAY_TIME = 300;//点击事件间隔
    private long lastClickTime = 0;
    public int widthPixels;
    public int heightPixels;
    public double density ;
    //配合刷新使用
    @SuppressLint("HandlerLeak")
    private Handler baseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case READ_DATA_END:
                    String back = (String) msg.obj;
                    readFileDataEnd(back.substring(0, back.indexOf("@")),
                            back.substring(back.indexOf("@") + 1));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparent();
        widthPixels = PhoneinfoUtils.getWindowsWidth(CTX);
        heightPixels = PhoneinfoUtils.getWindowsHight(CTX);
        density = PhoneinfoUtils.getWindowDensity(CTX);
        LogUtil.showLog("屏幕宽度:"+widthPixels+";屏幕高度:"+heightPixels+";屏幕密度:"+density);
        initStatusBar();
        setContentView(getContentViewId());
        initBundleData();
        init();
    }

    //设置statusBar,手机顶部导航栏
    public void initStatusBar() {

    }

    /**
     * 获取上一个界面传送过来的数据
     */
    protected abstract void initBundleData();

    /**
     * 获取显示view的xml文件ID
     */
    protected abstract int getContentViewId();

    /**
     * 初始化应用程序，设置一些初始化数据,获取数据等操作
     */
    protected abstract void init();


    public void back(View view) {
        finish();
    }

    public void setViewOnClickAndFocus(View viewOnClickAndFocus){
        if(null != viewOnClickAndFocus){
            viewOnClickAndFocus.setOnClickListener(this);
            viewOnClickAndFocus.setOnFocusChangeListener(this);
            viewOnClickAndFocus.setFocusable(true);
        }
    }

    /**
     * 设置标题
     *
     * @param activityTitleRes
     */
    protected void setTitleContent(int activityTitleRes) {
        TextView titleTv = findViewById(R.id.top_title_center_tv);
        titleTv.setText(getResStr(activityTitleRes));
    }

    protected void setTitleContent(String activityTitleRes) {
        TextView titleTv = findViewById(R.id.top_title_center_tv);
        titleTv.setText(activityTitleRes);
    }

    //设置右边显示的图片
    protected void setTitleRightImg(int res) {
        ImageView imageView = findViewById(R.id.top_title_right_iv);
        imageView.setImageResource(res);
        imageView.setOnClickListener(this);
    }

    //设置右边显示为功能文字
    protected void setRightActionTv(String activityTitle) {
        TextView titleTv = findViewById(R.id.top_title_right_text_tv);
        titleTv.setText(activityTitle);
        titleTv.setOnClickListener(this);
    }


    //设置输入框监听
    protected void setUseEditText(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AllBaseUIActivity.this.onFocusChange(editText, hasFocus);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                AllBaseUIActivity.this.beforeTextChanged(editText, s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AllBaseUIActivity.this.onTextChanged(editText, s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                AllBaseUIActivity.this.afterTextChanged(editText, s);
            }
        });
    }

    public void onEditTextTouch(EditText editText, MotionEvent event) {

    }

    public void beforeTextChanged(EditText editText, CharSequence s, int start, int count, int after) {

    }

    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {

    }

    public void afterTextChanged(EditText editText, Editable s) {

    }

    public void onFocusChange(View editText, boolean hasFocus) {

    }


    //初始化权限管理工具
    public void initPermissionUtils(int requestCode) {
        if (null == permissionUtils) {
            permissionUtils = new PermissionUtils();
        }
        permissionUtils.setRequestCode(requestCode);
        permissionUtils.setAfterRequestPermissions(new PermissionUtils.AfterRequestPermissions() {
            @Override
            public void OpenNeedPermission(int openRequestCode) {
                afterPermissionOpen(openRequestCode);
            }

            @Override
            public void CloseNeedPermission(int openRequestCode) {
                afterPermissionClose(openRequestCode);
            }
        });
    }

    //在权限打开之后后续操作
    public void afterPermissionOpen(int openRequestCode) {

    }

    //未授权操作
    public void afterPermissionClose(int openRequestCode) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (null != permissionUtils) {
            permissionUtils.afterRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
        }
        super.onRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
    }

    /**
     * @return 无
     * @throws
     * @说明：调用系统相机拍照
     * @Parameters 无
     */
    public void takeCardPic(int codeFlag) {
        try {
            imgTempName = FileUtiles.creatPicUrl(CTX,"pic");
            File file = new File(imgTempName);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri imageUri ;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                //通过FileProvider创建一个content类型的Uri
                imageUri = FileProvider.getUriForFile(CTX, CTX.getPackageName() + ".fileprovider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            } else {
                imageUri = Uri.fromFile(file) ;
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra("scale", true);
            startActivityForResult(intent, codeFlag);
        } catch (Exception e) {

        }
    }

    //相册获取图片
    public void toAlbumToGetPic(int codeFlag) {
        try {
            Intent getImage = new Intent(Intent.ACTION_PICK);
            getImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(getImage, codeFlag);
        } catch (Exception e) {

        }

    }

    /**
     * 使用回调获取返回数据数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //调用相机拍照返回
            if (XxBusinessConfig.TAKECARDPIC == requestCode) {
                String path = FileUtiles.compress(CTX,
                        imgTempName,
                        imgTempName,
                        4,"1");
                showPicFromCamera(requestCode, null, path);
            } else if (XxBusinessConfig.PICK_PHOTO == requestCode) {
                // 相册返回
                String img_path = "";
                if (data != null) {
                    Uri mImageCaptureUri = data.getData();
                    if (null != mImageCaptureUri &&
                            mImageCaptureUri.toString().startsWith("content")) {
                        String[] proj = {MediaStore.MediaColumns.DATA};
                        Cursor actualimagecursor = getContentResolver().query(
                                mImageCaptureUri, proj, null, null, null);
                        int actual_image_column_index = actualimagecursor
                                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                        actualimagecursor.moveToFirst();
                        img_path = actualimagecursor
                                .getString(actual_image_column_index);
                    } else {
                        img_path = mImageCaptureUri.toString().substring(7);
                    }
                    imgTempName = FileUtiles.compress(CTX,
                            FileUtiles.creatPicUrl(CTX,"Album"),
                            img_path,
                            1,"0");
                    showPicFromCamera(requestCode, null, imgTempName);
                }
            }
        }
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

    @Override
    public void onClick(View view) {
        long currentTime = DateUtil.getMillis(new Date());
        if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
            return;
        } else {
            lastClickTime = currentTime;
            doClick(view);
        }
    }

    //正常点击事件
    public void doClick(View view) {

    }

    //返回输入内容
    public String backInputData(EditText editText) {
        if (null != editText) {
            return editText.getText().toString().replace(" ", "");
        }
        return "";
    }

    //获取String资源文件
    public String getResStr(int strRes) {
        return getResources().getString(strRes);
    }

    //获取颜色资源
    public int getAppColor(int colorRes) {
        return getResources().getColor(colorRes);
    }

    //获取用户信息
    public String getSaveUserInfo(String key) {
        return getMyUserInfo().getString(key.replace(" ", ""));
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(EditText et) {
        if (null == imm) {
            this.imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        et.requestFocus();
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    //存数据
    protected void saveUserApiData(final String application, final String data) {
        if (StringUtils.isStringToNUll(data)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpCacheUtil.getHttpCacheUtil().saveCacheData(CTX,
                        getMyUserInfo().getString("id") + application
                        , "" + data);
            }
        }).start();
    }

    //读取数据
    protected void readUserApiData(final String application) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = HttpCacheUtil.getHttpCacheUtil().readCacheData(CTX,
                        getMyUserInfo().getString("auid") + application);
                Message message = new Message();
                message.what = READ_DATA_END;
                message.obj = application + "@" + data;
                baseHandler.sendMessage(message);
            }
        }).start();
    }

    //读取结束
    public void readFileDataEnd(String application, String resultData) {
        LogUtil.showLog("readFileDataEnd", "data:" + resultData);
    }

    //其他事件
    public void otherSendEnd(Object data) {

    }

    protected View watermarkView;//水印view
    private List<String> labels = new ArrayList<>();//水印文字
    //制定的view添加水印
    public void addWaterMark(int idRes) {
        String name = getMyUserInfo().getString("realName");
        if (!StringUtils.isStringToNUll(name)) {
            watermarkView = findViewById(idRes);
            //添加背景图
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                watermarkView.setBackgroundColor(0);
                labels.add(name);//可以显示多行文字
                watermarkView.setBackground(new WaterMarkBg(this, labels, -45, 12));
            }
        }
    }

    /**
     * 显示水印布局
     *
     * @param activity
     */
    public void showWatermarkView(final Activity activity) {
        String name = getMyUserInfo().getString("realName");
        if (!StringUtils.isStringToNUll(name)) {
            final ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            View watermarkView = LayoutInflater.from(activity).inflate(R.layout.all_activity_watermark_view, null);
            //添加背景图
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                watermarkView.setBackgroundColor(0);
                labels.add(name);//可以显示多行文字
                watermarkView.setBackground(new WaterMarkBg(this, labels, -45, 12));
            }
            //可对水印布局进行初始化操作
            rootView.addView(watermarkView);
        }
    }

    //那些不需要水印的
    private String[] activityNames = {
            getAllUiClassNameConfig().WelcomeActivity,
            getAllUiClassNameConfig().LoginActivity,
            getAllUiClassNameConfig().MainActivity

    };

    //过滤那些需要显示水印的Activity
    public void filterActivityToAdd() {
        String className = this.getClass().getName();
        boolean isNeedMark = true;
        for (String name : activityNames) {
            if (-1 != className.indexOf(name)) {
                isNeedMark = false;
            }
        }
        if (isNeedMark) {
            showWatermarkView(this);
        }
    }

    /**
     * 设置透明状态栏
     *
     */
    public void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //dialog监听,传递参数，那个dialog需要
    private InfoDialogListener backInfoDialogListener(final int listenerFrom) {
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

    @Override
    protected void onDestroy() {
        baseHandler.removeCallbacks(null);
        super.onDestroy();
    }
}
