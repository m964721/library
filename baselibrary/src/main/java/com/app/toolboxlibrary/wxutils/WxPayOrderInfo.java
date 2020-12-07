package com.app.toolboxlibrary.wxutils;

import java.io.Serializable;

public class WxPayOrderInfo implements Serializable {


    /**
     * package : Sign=WXPay
     * out_trade_no : wxpay1554110742896
     * appid : wx4cc7b65fb6fc077b
     * extdata : {"user_id":"8"}
     * sign : EDE27E4454C4725E6D57039B28E7E428
     * partnerid : 1529362781
     * prepayid : wx0117255069100045332f800d0636451496
     * noncestr : bu7zzR2wiBP9rDU7
     * timestamp : 1554110743
     */

    private String packageX;
    private String out_trade_no;
    private String appid;
    private String extdata;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getExtdata() {
        return extdata;
    }

    public void setExtdata(String extdata) {
        this.extdata = extdata;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
