package com.app.customviewlibrary.jsbridge;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class BridgeWebChromeClient extends WebChromeClient {

    ValueCallback<Uri> mUploadMessage ;
    ValueCallback<Uri[]> mUploadCallbackAboveL ;
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        openChooseFile(null,mUploadCallbackAboveL,mUploadMessage);
    }

    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        openChooseFile(null,mUploadCallbackAboveL,mUploadMessage);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        openChooseFile(null,mUploadCallbackAboveL,mUploadMessage);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        mUploadCallbackAboveL = filePathCallback;
        openChooseFile(webView,mUploadCallbackAboveL,mUploadMessage);
        return true;
    }

    //图片选择方法，集中处理
    public void openChooseFile( WebView webView , ValueCallback<Uri[]> mUploadCallbackAboveL, ValueCallback<Uri> mUploadMessage){

    }
}
