package com.app.customviewlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.toolboxlibrary.ImageViewUtils;
import com.app.toolboxlibrary.StringUtils;
import com.gobestsoft.applibrary.R;

/**
 * 复合组件
 */
public class HorizontalActionLayout extends RelativeLayout {
    private Context mContext;
    private ImageView action_left_img;//左边的图片
    private TextView function_layout_left_tv;//左边的文字
    private ImageView action_right_img;//右边的箭头
    private TextView function_layout_right_tv;//右边的文字
    private TextView cut_line_view;//分割线（父类长度）
    private CircleImageView function_action_right_iv ;//圆形图片
    private TextView function_layout_cut_line_alignleft_view;//分割线（和左边图片右对齐长度）

    private String left_action_name = "";
    private String right_action_name = "";
    private int left_action_color = 0xff333333;
    private int right_action_color = 0xff3c3c3c;
    private float left_action_size = 14;
    private float right_action_size = 14;

    private boolean isShowAllLine = true;
    private boolean is_show_left_image = true;
    private boolean is_show_right_image = true;

    private int left_image = 0xffffffff;
    private int right_image = 0xffffffff;
    private int line_color = 0xfff2f2f2;

    public HorizontalActionLayout(Context context) {
        this(context, null);
    }

    public HorizontalActionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalActionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(attrs, defStyleAttr);
    }

    @SuppressLint("ResourceAsColor")
    private void initView(AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.horizontal_function_layout, this);
        action_left_img =  findViewById(R.id.function_layout_left_iv);
        function_layout_left_tv =  findViewById(R.id.function_layout_name_tv);
        action_right_img =  findViewById(R.id.function_layout_right_iv);
        function_layout_right_tv =  findViewById(R.id.function_layout_right_tv);
        cut_line_view =  findViewById(R.id.function_layout_cut_line_view);
        function_layout_cut_line_alignleft_view =  findViewById(R.id.function_layout_cut_line_alignleft_view);
        function_action_right_iv = findViewById(R.id.function_action_right_iv) ;
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalActionLayout,
                defStyleAttr, 0);
        try {
            left_action_name = a.getString(R.styleable.HorizontalActionLayout_left_action_name);
            right_action_name = a.getString(R.styleable.HorizontalActionLayout_right_action_name);
            left_action_color = a.getColor(R.styleable.HorizontalActionLayout_left_action_color
                    ,left_action_color);

            right_action_color = a.getColor(R.styleable.HorizontalActionLayout_right_action_color
                    , right_action_color);
            left_action_size = a.getDimension(R.styleable.HorizontalActionLayout_left_action_size, 14);
            right_action_size = a.getDimension(R.styleable.HorizontalActionLayout_right_action_size, 14);

            left_image = a.getResourceId(R.styleable.HorizontalActionLayout_left_image, left_image);
            right_image = a.getResourceId(R.styleable.HorizontalActionLayout_right_image, right_image);
            is_show_left_image = a.getBoolean(R.styleable.HorizontalActionLayout_is_show_left_image, true);
            is_show_right_image = a.getBoolean(R.styleable.HorizontalActionLayout_is_show_right_image, true);

            line_color = a.getColor(R.styleable.HorizontalActionLayout_line_color
                    , line_color);
            isShowAllLine = a.getBoolean(R.styleable.HorizontalActionLayout_isShowAllLine, true);
        } finally {
            a.recycle();
        }
        if(is_show_left_image){
            action_left_img.setImageResource(left_image);
        }else{
            action_left_img.setVisibility(GONE);
        }
        if(is_show_right_image){
            action_right_img.setImageResource(right_image);
        }else{
            action_right_img.setVisibility(GONE);
        }

        function_layout_left_tv.setText(left_action_name);
        function_layout_left_tv.setTextColor(left_action_color);
        function_layout_left_tv.setTextSize(left_action_size);
        function_layout_right_tv.setText(right_action_name);
        function_layout_right_tv.setTextColor(right_action_color);
        function_layout_right_tv.setTextSize(right_action_size);

        cut_line_view.setVisibility(isShowAllLine ? VISIBLE : GONE);
        function_layout_cut_line_alignleft_view.setVisibility(!isShowAllLine ? VISIBLE : GONE);
        cut_line_view.setBackgroundColor(line_color);
        function_layout_cut_line_alignleft_view.setBackgroundColor(line_color);
    }


    //右边imageview
    public void showRihgtImg(String imgUrl , int errorImg){
        function_layout_right_tv.setVisibility(GONE);
        function_action_right_iv.setVisibility(VISIBLE);
        if(!StringUtils.isStringToNUll(imgUrl)){
            ImageViewUtils.getInstance().showUrlAsBitmap(mContext,imgUrl,function_action_right_iv,errorImg);
        }else{
            function_action_right_iv.setImageResource(errorImg);
        }
    }

    //右边提示文字
    public void showRightHintData(String data){
        function_action_right_iv.setVisibility(GONE);
        function_layout_right_tv.setVisibility(VISIBLE);
        function_layout_right_tv.setText(data);
    }
    //左边提示文字
    public void showLeftHintData(String data){
        function_layout_left_tv.setVisibility(VISIBLE);
        function_layout_left_tv.setText(data);
    }

    //左边imageView
    public void showLeftImg(String imgUrl , int errorImg){
        action_left_img.setVisibility(VISIBLE);
        if(!StringUtils.isStringToNUll(imgUrl)){
            ImageViewUtils.getInstance().showUrlAsBitmap(mContext,""+imgUrl, action_left_img,errorImg);
        }else{
            action_left_img.setImageResource(errorImg);
        }
    }

    //隐藏或显示右边箭头
    public void setShowRightImage(boolean isShow){
        action_right_img.setVisibility(isShow?VISIBLE:GONE);
    }
}
