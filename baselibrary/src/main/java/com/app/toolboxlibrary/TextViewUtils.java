package com.app.toolboxlibrary;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class TextViewUtils {

    /**
     * textview首行缩进
     *
     * @param textView
     * @param data
     */
    public static void indentTextView(TextView textView, String data) {
        if (null != textView) {
            SpannableStringBuilder span = new SpannableStringBuilder("缩进" + data);
            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(span);
        }
    }

    /**
     * 显示不同颜色
     */
    public static void showMoreColor(TextView textView,
                                     String data[] ,
                                     String color[]
    ) {
        if (null == textView) {
            return;
        }
        int min = 0;
        String showData = "";
        int dataLength = 0;
        if (data.length > color.length) {
            min = color.length;
        } else {
            min = data.length;
        }
        for (int i = 0; i < min; i++) {
            showData = showData + data[i];
        }
        LogUtil.showLog("showMoreColor", "showData:" + showData);
        //创建一个 SpannableString对象
        SpannableString spannableString = new SpannableString(showData);
        for (int i = 0; i < min; i++) {
            if (data[i].length() != 0) {
                spannableString.setSpan(
                        new ForegroundColorSpan(Color.parseColor(color[i])),
                        dataLength,
                        data[i].length() + dataLength,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                LogUtil.showLog("showMoreColor", "start:" + dataLength + "  end:" + (data[i].length() + dataLength));
                dataLength = dataLength + data[i].length();
            }
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
