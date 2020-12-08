package com.app.toolboxlibrary;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PPH on 17/7/26.
 */

public class PermissionUtils {
    public final String ONLY_CARMER[] = {Manifest.permission.CAMERA};//只是用相机
    public final String CAMERA[] = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};//相机读写
    public final String ALBUM[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};//读写权限
    public final String[] RECORD_AUDIO = {  Manifest.permission.RECORD_AUDIO };//音频
    public final String[] CALL_PHONE = { Manifest.permission.CALL_PHONE };//打电话
    public final String[] READ_CONTACTS = { Manifest.permission.READ_CONTACTS };//读取通讯录
    public final String[] CONTACT_GROUP = { Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS };//读写通讯录
    public final String[] SEND_SMS = {Manifest.permission.SEND_SMS};//发短信
    public final String[] READ_SMS = {Manifest.permission.READ_SMS};//读取短信
    public final String[] READ_PHONE_STATE = { Manifest.permission.READ_PHONE_STATE };//读取手机信息
    public final String[] LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};//开启定位权限


    private int openRequestCode  = -1 ;//默认判断
    private static final int OPENONEPERMISSION = 1800 ;//执行获取权限的code
    private AfterRequestPermissions afterRequestPermissions ;//监听
    public static final int NotificationPermission = 100 ;

    /**
     * context 当前上下文环境
     * 勿扰权限跳转
     */
    public static boolean startToNotification(Activity context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            context.startActivityForResult( intent , NotificationPermission );
            return true ;
        }else{
            return false ;
        }
    }


    public PermissionUtils(){

    }

    public int getRequestCode() {
        return openRequestCode;
    }

    public void setRequestCode(int requestCode) {
        this.openRequestCode = requestCode;
    }

    public AfterRequestPermissions getAfterRequestPermissions() {
        if(  null == afterRequestPermissions){
            afterRequestPermissions = new AfterRequestPermissions() {
                @Override
                public void OpenNeedPermission(int requestCode) {

                }

                @Override
                public void CloseNeedPermission(int requestCode) {

                }
            };
        }
        return afterRequestPermissions;
    }

    public void setAfterRequestPermissions(AfterRequestPermissions afterRequestPermissions) {
        this.afterRequestPermissions = afterRequestPermissions;
    }
    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    public List<String> findDeniedPermissions(Activity activity , String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(activity,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    //检验权限
    public void checkUsePermission(Activity activity , String checkUsePermission[]){
        String needCheck[] ;
        List<String> findDeniedList = findDeniedPermissions(activity,checkUsePermission);
        //过滤需要的权限，若果有，执行打开权限的方法
        if( null != findDeniedList && findDeniedList.size()>0){
            needCheck = findDeniedList.toArray(
                    new String[findDeniedList.size()]);
            if(openRequestCode == -1){
                openRequestCode = OPENONEPERMISSION ;
            }
            ActivityCompat.requestPermissions(activity, needCheck, openRequestCode);
        }else{
            //没有需要开启的权限，执行正常操作
            getAfterRequestPermissions().OpenNeedPermission(openRequestCode);
        }
    }
    //承接开启权限的回调
    public void afterRequestPermissionsResult( int requestCode,
                                               String[] permissions,
                                               int[] paramArrayOfInt ){
        if( openRequestCode == requestCode  ){
            if (null != paramArrayOfInt && paramArrayOfInt.length > 0
                    && paramArrayOfInt[0] != PackageManager.PERMISSION_GRANTED) {
                getAfterRequestPermissions().CloseNeedPermission(requestCode);
            }else{
                getAfterRequestPermissions().OpenNeedPermission(requestCode);
            }
        }
    }

    //回调执行操作
    public interface AfterRequestPermissions{
        void OpenNeedPermission(int openRequestCode);
        void CloseNeedPermission(int openRequestCode);
    }


    /**
     * 动态获取某个权限是否开启
     *
     * @param perm
     * @return
     */
    public static boolean checkPermissionIsOpen( Activity activity , String perm) {
        boolean isCheck = false;
        if (ContextCompat.checkSelfPermission(activity,
                perm) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.shouldShowRequestPermissionRationale(
                activity, perm)) {
            isCheck = false;
        } else {
            isCheck = true;
        }
        return isCheck;
    }

}
