package com.demo.application.utils.wxutils;

import android.content.Context;

import com.app.toolboxlibrary.LogUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxPayAndShare {

    //申请的微信Appid wx4cc7b65fb6fc077b wxd111d4d7f967727d
    private static String WECHAT_PAY_APPID = "wx1f6fab66fc8b52cf";
    private Context context;
    private static  String WECHAT_SECRET = "1b182b5f89310bcfd3d77b4183af5d70";
    public static int WXSceneSession = SendMessageToWX.Req.WXSceneSession;//分享好友
    public static int WXSceneTimeline = SendMessageToWX.Req.WXSceneTimeline;//分享朋友圈
    public static WxPayAndShare wxPay;
    private IWXAPI iwxapi;//微信实例

    private WxPayAndShare(Context context) {
        this.context = context;
    }

    //获取单例模式
    public static WxPayAndShare getInstance(Context context) {
        if (null == wxPay) {
            synchronized (WxPayAndShare.class) {
                if (null == wxPay) {
                    wxPay = new WxPayAndShare(context);
                }
            }
        }
        return wxPay;
    }

    //获取单例模式
    public static WxPayAndShare getInstance(
            Context context ,
            String wechatAppid ,
            String wechatSecret) {
        if (null == wxPay) {
            synchronized (WxPayAndShare.class) {
                if (null == wxPay) {
                    wxPay = new WxPayAndShare(context);
                }
            }
        }
        WECHAT_PAY_APPID = wechatAppid ;
        WECHAT_SECRET = wechatSecret ;
        return wxPay;
    }

    public IWXAPI getIwxapi() {
        initWxApi();
        return iwxapi;
    }

    //初始化微信Api对象
    public void initWxApi() {
        if (null == iwxapi) {
            iwxapi = WXAPIFactory.createWXAPI(context, WECHAT_PAY_APPID, true);
            iwxapi.registerApp(WECHAT_PAY_APPID);
        }
    }

    //微信支付
    public void doWxPay(WxPayOrderInfo wxPayOrderInfo) {
        if (isWXAppInstalled() && null != wxPayOrderInfo) {
            PayReq request = new PayReq();
            request.appId = wxPayOrderInfo.getAppid();
            request.partnerId = wxPayOrderInfo.getPartnerid();
            request.prepayId = wxPayOrderInfo.getPrepayid();
            request.packageValue = wxPayOrderInfo.getPackageX();
            request.nonceStr = wxPayOrderInfo.getNoncestr();
            request.timeStamp = wxPayOrderInfo.getTimestamp();
            request.sign = wxPayOrderInfo.getSign();
            getIwxapi().sendReq(request);
        }
    }

    /**
     * 分享文字
     *
     * @param shareContent 分享内容
     * @param type         分享类型，朋友圈、收藏、好友
     */
    public void shareText(String shareContent, int type) {
        if (isWXAppInstalled()) {
            WXTextObject textObj = new WXTextObject();
            textObj.text = shareContent;

            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = textObj;
            // 发送文本类型的消息时，title字段不起作用
            // msg.title = "Title";
            msg.description = shareContent;

            // 构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
            req.message = msg;
            req.scene = type;
            getIwxapi().sendReq(req);
        }
    }

    /**
     * 分享一个图片
     *
     * @param shareBitmap 要分享的图片
     * @param type        分享类型，朋友圈、收藏、好友
     */
    public void shareImage(byte[] thumbData , Object shareBitmap, int type) {
        if (isWXAppInstalled() && null != shareBitmap) {
            WXImageObject imgObj = new WXImageObject();
            imgObj.setImagePath((String)shareBitmap);

            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;

            if(null != thumbData){
                msg.thumbData = thumbData ;  // 设置缩略图
            }


            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            req.scene = type;
            getIwxapi().sendReq(req);
        }
    }


    /**
     * @param shareUrl    分享地址
     * @param shareIcon   分享图标
     * @param title       分享标题
     * @param description 分享描述
     * @param type        分享类型，朋友圈、收藏、好友
     */
    public void shareToWXSceneSession(String shareUrl,
                                      byte[] shareIcon,
                                      String title,
                                      String description,
                                      int type
    ) {
        if (isWXAppInstalled()) {
            //初始化一个WXWebpageObject，填写url
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = ""+shareUrl;
            //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
            WXMediaMessage msg = new WXMediaMessage(webpage);
            if(null != shareIcon){
                msg.thumbData = shareIcon;
            }
            msg.title = ""+title;
            msg.description = ""+description;

            //构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = type;

            //调用api接口，发送数据到微信
            getIwxapi().sendReq(req);
        }
    }


    /**
     * 构建一个唯一标志
     *
     * @param type 分享的类型分字符串
     * @return 返回唯一字符串
     */
    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) :
                type + System.currentTimeMillis();
    }

    public void wxAuthen(){
        if(isWXAppInstalled()){
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "xzkb_wx_login";
            getIwxapi().sendReq(req);
        }
    }

    //判断是否安装
    public boolean isWXAppInstalled() {
        boolean isInstalled = getIwxapi().isWXAppInstalled();
        if (!isInstalled) {
            LogUtil.showToast(context, "您手机尚未安装微信，请安装后再登录");
            return isInstalled ;
        }
        return isInstalled;
    }


}
