package com.app.customviewlibrary.refreshview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.customviewlibrary.refreshview.layout.BaseHeaderView;
import com.app.customviewlibrary.refreshview.support.type.LayoutType;
import com.app.customviewlibrary.refreshview.support.utils.AnimUtil;
import com.app.library.R;

/**
 * Created by PPH on 18/1/23.
 */

public class HeadView extends BaseHeaderView {
    private TextView textView;
    private View tagImg;
    private TextView refreshHintTv;
    private View progress;
    private Context context;

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.head_view_layout, this, true);
        textView = findViewById(R.id.head_text);
        tagImg = findViewById(R.id.head_tag);
        progress = findViewById(R.id.head_progress);
        refreshHintTv = findViewById(R.id.refresh_hint_tv);
        showRefreshView(true);
    }

    @Override
    protected void onStateChange(int state) {
        if (textView == null || tagImg == null || progress == null || null == refreshHintTv) {
            return;
        }
        switch (state) {
            case NONE:
                break;
            case PULLING:
                showRefreshView(true);
                textView.setText("下拉刷新");
                AnimUtil.startRotation(tagImg, 0);
                break;
            case LOOSENT_O_REFRESH:
                textView.setText("松开刷新");
                AnimUtil.startRotation(tagImg, 180);
                break;
            case REFRESHING:
                showRefreshView(false);
                refreshHintTv.setText("正在刷新");
                AnimUtil.startShow(progress, 0.1f, 400, 200);
                break;
            case REFRESH_CLONE:
                progress.setVisibility(GONE);
                refreshHintTv.setText("刷新完成");
                break;
        }

    }

    //控制刷新数据
    private void showRefreshView(boolean isFirst) {
        if (!isFirst) {
            textView.setVisibility(INVISIBLE);
            tagImg.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
            refreshHintTv.setVisibility(VISIBLE);
        } else {
            refreshHintTv.setVisibility(INVISIBLE);
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
