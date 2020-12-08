package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.library.R;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 密码输入管理
 */
public class PasswordDialog extends BaseDialog {

    public static int FORGETPWHINT = 0;

    private TextView passwordTitleTv;
    private ImageView passwordCloseIv;

    //密码显示
    private TextView showPw1Tv;
    private TextView showPw2Tv;
    private TextView showPw3Tv;
    private TextView showPw4Tv;
    private TextView showPw5Tv;
    private TextView showPw6Tv;

    private TextView forgetPwHintTv;
    //输入0到9，删除按钮
    private TextView showPwInput1Tv;
    private TextView showPwInput2Tv;
    private TextView showPwInput3Tv;
    private TextView showPwInput4Tv;
    private TextView showPwInput5Tv;
    private TextView showPwInput6Tv;
    private TextView showPwInput7Tv;
    private TextView showPwInput8Tv;
    private TextView showPwInput9Tv;
    private TextView showPwInputTv;
    private TextView showPwInput0Tv;
    private RelativeLayout showPwInputDeleteLayout;

    //存放数据
    private List<TextView> showPwList = new ArrayList<>();
    private List<View> inpoutViewList = new ArrayList<>();
    String inputData = "";//输入密码

    InputFinish inputFinish;

    public PasswordDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password_layout);
        initView();
    }

    //初始化
    private void initView() {

        passwordTitleTv = (TextView) findViewById(R.id.password_title_tv);
        passwordCloseIv = (ImageView) findViewById(R.id.password_close_iv);
        showPw1Tv = (TextView) findViewById(R.id.show_pw1_tv);
        showPw2Tv = (TextView) findViewById(R.id.show_pw2_tv);
        showPw3Tv = (TextView) findViewById(R.id.show_pw3_tv);
        showPw4Tv = (TextView) findViewById(R.id.show_pw4_tv);
        showPw5Tv = (TextView) findViewById(R.id.show_pw5_tv);
        showPw6Tv = (TextView) findViewById(R.id.show_pw6_tv);
        forgetPwHintTv = (TextView) findViewById(R.id.forget_pw_hint_tv);
        showPwInput1Tv = (TextView) findViewById(R.id.show_pw_input1_tv);
        showPwInput2Tv = (TextView) findViewById(R.id.show_pw_input2_tv);
        showPwInput3Tv = (TextView) findViewById(R.id.show_pw_input3_tv);
        showPwInput4Tv = (TextView) findViewById(R.id.show_pw_input4_tv);
        showPwInput5Tv = (TextView) findViewById(R.id.show_pw_input5_tv);
        showPwInput6Tv = (TextView) findViewById(R.id.show_pw_input6_tv);
        showPwInput7Tv = (TextView) findViewById(R.id.show_pw_input7_tv);
        showPwInput8Tv = (TextView) findViewById(R.id.show_pw_input8_tv);
        showPwInput9Tv = (TextView) findViewById(R.id.show_pw_input9_tv);
        showPwInputTv = (TextView) findViewById(R.id.show_pw_input_tv);
        showPwInput0Tv = (TextView) findViewById(R.id.show_pw_input0_tv);
        showPwInputDeleteLayout = (RelativeLayout) findViewById(R.id.show_pw_input_delete_layout);

        showPwList.add(showPw1Tv);
        showPwList.add(showPw2Tv);
        showPwList.add(showPw3Tv);
        showPwList.add(showPw4Tv);
        showPwList.add(showPw5Tv);
        showPwList.add(showPw6Tv);

        inpoutViewList.add(passwordCloseIv);
        inpoutViewList.add(forgetPwHintTv);

        inpoutViewList.add(showPwInput0Tv);
        inpoutViewList.add(showPwInput1Tv);
        inpoutViewList.add(showPwInput2Tv);
        inpoutViewList.add(showPwInput3Tv);
        inpoutViewList.add(showPwInput4Tv);
        inpoutViewList.add(showPwInput5Tv);
        inpoutViewList.add(showPwInput6Tv);
        inpoutViewList.add(showPwInput7Tv);
        inpoutViewList.add(showPwInput8Tv);
        inpoutViewList.add(showPwInput9Tv);
        inpoutViewList.add(showPwInputTv);

        inpoutViewList.add(showPwInputDeleteLayout);

        for (int i = 0; i < inpoutViewList.size(); i++) {
            inpoutViewList.get(i).setOnClickListener(onClickListener);
        }
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == passwordCloseIv) {
                dismiss();
            } else if (v == forgetPwHintTv) {
                getOnClickDialog().onDialogItemClicked(FORGETPWHINT);
            } else if (v == showPwInputDeleteLayout) {
                dealWithInputData(false, null);
            } else {
                dealWithInputData(true, ((TextView) v).getText().toString());
            }
        }
    };

    //处理数据
    private void dealWithInputData(boolean isInput, String input) {
        if (isInput) {
            inputData = inputData + input;
            if (inputData.length() == 6) {
                getInputFinish().OnInputFinishListener(inputData);
            }
        } else {
            if (!StringUtils.isStringToNUll(inputData)) {
                inputData = inputData.substring(0, inputData.length() - 1);
            }
        }
        LogUtil.showLog("dealWithInputData", "inputData: " + inputData);

        for (int i = 0; i < showPwList.size(); i++) {
            if (i < inputData.length()) {
                showPwList.get(i).setBackgroundResource(R.drawable.dot_shape);
            } else {
                showPwList.get(i).setBackgroundResource(R.drawable.dot_un_shape);
            }
        }
    }

    //获取监听
    public InputFinish getInputFinish() {
        if (null == inputFinish) {
            inputFinish = new InputFinish() {
                @Override
                public void OnInputFinishListener(String inputData) {

                }
            };
        }
        return inputFinish;
    }

    //设置输入结束监听
    public void setOnInputFinishListener(InputFinish inputFinish) {
        this.inputFinish = inputFinish;
    }

    public interface InputFinish {
        void OnInputFinishListener(String inputData);
    }

    @Override
    public void show() {
        inputData = "";
        dealWithInputData(false, null);
        super.show();
    }
}
