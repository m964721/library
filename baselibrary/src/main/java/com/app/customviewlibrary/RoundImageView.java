package com.app.customviewlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.app.toolboxlibrary.TransformUtil;
import com.gobestsoft.applibrary.R;

/**
 * 圆角的ImageView
 *
 */
@SuppressLint("AppCompatCustomView")
public class RoundImageView  extends ImageView {

    float width, height;
    float roundSize = 5 ;//默认圆角

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImageView,
                defStyleAttr, 0);
        try{
            roundSize = a.getFloat(R.styleable.RoundImageView_need_radius,roundSize);
        }catch (Exception e){
            
        }
        roundSize = TransformUtil.dip2px(context,roundSize);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        if (width > roundSize && height > roundSize) {
            Path path = new Path();
            path.moveTo(roundSize, 0);
            path.lineTo(width - roundSize, 0);
            path.quadTo(width, 0, width, roundSize);
            path.lineTo(width, height - roundSize);
            path.quadTo(width, height, width - roundSize, height);
            path.lineTo(roundSize, height);
            path.quadTo(0, height, 0, height - roundSize);
            path.lineTo(0, roundSize);
            path.quadTo(0, 0, roundSize, 0);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}