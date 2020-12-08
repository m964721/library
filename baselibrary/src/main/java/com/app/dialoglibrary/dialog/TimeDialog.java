package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.library.R;
import com.app.dialoglibrary.wheel.ArrayWheelAdapter;
import com.app.dialoglibrary.wheel.OnWheelChangedListener;
import com.app.dialoglibrary.wheel.WheelView;
import com.app.toolboxlibrary.ListUtils;
import com.app.toolboxlibrary.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间选择器(只有一个选项)
 */
public class TimeDialog extends BaseDialog {
    private TextView title_line_tv;//分割线
    private TextView dialog_left_tv;//取消
    private TextView dialog_title_tv;//标题
    private TextView dialog_right_tv;//确定
    private WheelView dialog_wheelview;//时间选择view
    private ArrayWheelAdapter<String> arrayTypeAdapter;//适配器

    private String dataString;//选中的数据
    private int value = 0;//选中的index

    //底部上方，左边文字，标题，右边文字
    private float leftSize = 0f;
    private String leftData = "";
    private int leftColor = 0;

    private float titleSize = 0f;
    private String titleData = "";
    private int titleColor = 0;

    private float rightSize = 0f;
    private String rightData = "";
    private int rightColor = 0;

    private int titleLineColor = 0;//分割线颜色
    private boolean isShowTitleLine = true; //是否显示分割线
    private boolean isChooseLeftButton = false; //选择那个返回数据,默认右边的按钮
    private onChooseClick onChooseClick;//回调事件
    private List<String> dataList = new ArrayList<>();//数据来源
    private int showIndex;//选中的index

    private TimeDialog(Context context, Builder builder) {
        super(context);
        this.leftSize = builder.leftSize;
        this.leftData = builder.leftData;
        this.leftColor = builder.leftColor;

        this.titleSize = builder.titleSize;
        this.titleData = builder.titleData;
        this.titleColor = builder.titleColor;

        this.rightSize = builder.rightSize;
        this.rightData = builder.rightData;
        this.rightColor = builder.rightColor;

        this.titleLineColor = builder.titleLineColor;
        this.isShowTitleLine = builder.isShowTitleLine;
        this.isChooseLeftButton = builder.isChooseLeftButton;
        this.setOnChooseClick(builder.onChooseClick);
        this.dataList = builder.dataList;
        this.showIndex = builder.showIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time_layout);
        initView();
    }

    //初始化
    private void initView() {
        title_line_tv = findViewById(R.id.title_line_tv);
        dialog_left_tv = findViewById(R.id.dialog_left_tv);
        dialog_title_tv = findViewById(R.id.dialog_title_tv);
        dialog_right_tv = findViewById(R.id.dialog_right_tv);
        dialog_wheelview = findViewById(R.id.dialog_wheelview);

        dialog_wheelview.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogUtil.showLog("选中", dataList.get(newValue));
                dataString = dataList.get(newValue);
                value = newValue;
            }
        });
        dialog_left_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (isChooseLeftButton) {
                    getOnChooseClick().onChooseData(value, dataString);
                }
            }
        });
        dialog_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (!isChooseLeftButton) {
                    getOnChooseClick().onChooseData(value, dataString);
                }
            }
        });

        setShowDialogData();
    }

    //设置默认选中的位置
    public void setCurrentItem(int index) {
        dialog_wheelview.setCurrentItem(index);
    }

    public TimeDialog.onChooseClick getOnChooseClick() {
        if (null == onChooseClick) {
            onChooseClick = new onChooseClick() {
                @Override
                public void onChooseData(int newValue, String chooseData) {

                }
            };
        }
        return onChooseClick;
    }

    public void setOnChooseClick(BaseDialog.onChooseClick onChooseClick) {
        this.onChooseClick = onChooseClick;
    }

    //显示dialog
    private void setShowDialogData() {
        if (null != leftData && leftData.length() > 0) {
            dialog_left_tv.setVisibility(View.VISIBLE);
            dialog_left_tv.setText(leftData);
            dialog_left_tv.setTextSize(leftSize != 0 ? leftSize : 14f);
            if (leftColor != -1) {
                dialog_left_tv.setTextColor(leftColor);
            }
        } else {
            dialog_left_tv.setVisibility(View.GONE);
        }
        if (null != titleData && titleData.length() > 0) {
            dialog_title_tv.setVisibility(View.VISIBLE);
            dialog_title_tv.setText(titleData);
            dialog_title_tv.setTextSize(titleSize != 0 ? titleSize : 14f);
            if (titleColor != -1) {
                dialog_title_tv.setTextColor(titleColor);
            }
        } else {
            dialog_title_tv.setVisibility(View.GONE);
        }
        if (null != rightData && rightData.length() > 0) {
            dialog_right_tv.setVisibility(View.VISIBLE);
            dialog_right_tv.setText(rightData);
            dialog_right_tv.setTextSize(rightSize != 0 ? rightSize : 14f);
            if (rightColor != -1) {
                dialog_right_tv.setTextColor(rightColor);
            }
        } else {
            dialog_right_tv.setVisibility(View.GONE);
        }
        title_line_tv.setVisibility(isShowTitleLine ? View.VISIBLE : View.GONE);
        title_line_tv.setBackgroundColor(titleLineColor != -1 ? titleLineColor : 0x000000);

        setCurrentItem(showIndex);
    }

    //设置数据
    public void setDataList(List<String> sendDataList) {
        if(ListUtils.backArrayListSize(sendDataList)>0){
            value = 0 ;
            showIndex = 0 ;
            this.dataList = sendDataList;
            dataString = dataList.get(value);
            arrayTypeAdapter = new ArrayWheelAdapter<>(context, dataList);
            dialog_wheelview.setViewAdapter(arrayTypeAdapter);
            setCurrentItem(showIndex);
        }
    }

    //更改标题
    public void setTitleData(String title) {
        dialog_title_tv.setText("" + title);
    }

    //Builder模式
    public static class Builder {
        //底部上方，左边文字，标题，右边文字
        private float leftSize = 0f;
        private String leftData = "";
        private int leftColor = -1;

        private float titleSize = 0f;
        private String titleData = "";
        private int titleColor = -1;

        private float rightSize = 0f;
        private String rightData = "";
        private int rightColor = -1;

        private int titleLineColor = -1;//分割线颜色
        private boolean isShowTitleLine = true; //是否显示分割线
        private boolean isChooseLeftButton = false; //选择那个返回数据,默认右边的按钮
        private onChooseClick onChooseClick;//回调事件
        private List<String> dataList;//数据来源

        private int showIndex = 0;//默认显示

        public Builder setLeftSize(float leftSize) {
            this.leftSize = leftSize;
            return this;
        }

        public Builder setLeftData(String leftData) {
            this.leftData = leftData;
            return this;
        }

        public Builder setLeftColor(int leftColor) {
            this.leftColor = leftColor;
            return this;
        }

        public Builder setTitleSize(float titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public Builder setTitleData(String titleData) {
            this.titleData = titleData;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setRightSize(float rightSize) {
            this.rightSize = rightSize;
            return this;
        }

        public Builder setRightData(String rightData) {
            this.rightData = rightData;
            return this;
        }

        public Builder setRightColor(int rightColor) {
            this.rightColor = rightColor;
            return this;
        }

        public Builder setTitleLineColor(int titleLineColor) {
            this.titleLineColor = titleLineColor;
            return this;
        }

        public Builder setShowTitleLine(boolean showTitleLine) {
            isShowTitleLine = showTitleLine;
            return this;
        }

        public Builder setChooseLeftButton(boolean chooseLeftButton) {
            isChooseLeftButton = chooseLeftButton;
            return this;
        }

        public Builder setOnChooseClick(TimeDialog.onChooseClick onChooseClick) {
            this.onChooseClick = onChooseClick;
            return this;
        }

        public Builder setDataList(List<String> dataList) {
            this.dataList = dataList;
            return this;
        }

        public Builder setValue(int showIndex) {
            this.showIndex = showIndex;
            return this;
        }

        public TimeDialog build(Context context) {
            return new TimeDialog(context, this);
        }
    }
}
