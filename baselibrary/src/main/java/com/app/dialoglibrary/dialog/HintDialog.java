package com.app.dialoglibrary.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gobestsoft.applibrary.R;
import com.app.dialoglibrary.InfoDialogListener;
import com.app.toolboxlibrary.PhoneinfoUtils;

/**
 * 弹窗提示
 */

public class HintDialog extends BaseDialog {
    private LinearLayout have_dialog_layout;//最外层布局
    private TextView title_textview, body_textview;//标题和显示内容的textview
    private Button leftButton, rightButton;//底部按钮
    private TextView haveDialogLineTv;//水平分割线
    private TextView haveButtonLineTv;//按钮中间的
    private String title;//标题
    private String body;//内容
    private String leftData;//左边按钮显示内容
    private String rightData;//右边显示内容
    private int leftButtonTextColor;//显示颜色,传值显示传入的颜色，没有则显示默认
    private int rightButtonTextColor;
    private boolean isShowLeftButton;
    private boolean isShowRightButton;
    private boolean isShowDialogLine;
    private boolean isShowButtonLine;
    private boolean bodyLeftGravity ;
    private boolean isCancelable ;

    private HintDialog(Context context, Builder dialogBuilder) {
        super(context, R.style.my_dialog);
        this.title = dialogBuilder.title;
        this.body = dialogBuilder.body;
        this.leftData = dialogBuilder.leftData;
        this.rightData = dialogBuilder.rightData;
        this.leftButtonTextColor = dialogBuilder.leftButtonTextColor;
        this.rightButtonTextColor = dialogBuilder.rightButtonTextColor;
        this.isShowLeftButton = dialogBuilder.isShowLeftButton;
        this.isShowRightButton = dialogBuilder.isShowRightButton;
        this.isShowDialogLine = dialogBuilder.isShowDialogLine;
        this.isShowButtonLine = dialogBuilder.isShowButtonLine;
        this.setOnClickDialog(dialogBuilder.onClickDialog);
        this.wigthSize = dialogBuilder.wigthSize;
        this.bodyLeftGravity = dialogBuilder.bodyLeftGravity ;
        this.isCancelable = dialogBuilder.isCancelable ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);
        initView();
    }

    private void initView() {
        have_dialog_layout = findViewById(R.id.have_dialog_layout);
        title_textview = findViewById(R.id.title_textview);
        body_textview = findViewById(R.id.body_textview);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        haveDialogLineTv = findViewById(R.id.have_dialog_line_tv);
        haveButtonLineTv = findViewById(R.id.have_button_line_tv);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getOnClickDialog().onLeftButtonClicked();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getOnClickDialog().onRightButtonClicked();
            }
        });
        ViewGroup.LayoutParams lp = have_dialog_layout.getLayoutParams();
        lp.width = (int) (PhoneinfoUtils.getWindowsWidth((Activity) context) * wigthSize + 0.5);
        have_dialog_layout.setLayoutParams(lp);
        setTvData();
    }

    //设置显示的内容
    private void setTvData() {
        if (title == null || title.isEmpty()) {
            title_textview.setVisibility(View.GONE);
        } else {
            title_textview.setVisibility(View.VISIBLE);
            title_textview.setText(title);
        }
        if (body == null || body.isEmpty()) {
            body_textview.setVisibility(View.GONE);
        } else {
            body_textview.setVisibility(View.VISIBLE);
            body_textview.setText(body);
        }
        body_textview.setGravity( bodyLeftGravity ? Gravity.LEFT : Gravity.CENTER);
        leftButton.setVisibility(isShowLeftButton ? View.VISIBLE : View.GONE);
        leftButton.setText(leftData);
        if (leftButtonTextColor != 0) {
            leftButton.setTextColor(leftButtonTextColor);
        }
        rightButton.setVisibility(isShowRightButton ? View.VISIBLE : View.GONE);
        rightButton.setText(rightData);
        if (rightButtonTextColor != 0) {
            rightButton.setTextColor(rightButtonTextColor);
        }
        haveDialogLineTv.setVisibility(isShowDialogLine ? View.VISIBLE : View.GONE);
        haveButtonLineTv.setVisibility(isShowButtonLine ? View.VISIBLE : View.GONE);
        setCancelable(isCancelable);
    }

    public void setBody(String body) {
        if (body == null || body.isEmpty()) {
            body_textview.setVisibility(View.GONE);
        } else {
            body_textview.setVisibility(View.VISIBLE);
            body_textview.setText(body);
        }
    }

    //工厂模式
    public static class Builder {
        private String title;
        private String body;
        private String leftData;
        private String rightData;
        private boolean bodyLeftGravity = false ;
        private int leftButtonTextColor = 0;
        private int rightButtonTextColor = 0;
        private boolean isShowLeftButton = true;
        private boolean isShowRightButton = true;
        private boolean isShowDialogLine = true;
        private boolean isShowButtonLine = true;
        private InfoDialogListener onClickDialog;
        private float wigthSize = 0.7f;
        private boolean isCancelable ;


        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this ;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setLeftData(String leftData) {
            this.leftData = leftData;
            return this;
        }

        public Builder setRightData(String rightData) {
            this.rightData = rightData;
            return this;
        }

        public Builder setLeftButtonTextColor(int leftButtonTextColor) {
            this.leftButtonTextColor = leftButtonTextColor;
            return this;
        }

        public Builder setRightButtonTextColor(int rightButtonTextColor) {
            this.rightButtonTextColor = rightButtonTextColor;
            return this;
        }

        public Builder setShowLeftButton(boolean showLeftButton) {
            isShowLeftButton = showLeftButton;
            return this;
        }

        public Builder setShowRightButton(boolean showRightButton) {
            isShowRightButton = showRightButton;
            return this;
        }

        public Builder setShowDialogLine(boolean showDialogLine) {
            isShowDialogLine = showDialogLine;
            return this;
        }

        public Builder setShowButtonLine(boolean showButtonLine) {
            isShowButtonLine = showButtonLine;
            return this;
        }

        public Builder setOnClickDialog(InfoDialogListener onClickDialog) {
            this.onClickDialog = onClickDialog;
            return this;
        }

        public Builder setWigthSize(float wigthSize) {
            this.wigthSize = wigthSize;
            return this;
        }

        public Builder setBodyGravity(boolean bodyGravity) {
            this.bodyLeftGravity = bodyGravity;
            return this ;
        }

        public HintDialog build(Context context) {
            return new HintDialog(context, this);
        }
    }

}
