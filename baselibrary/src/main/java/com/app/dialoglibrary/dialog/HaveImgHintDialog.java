package com.app.dialoglibrary.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.toolboxlibrary.InfoDialogListener;
import com.app.toolboxlibrary.PhoneinfoUtils;
import com.gobestsoft.applibrary.R;

/**
 * 弹窗提示
 */

public class HaveImgHintDialog extends BaseDialog {
    private LinearLayout have_dialog_layout;//最外层布局
    private ImageView dialog_hint_iv;//提示的图片
    private Button leftButton, rightButton;//底部按钮
    private TextView haveDialogLineTv;//水平分割线
    private TextView haveButtonLineTv;//按钮中间的

    private int imgRes = 0;
    private String leftData;//左边按钮显示内容
    private String rightData;//右边显示内容
    private int leftButtonTextColor;//显示颜色,传值显示传入的颜色，没有则显示默认
    private int rightButtonTextColor;
    private boolean isShowLeftButton;
    private boolean isShowRightButton;
    private boolean isShowDialogLine;
    private boolean isShowButtonLine;

    private HaveImgHintDialog(Context context, Builder dialogBuilder) {
        super(context, R.style.my_dialog);
        this.imgRes = dialogBuilder.imgRes;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_img_hint);
        initView();
    }

    private void initView() {
        have_dialog_layout = findViewById(R.id.have_dialog_layout);
        dialog_hint_iv = findViewById(R.id.dialog_hint_iv);
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
        dialog_hint_iv.setImageResource(imgRes);

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

    }

    //工厂模式
    public static class Builder {
        private int imgRes = 0;//展示图片资源
        private String leftData;
        private String rightData;
        private int leftButtonTextColor = 0;
        private int rightButtonTextColor = 0;
        private boolean isShowLeftButton = true;
        private boolean isShowRightButton = true;
        private boolean isShowDialogLine = true;
        private boolean isShowButtonLine = true;
        private InfoDialogListener onClickDialog;
        private float wigthSize = 0.7f;

        public Builder setImgRes(int imgRes) {
            this.imgRes = imgRes;
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

        public HaveImgHintDialog build(Context context) {
            return new HaveImgHintDialog(context, this);
        }
    }

    //替换资源
    public void setShowImgRes(int res){
        dialog_hint_iv.setImageResource(res);
    }
}
