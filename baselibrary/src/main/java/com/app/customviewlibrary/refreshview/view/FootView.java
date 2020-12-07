package com.app.customviewlibrary.refreshview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.customviewlibrary.refreshview.layout.BaseFooterView;
import com.app.customviewlibrary.refreshview.support.type.LayoutType;
import com.app.customviewlibrary.refreshview.support.utils.AnimUtil;
import com.gobestsoft.applibrary.R;

/**
 * Created by PPH on 18/1/23.
 */

public class FootView extends BaseFooterView {
    private TextView footHintTv;
    private TextView textView;
    private View tagImg;
    private View progress;
    private Context context ;

    public FootView(Context context) {
        this(context, null);
    }

    public FootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.foot_view_layout, this, true);
        textView = findViewById(R.id.foot_text);
        tagImg = findViewById(R.id.foot_tag);
        progress = findViewById(R.id.foot_progress);
        footHintTv = findViewById(R.id.foot_hint_tv);

        showRefreshView(true);
    }


    @Override
    protected void onStateChange(int state) {

        switch (state) {
            case NONE:
                break;
            case PULLING:
                showRefreshView(true);
                textView.setText("上拉加载更多");
                AnimUtil.startRotation(tagImg, 0);
                break;
            case LOOSENT_O_LOAD:
                textView.setText("松开加载");
                AnimUtil.startRotation(tagImg, 180);
                break;
            case LOADING:
                showRefreshView(false);
                footHintTv.setText("正在加载");
                AnimUtil.startShow(progress, 0.1f, 400, 200);
                break;
            case LOAD_CLONE:
                progress.setVisibility(GONE);
                footHintTv.setText("加载完成");
                break;

        }

    }

    //控制刷新数据
    private void showRefreshView(boolean isFirst) {
        if (!isFirst) {
            textView.setVisibility(INVISIBLE);
            tagImg.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
            footHintTv.setVisibility(VISIBLE);
        } else {
            footHintTv.setVisibility(INVISIBLE);
            progress.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            tagImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public float getSpanHeight() {
        return getHeight();
    }

    @Override
    public int getLayoutType() {
        return LayoutType.LAYOUT_NORMAL;
    }
}
