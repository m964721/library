package com.app.customviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

/**
 * 显示密码等级的view
 */

public class StrengthGradeView extends LinearLayout {

    private Context mContext  ;
    private int defaultColor = 0xffe8e8ee;//默认背景色
    private float defaultWidth = 1000;//默认宽度
    private int defaultSize = 3;//默认显示等级个数
    private int strengthSize = 1 ;
    private int strengthColor = 0xfff75455  ;
    private TextView backTv ;//显示不同等级的view
    LayoutParams linearParams ;
    public StrengthGradeView(Context context) {
        super(context);
    }

    public StrengthGradeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StrengthGradeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initLayout(Context context) {
        linearParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
        );
        mContext = context ;
        if( null == backTv ){
            backTv = new TextView(context) ;
        }
        refreshStrengthColor(strengthSize,strengthColor);
        this.addView(backTv);
        this.setBackgroundColor(defaultColor);
    }
    //设置默认的属性
    public void setDefaultData(int defaultColor,float defaultWidth,int defaultSize){
        this.defaultColor = defaultColor ;
        this.defaultSize = defaultSize ;
        this.defaultWidth = defaultWidth ;
    }

    //设置等级，以及显示的颜色
    public void refreshStrengthColor( int strengthSize , int strengthColor){
        linearParams.width = (int) (defaultWidth*strengthSize/defaultSize+0.5);
        backTv.setLayoutParams(linearParams);
        backTv.setBackgroundColor(strengthColor);
    }
}
