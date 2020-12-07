package com.app.customviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.toolboxlibrary.ImageViewUtils;
import com.gobestsoft.applibrary.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PPH on 18/3/6.
 * 底部条控件
 */

public class BottomBarLayout extends LinearLayout {
    Context mcontext ;
    TextView textView;
    android.widget.ImageView imageView;
    TextView line_tv ;
    List<Integer> colorsList = new ArrayList<>() ;
    List<Integer> imgResList = new ArrayList<>() ;

    public BottonItemClick getBottonItemClick() {
        return bottonItemClick;
    }

    public void setBottonItemClick(BottonItemClick bottonItemClick) {
        this.bottonItemClick = bottonItemClick;
    }

    BottonItemClick bottonItemClick;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    int index;

    public BottomBarLayout(Context context) {
        super(context);
        initLayout(context);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        mcontext = context ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.baritem_layout, this);
        imageView =  findViewById(R.id.show_img);
        textView =   findViewById(R.id.name_text);
        line_tv = findViewById(R.id.bar_line_tv);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getBottonItemClick().BottomItemClicked(getIndex());
            }
        });
    }

    //设置文字，颜色，以及显示的图片
    public void setShowDataText(String text , List<Integer> colorsList , List<Integer> imgResList ){
        textView.setText(text);
        this.colorsList = colorsList ;
        this.imgResList = imgResList ;
    }

    //设置点击状态
    public void setItemClickable(boolean isclick ){
        this.setClickable( !isclick );
        if(isclick){
            textView.setTextColor(colorsList.get(0));
            imageView.setImageResource(imgResList.get(0));
        }else{
            imageView.setImageResource(imgResList.get(1));
            textView.setTextColor(colorsList.get(1));
        }
    }

    //这是单个显示的
    public void setShowData( int text , String url,int textColor, boolean isShowLine) {
        textView.setTextColor(textColor);
        textView.setText(text);
        ImageViewUtils.getInstance().showUrlImg(mcontext,""+url,imageView);
        if(isShowLine){
            line_tv.setVisibility(VISIBLE);
        }else{
            line_tv.setVisibility(GONE);
        }
    }

    public void setShowData( int text , int res , int textColor , boolean isShowLine) {
        textView.setText(text);
        textView.setTextColor(textColor);
        imageView.setImageResource(res);
        if(isShowLine){
            line_tv.setVisibility(VISIBLE);
        }else{
            line_tv.setVisibility(GONE);
        }
    }

    public void setImgSize( int size ){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.width = size ;
        layoutParams.height = size ;
        imageView.setLayoutParams(layoutParams);
    }

    public interface BottonItemClick {
        void BottomItemClicked(int index);
    }
}
