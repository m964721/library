package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.gobestsoft.applibrary.R;
import com.app.dialoglibrary.wheel.ArrayWheelAdapter;
import com.app.dialoglibrary.wheel.OnWheelChangedListener;
import com.app.dialoglibrary.wheel.WheelView;
import com.app.toolboxlibrary.DateUtil;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间选择器(年月日三个选项)
 */
public class ThreeTimeDialog extends BaseDialog {
    private TextView title_line_tv;//分割线
    private TextView dialog_left_tv;//取消
    private TextView dialog_title_tv;//标题
    private TextView dialog_right_tv;//确定
    private WheelView yearWheelview;//时间选择view 年份
    private WheelView monthWheelview;//时间选择view 月份
    private WheelView dayWheelview;//时间选择view 号数
    private ArrayWheelAdapter<String> arrayTypeAdapter;//适配器

    private String dataYearString; //选中的数据(年月日)
    private String dataMonthString; //选中的数据(年月日)
    private String dataDayString; //选中的数据(年月日)

    private int yearValue = 0; //选中的index 年
    private int monthValue = 0; //选中的index 月
    private int dayValue = 0; //选中的index 日

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
    private List<String> yearDataList;//数据来源
    private List<String> monthDataList;//数据来源
    private List<String> dayDataList;//数据来源
    private String userTime = "";//格式yyyymmdd

    private ThreeTimeDialog(Context context, Builder builder) {
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
        this.userTime = builder.userTime;
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
        yearWheelview = findViewById(R.id.dialog_wheelview);
        monthWheelview = findViewById(R.id.dialog_wheelview1);
        dayWheelview = findViewById(R.id.dialog_wheelview2);
        monthWheelview.setVisibility(View.VISIBLE);
        dayWheelview.setVisibility(View.VISIBLE);

        dialog_left_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (isChooseLeftButton) {
                    getOnChooseClick().onChooseData(yearValue,
                            dataYearString.replace("年", "-") +
                                    dataMonthString.replace("月", "-") +
                                    dataDayString.replace("号", "")
                    );
                }
            }
        });
        dialog_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (!isChooseLeftButton) {
                    getOnChooseClick().onChooseData(yearValue,
                            dataYearString.replace("年", "-") +
                                    dataMonthString.replace("月", "-") +
                                    dataDayString.replace("号", "")
                    );
                }
            }
        });
        initShowTimeData();
        setShowDialogData();
    }

    //初始化当前时间list
    private void initShowTimeData() {
        int year = DateUtil.getYear(new Date());
        int month = DateUtil.getMonth(new Date());
        int day = DateUtil.getDay(new Date());
        yearDataList = new ArrayList<>();
        monthDataList = new ArrayList<>();
        dayDataList = new ArrayList<>();
        for (int i = 1900; i < year + 1; i++) {
            yearDataList.add(i + "年");
        }
        for (int i = 1; i < month + 1; i++) {
            monthDataList.add(DateUtil.dateTofillero(i) + "月");
        }
        for (int i = 1; i < day + 1; i++) {
            dayDataList.add(DateUtil.dateTofillero(i) + "号");
        }

        //默认选中当前年月日对应的位置
        yearValue = yearDataList.size() - 1;
        monthValue = monthDataList.size() - 1;
        dayValue = dayDataList.size() - 1;

        yearWheelview.setViewAdapter(new ArrayWheelAdapter<>(context, yearDataList));
        monthWheelview.setViewAdapter(new ArrayWheelAdapter<>(context, monthDataList));
        arrayTypeAdapter = new ArrayWheelAdapter<>(context, dayDataList);
        dayWheelview.setViewAdapter(arrayTypeAdapter);

        yearWheelview.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogUtil.showLog("addChangingListener", "选中年份：:" + yearDataList.get(newValue));
                dataYearString = "" + yearDataList.get(newValue);
                yearValue = newValue;
                changeDayToShow();
            }
        });
        monthWheelview.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogUtil.showLog("addChangingListener", "选中月份：:" + monthDataList.get(newValue));
                dataMonthString = "" + monthDataList.get(newValue);
                monthValue = newValue;
                changeDayToShow();
            }
        });
        dayWheelview.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogUtil.showLog("addChangingListener", "选中号数：:" + dayDataList.get(newValue));
                dataDayString = "" + dayDataList.get(newValue);
                dayValue = newValue;
            }
        });
    }

    //选择年份，月份之后显示号数
    private void changeDayToShow() {
        int year = Integer.parseInt(yearDataList.get(yearValue).replace("年", ""));
        int month = Integer.parseInt(monthDataList.get(monthValue).replace("月", ""));
        int days = DateUtil.getMonthEndDay(year, month);
        dayDataList.clear();
        for (int i = 1; i < days + 1; i++) {
            dayDataList.add(DateUtil.dateTofillero(i) + "号");
        }
        if (2 == month) {
            arrayTypeAdapter = new ArrayWheelAdapter<>(context, dayDataList);
            dayWheelview.setViewAdapter(arrayTypeAdapter);
        }
        //是否越界
        if (dayValue < dayDataList.size()) {
            dayWheelview.setCurrentItem(dayValue);//默认之前的数据
        } else {
            dayWheelview.setCurrentItem(0);//默认第一个
        }
    }

    //设置标题栏
    public void setTitle(String titleData){
        this.titleData = titleData ;
        dialog_title_tv.setText(titleData);
    }

    //设置默认选中显示的年月日
    public void setShowTime(String chooseTime) {
        if (StringUtils.isStringToNUll(chooseTime)) {
            yearWheelview.setCurrentItem(yearValue);
            monthWheelview.setCurrentItem(monthValue);
            dayWheelview.setCurrentItem(dayValue);
            return;
        }
        int year = DateUtil.getYear(chooseTime);
        int month = DateUtil.getMonth(chooseTime);
        int day = DateUtil.getDay(chooseTime);

        for (int i = 0; i < yearDataList.size(); i++) {
            if (year == Integer.parseInt(yearDataList.get(i).replace("年", ""))) {
                yearWheelview.setCurrentItem(i);
                continue;
            }
        }
        for (int i = 0; i < monthDataList.size(); i++) {
            if (month == Integer.parseInt(monthDataList.get(i).replace("月", ""))) {
                monthWheelview.setCurrentItem(i);
                break;
            }
        }
        for (int i = 0; i < dayDataList.size(); i++) {
            if (day == Integer.parseInt(dayDataList.get(i).replace("号", ""))) {
                dayWheelview.setCurrentItem(i);
                break;
            }
        }

    }

    public ThreeTimeDialog.onChooseClick getOnChooseClick() {
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
        setShowTime(userTime);
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
        private String userTime = "";//格式yyyymmdd

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

        public Builder setOnChooseClick(ThreeTimeDialog.onChooseClick onChooseClick) {
            this.onChooseClick = onChooseClick;
            return this;
        }

        public Builder setUserTime(String userTime) {
            this.userTime = userTime;
            return this;
        }

        public ThreeTimeDialog build(Context context) {
            return new ThreeTimeDialog(context, this);
        }
    }
}
