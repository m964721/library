package com.app.toolboxlibrary;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.Nullable;

public class CutDownTimeUtil {

    private CutDownListener cutDownListener;
    private final int cutDowningWhat = 0x01;//计时中发送标志
    private final int cutDownFinishWhat = 0x02;//计时结束标志
    private int time = 0;//计时器计时
    private int finalTime = 100;//总时间
    private long delayMillis = 1000 ;//休息时间
    public static String typeLimited = "0" ;//正常的倒计时
    public static String typeLoop = "1" ;//死循环
    private String type = typeLimited ;//倒计时标识

    //配合线程使用
    @SuppressLint("HandlerLeak")
    private Handler cutDownHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case cutDowningWhat:
                    if(type.equals(typeLoop)){
                        time++ ;
                        getCutDownListener().onCutDowning(time);
                        Message message = this.obtainMessage(cutDowningWhat);
                        this.sendMessageDelayed(message, delayMillis);
                    }else if(type.equals(typeLimited) ){
                        if( time < finalTime){
                            time++;
                            getCutDownListener().onCutDowning(time);
                            Message message = this.obtainMessage(cutDowningWhat);
                            this.sendMessageDelayed(message, delayMillis);
                        }else {
                            cutDownHandler.sendEmptyMessage(cutDownFinishWhat);
                        }
                    }
                    break;
                case cutDownFinishWhat:
                    getCutDownListener().onFinish();
                    this.removeMessages(cutDowningWhat);
                    this.removeMessages(cutDownFinishWhat);
                    break;
            }
        }
    };

    //初始化
    public CutDownTimeUtil(int allTime, int delayMillis, @Nullable String type) {
        this.finalTime = allTime;
        this.delayMillis = delayMillis ;
        this.type = type ;
    }

    //启动
    public void startCutDown() {
        cutDownHandler.sendEmptyMessageDelayed(cutDowningWhat,delayMillis);
    }

    //停止
    public void stopCutDown() {
        time = 0 ;
        cutDownHandler.removeMessages(cutDowningWhat);
        cutDownHandler.removeMessages(cutDownFinishWhat);
        cutDownHandler.removeCallbacks(null);
        cutDownHandler.removeCallbacksAndMessages(null);
    }

    //获取监听事件
    public CutDownListener getCutDownListener() {
        if (null == cutDownListener) {
            cutDownListener = new CutDownListener() {
                @Override
                public void onCutDowning(int executeTime) {

                }

                @Override
                public void onFinish() {

                }
            };
        }
        return cutDownListener;
    }

    //设置回调监听
    public void setCutDownListener(CutDownListener cutDownListener) {
        this.cutDownListener = cutDownListener;
    }

    //回调处理
    public interface CutDownListener {
        /**
         * 已执行的时间
         *
         * @param executeTime 执行时间
         */
        void onCutDowning(int executeTime);

        void onFinish();
    }
}
