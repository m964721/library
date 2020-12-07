package com.app.customviewlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.toolboxlibrary.ListUtils;
import com.app.toolboxlibrary.TransformUtil;

import java.util.List;

/**
 * 绘制蒙层的Drawable
 */
public class WaterMarkBg extends Drawable {

    private Paint paint = new Paint();
    private List<String> labels;
    private Context context;
    private int degress;//角度
    private Path path = new Path();//路径
    private double sin = 0;//计算三角函数值
    private int textSize = 0;
    private int startY = 0 ;

    /**
     * 初始化构造
     *
     * @param context  上下文
     * @param labels   水印文字列表 多行显示支持
     * @param degress  水印角度
     * @param fontSize 水印文字大小
     */
    public WaterMarkBg(Context context, List<String> labels, int degress, int fontSize) {
        this.context = context ;
        this.labels = labels;
        this.context = context;
        this.degress = Math.abs(degress);
        sin = Math.sin(this.degress * Math.PI / 180);//算sin正弦函数值
        textSize = TransformUtil.sp2px(context, fontSize);
        startY = TransformUtil.sp2px(context,60);

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (ListUtils.backArrayListSize(labels) > 0) {
            int width = getBounds().right;
            int height = getBounds().bottom;
            canvas.drawColor(Color.parseColor("#00ffffff"));
            paint.setColor(Color.parseColor("#1f000000"));
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            float textWidth = paint.measureText(labels.get(0));
            //canvas.rotate( degress );//设置旋转角度
            canvas.save();
            int index = 0;
            for (int positionY = startY ; positionY <= height ; positionY += textWidth*3) {
                float fromX = -width + (index++ % 20) * textWidth;
                for (float positionX = fromX; positionX < width; positionX += 300) {
                    int spacing = 0;//间距
                    for (String label : labels) {
                        path.moveTo(positionX, positionY);
                        path.lineTo(positionX + textWidth , positionY + (float)(textWidth*sin+0.5) );
                        canvas.drawTextOnPath(label, path, 0, 0, paint);
//                    canvas.drawText(label, positionX , positionY+spacing , paint);
                        spacing = spacing + 50;
                        path.reset();
                    }

                }
            }
            canvas.restore();
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}

