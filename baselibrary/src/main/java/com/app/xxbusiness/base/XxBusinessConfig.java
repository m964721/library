package com.app.xxbusiness.base;


import com.app.networklibrary.config.NetworkConfig;

/**
 * Created by jun on 17/2/22.
 */

public class XxBusinessConfig {


    public static final int START_SETTING = 0x995;//跳转系统设置
    public static final int SHARE_DIALOG_FLAG = 0x996;//分享弹窗
    public static final int INPUT_DIALOG = 0x997;//多个item的弹窗
    public static final int HAVE_LIST_DIALOG = 0x998;//多个item的弹窗
    public static final int APP_UPDATE = 0x999;//APP更新


    public static final int BackToRefreFragment = 0x999;//返回刷新fragmnt
    public static final int WILL_BE_CLOSED = 0x1000;// activity关闭控制
    public static final int ABNORMAL_TO_LOGIN = 0x1001;//异常跳转至登录
    public static final int ABNORMAL_AFTER_LOGIN = 0x1002;//登陆之后返回界面，需要刷新接口

    public static final int PICK_PHOTO = 0x1011;//相册拿到照片
    public static final int TAKECARDPIC = 0x1012;//拍照的标志
    public static final int APPNEEDPERMISSION = 0x1013;//需要部分权限

    public static final String AllStringJumpKey = "jumpType";//bundle参数String类型Key
    public static final String AllStringJumpVaule = "AllStringJumpVaule";//bundle参数String vaule
    public static final String AllIntJumpKey = "jumpIntType";//bundle参数int类型Key
    public static final String AllSerializableJumpKey = "Serializable";//bundle参数String类型Key

}
