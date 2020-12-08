package com.app.dialoglibrary.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gobestsoft.applibrary.R;
import com.app.dialoglibrary.InfoDialogListener;
import com.app.toolboxlibrary.PhoneinfoUtils;

/**
 * 附带输入框的
 */

public class InputDialog extends BaseDialog {
    private LinearLayout have_dialog_layout;
    private TextView titleTextview;
    private EditText dialogInputEt;
    private TextView next_tv ;//多余按钮
    private Button leftButton, rightButton;//底部按钮
    private TextView haveDialogLineTv;//水平分割线
    private TextView haveButtonLineTv;//按钮中间的

    private String title;
    private String hintBody;
    private String leftData;
    private String rightData;
    private int leftButtonTextColor ;
    private int rightButtonTextColor ;
    private boolean isShowLeftButton ;
    private boolean isShowRightButton ;
    private boolean isShowDialogLine ;
    private boolean isShowButtonLine;
    private float wigthSize ;
    private int inputType ;
    private int maxLength ;
    private onChooseClick onChooseClick;//回调事件
    private InputDialog(Context context, Builder dialogBuilder) {
        super(context);
        this.title = dialogBuilder.title;
        this.hintBody = dialogBuilder.hintBody;
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
        this.inputType = dialogBuilder.inputType;
        this.maxLength = dialogBuilder.maxLength;
        this.onChooseClick = dialogBuilder.onChooseClick ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_layout);
        initView();
    }

    private void initView() {
        have_dialog_layout = findViewById(R.id.have_dialog_layout);
        titleTextview = findViewById(R.id.title_textview);
        dialogInputEt = findViewById(R.id.dialog_input_et);
        leftButton = findViewById(R.id.cancel_button);
        rightButton = findViewById(R.id.confirm_button);
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
                onChooseClick.onChooseData(0,dialogInputEt.getText().toString());
                getOnClickDialog().onRightButtonClicked();
            }
        });
        ViewGroup.LayoutParams lp = have_dialog_layout.getLayoutParams();
        lp.width = (int) (PhoneinfoUtils.getWindowsWidth((Activity) context) * wigthSize + 0.5);
        have_dialog_layout.setLayoutParams(lp);

        setTvData();
    }

    //设置显示的文字
    private void setTvData() {
        if (title == null || title.isEmpty()) {
            titleTextview.setVisibility(View.GONE);
        } else {
            titleTextview.setVisibility(View.VISIBLE);
            titleTextview.setText(title);
        }
        dialogInputEt.setHint(hintBody);
        if (-1 != inputType) {
            dialogInputEt.setInputType(inputType);
        }
        if (-1 != maxLength) {
            dialogInputEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
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

    //清空输入数据
    public void clearInputData(int inputType,int maxLength){
        dialogInputEt.setText("");
        if (-1 != inputType) {
            dialogInputEt.setInputType(inputType);
        }
        if (-1 != maxLength) {
            dialogInputEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
    }

    //修改标题
    public void setTitle(String showTitile){
        this.title = showTitile ;
        if (title == null || title.isEmpty()) {
            titleTextview.setVisibility(View.GONE);
        } else {
            titleTextview.setVisibility(View.VISIBLE);
            titleTextview.setText(title);
        }
    }

    public static class Builder {
        private String title;
        private String hintBody;
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
        private int inputType = -1;
        private int maxLength = -1;
        private onChooseClick onChooseClick;//回调事件
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setHintBody(String hintBody) {
            this.hintBody = hintBody;
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

        public Builder setInputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        public Builder setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Builder setOnChooseClick(TimeDialog.onChooseClick onChooseClick) {
            this.onChooseClick = onChooseClick;
            return this;
        }

        public InputDialog build(Context context) {
            return new InputDialog(context, this);
        }
    }
}
