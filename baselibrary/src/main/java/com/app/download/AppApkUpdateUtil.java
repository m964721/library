package com.app.download;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.app.dialoglibrary.dialog.FileDownloadDialog;
import com.app.dialoglibrary.dialog.HintDialog;
import com.app.toolboxlibrary.InfoDialogListener;
import com.app.toolboxlibrary.LogUtil;
import com.app.xxbusiness.base.XxBusiness;

import java.io.File;

public class AppApkUpdateUtil {
    private HintDialog hintDialog;//升级提示窗
    private HintDialog settingDialog;//设置提示窗
    private FileDownloadDialog fileDownloadDialog;//进度条弹窗
    private AppFileDownload appFileDownload;//文件下载
    private OnDownResult onDownResult;//是否更新的回调
    Context CTX;
    String apkFileName;//file名字
    String apkFilePath="";
    String apkUrl;//apk下载路径
    boolean must; //是否强制更新
    String updateContent; //更新提示语
    String leftHint; //左边提示文字
    String rightHint; //右边提示文字
    int requestCodeInstall;//安装应用未知来源

    private AppApkUpdateUtil(Context context, Builder builder) {
        this.CTX = context;
        this.apkFileName = builder.apkFilePath;
        this.onDownResult = builder.onDownResult;
        this.must = builder.must;
        this.updateContent = builder.updateContent;
        this.leftHint = builder.leftHint;
        this.rightHint = builder.rightHint;
        this.requestCodeInstall = builder.requestCode;
        this.apkUrl = builder.apkUrl;
    }

    /**
     * build模式
     */
    public static class Builder {

        String apkFilePath = "";
        String apkUrl = "";
        OnDownResult onDownResult;
        boolean must = true;
        String updateContent = "版本更新";
        String leftHint = "取消";
        String rightHint = "确定";
        int requestCode = 0x0010;

        public Builder setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder setApkFilePath(String apkFilePath) {
            this.apkFilePath = apkFilePath;
            return this;
        }

        public Builder setOnDownResult(OnDownResult onDownResult) {
            this.onDownResult = onDownResult;
            return this;
        }

        public Builder setMust(boolean must) {
            this.must = must;
            return this;
        }

        public Builder setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
            return this;
        }

        public Builder setLeftHint(String leftHint) {
            this.leftHint = leftHint;
            return this;
        }

        public Builder setRightHint(String rightHint) {
            this.rightHint = rightHint;
            return this;
        }

        public AppApkUpdateUtil build(Context context) {
            return new AppApkUpdateUtil(context, this);
        }
    }

    /**
     *
     */
    public void onDestroy(){
        hintDialog = null ;
        settingDialog = null ;
        fileDownloadDialog = null ;
        appFileDownload = null ;
        CTX = null ;
    }

    //外部onActivityResult调用
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeInstall) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!isHasInstallPermissionWithO(CTX)) {
                    //没有打开允许开关
                    showGoToSetting();
                } else {
                    autoInstall(CTX, apkFilePath);
                }
            }
        }
    }

    /***
     * 下载更新提示框
     */
    public void showDownDialog() {
        if (null != hintDialog && hintDialog.isShowing()) {
            hintDialog.dismiss();
        } else {
            HintDialog.Builder builder = new HintDialog.Builder()
                    .setTitle("版本升级")
                    .setBody(updateContent)
                    .setLeftData(leftHint)
                    .setRightData(rightHint)
                    .setCancelable(!must)
                    .setOnClickDialog(new InfoDialogListener() {
                        @Override
                        public void onLeftButtonClicked() {
                            onDownResult.OnDownResult(false);
                        }

                        @Override
                        public void onRightButtonClicked() {
                            toDownloadApk();
                        }

                        @Override
                        public void onDialogItemClicked(int otherClickedFlag) {

                        }
                    });
            hintDialog = builder.build(CTX);
            //点击返回按钮 退出下载
            hintDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    onDownResult.OnDownResult(false);
                    return false;
                }
            });
        }
        hintDialog.show();
    }

    /**
     * 下载apk
     */
    private void toDownloadApk() {
        if (null != apkUrl && apkUrl.length() > 0 && XxBusiness.checkNetStatus(CTX)) {
            if (!apkUrl.startsWith("http") && !apkUrl.startsWith("https")) {
                Toast.makeText(CTX, "下载apk地址为空，无效或者为空", Toast.LENGTH_SHORT).show();
                onDownResult.OnDownResult(false);
            } else {
                fileDownloadDialog = new FileDownloadDialog(CTX);
                if (null == appFileDownload) {
                    appFileDownload = new AppFileDownload(CTX);
                }
                appFileDownload.downloadFile(apkUrl, apkFileName);
                appFileDownload.setOnDownloadFinish(new AppFileDownload.OnDownloadFinish() {
                    @Override
                    public void onDownloadProgress(int progress) {
                        fileDownloadDialog.setTip(progress);
                        if (progress == 100) {
                            fileDownloadDialog.dismiss();
                        }
                    }

                    @Override
                    public void onDownloadFinish(String filePath) {
                        apkFilePath = filePath ;
                        LogUtil.showLog("AppFileDownload onDownloadFinish",filePath);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (!isHasInstallPermissionWithO(CTX)) {
                                Toast.makeText(CTX, "请打开安装应用未知来源选项,否则会影响您的正常使用!", Toast.LENGTH_SHORT).show();
                                startInstallPermissionSettingActivity(CTX, requestCodeInstall);
                            } else {
                                autoInstall(CTX, apkFilePath);
                            }
                        } else {
                            autoInstall(CTX, apkFilePath);
                        }
                    }

                    @Override
                    public void onDownloadError(Object o) {
                        fileDownloadDialog.dismiss();
                    }
                });

                fileDownloadDialog.show();
            }
        } else {
            Toast.makeText(CTX, "下载apk地址为空，无效或者为空", Toast.LENGTH_SHORT).show();
            onDownResult.OnDownResult(false);
        }
    }

    /**
     * 设置安装未知来源
     */
    private void showGoToSetting() {
        if (null != settingDialog && settingDialog.isShowing()) {
            settingDialog.dismiss();
        } else {
            HintDialog.Builder builder = new HintDialog.Builder()
                    .setTitle("提示")
                    .setBody("当前应用缺少必要权限。请点击-设置-安装未知来源应用权限界面,找到本App打开相应开关。")
                    .setLeftData("取消")
                    .setRightData("设置")
                    .setOnClickDialog(new InfoDialogListener() {
                        @Override
                        public void onLeftButtonClicked() {
                            onDownResult.OnInstallResult(false);
                        }

                        @Override
                        public void onRightButtonClicked() {
                            startAppSettings();
                        }

                        @Override
                        public void onDialogItemClicked(int otherClickedFlag) {

                        }
                    });
            settingDialog = builder.build(CTX);
        }
        settingDialog.show();
    }


    /**
     * 下载完成后安装apk
     *
     * @param context
     * @param apkFilepath apk路径
     */
    private void autoInstall(Context context, String apkFilepath) {
        if (null != apkFilepath) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File apkFile = new File(apkFilepath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(
                        context, context.getPackageName() + ".fileprovider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile),
                        "application/vnd.android.package-archive");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 如果为8.0以上系统，则判断是否有未知应用安装权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isHasInstallPermissionWithO(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    private void startInstallPermissionSettingActivity(Context context, int requestCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        //获取当前apk包URI，并设置到intent中（这一步设置，可让“未知应用权限设置界面”只显示当前应用的设置项）
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        intent.setData(packageURI);
        //设置不同版本跳转未知应用的动作
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        } else {
            intent.setAction(android.provider.Settings.ACTION_SECURITY_SETTINGS);
        }
        ((Activity) context).startActivityForResult(intent, requestCode);
    }


    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        try {
            Intent mIntent = new Intent();
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                mIntent.setData(Uri.fromParts("package", CTX.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                mIntent.setAction(Intent.ACTION_VIEW);
                mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                mIntent.putExtra("com.android.settings.ApplicationPkgName", CTX.getPackageName());
            }
            ((Activity) CTX).startActivityForResult(mIntent, requestCodeInstall);
        } catch (Exception e) {

        }
    }

    //回调接口
    public interface OnDownResult {
        void OnDownResult(boolean isDownLoad);//是否下载
        void OnInstallResult(boolean isInstall);//安装结果
    }
}
