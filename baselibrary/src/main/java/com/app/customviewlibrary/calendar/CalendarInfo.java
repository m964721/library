package com.app.customviewlibrary.calendar;

import com.app.customviewlibrary.recyclerviewlib.BaseInfo;

public class CalendarInfo extends BaseInfo {
    int year ;//年份
    int month ;//月份
    int monthDay;//选择月份的天数
    int data;//显示的数据
    boolean choose = false;//是否选中
    boolean isClick = false;//是否可以点击
    int bgRes;//设置背景图


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }


    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean getIsChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public boolean getIsClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getBgRes() {
        return bgRes;
    }

    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }
}
