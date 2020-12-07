package com.app.customviewlibrary.jsbridge;


public interface WebViewJavascriptBridge {
	public void send(String data);
	public void send(String data, CallBackFunction responseCallback);
}