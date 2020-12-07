package com.app.customviewlibrary.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.app.customviewlibrary.recyclerviewlib.BaseRecycleConfig;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleItemViewCLick;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleView;
import com.app.toolboxlibrary.DateUtil;
import com.app.toolboxlibrary.LogUtil;
import com.gobestsoft.applibrary.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日历布局
 */
public class CalendarLayout extends LinearLayout {
    private String tag = this.getClass().getName();
    Context context;
    //提示文字
    private ImageView backLastIv;
    private TextView showThisMonthTv;
    private ImageView backNextIv;
    private TextView weekHint7Tv;
    private TextView weekHint1Tv;
    private TextView weekHint2Tv;
    private TextView weekHint3Tv;
    private TextView weekHint4Tv;
    private TextView weekHint5Tv;
    private TextView weekHint6Tv;
    private BaseRecycleView calendarRecycleview;
    List<CalendarInfo> listThisMonth = new ArrayList<>();//当前月数据
    List<CalendarInfo> listNextMonth = new ArrayList<>();//下个月份数据
    List<CalendarInfo> listShowMonth = new ArrayList<>();//当前月数据
    CalanderAdapter calanderAdapter;//适配器
    int year;//年
    int month;//月
    int choosePosition = -1;//选择数据
    int startDate = 0;//月份起始日
    int endDate = 0;//月份截止日
    boolean isNext = true;//是否点击左右按钮

    boolean show_week_hint = true;//顶部怎么显示

    public CalendarLayout(Context context) {
        this(context, null);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs, defStyleAttr);
    }


    private void initView(AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_layout, this);
        backLastIv = findViewById(R.id.back_last_iv);
        showThisMonthTv = findViewById(R.id.show_this_month_tv);
        backNextIv = findViewById(R.id.back_next_iv);

        weekHint7Tv = findViewById(R.id.week_hint7_tv);
        weekHint1Tv = findViewById(R.id.week_hint1_tv);
        weekHint2Tv = findViewById(R.id.week_hint2_tv);
        weekHint3Tv = findViewById(R.id.week_hint3_tv);
        weekHint4Tv = findViewById(R.id.week_hint4_tv);
        weekHint5Tv = findViewById(R.id.week_hint5_tv);
        weekHint6Tv = findViewById(R.id.week_hint6_tv);

        calendarRecycleview = findViewById(R.id.calendar_recycleview);
        calendarRecycleview.setLayoutManager(new GridLayoutManager(context, 7));

        calendarRecycleview.setOnBaseRecycleItemViewCLick(new BaseRecycleItemViewCLick() {
            @Override
            public void onItemChildrenViewClickListener(int viewClickFlag, int position) {
                switch (viewClickFlag) {
                    case BaseRecycleConfig.viewClickFlag1000:
                        listShowMonth = calanderAdapter.getDataList();
                        calanderAdapter.notifyItemChanged(
                                position, listShowMonth.get(position)
                        );
                        choosePosition = position;
                        break;
                }
            }
        });

        initShowHint(show_week_hint);
        initListView();
        backLastIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNext) {
                    isNext = true;
                    if (month > 1) {
                        month = month - 1;
                    } else {
                        month = 12;
                        year--;
                    }

                    listThisMonth = backListMonthData(month, isNext);
                    showHintYM(listThisMonth);
                }
            }
        });
        backNextIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNext) {
                    isNext = false;
                    if (month < 12) {
                        month = month + 1;
                    } else {
                        year++;
                        month = 1;
                    }
                    listNextMonth = backListMonthData(month, isNext);
                    showHintYM(listNextMonth);
                }
            }
        });
    }

    //顶部显示周几字样
    public void initShowHint(boolean showWeekHint) {
        if (showWeekHint) {
            weekHint7Tv.setText("星期日");
            weekHint1Tv.setText("星期一");
            weekHint2Tv.setText("星期二");
            weekHint3Tv.setText("星期三");
            weekHint4Tv.setText("星期四");
            weekHint5Tv.setText("星期五");
            weekHint6Tv.setText("星期六");
        } else {
            weekHint7Tv.setText("日");
            weekHint1Tv.setText("一");
            weekHint2Tv.setText("二");
            weekHint3Tv.setText("三");
            weekHint4Tv.setText("四");
            weekHint5Tv.setText("五");
            weekHint6Tv.setText("六");
        }
    }

    //获取选中的数据
    public String getChooseDate() {
        String chooseData = "";
        if (choosePosition != -1) {
            CalendarInfo calendarInfo = listShowMonth.get(choosePosition);
            if (calendarInfo.getIsChoose()) {
                chooseData = calendarInfo.getYear() + "-" + DateUtil.dateTofillero(calendarInfo.getMonth()) + "-" + DateUtil.dateTofillero(calendarInfo.getData());
            }
        }
        return chooseData;
    }

    //设置起始或者结束时间
    public void setShowData(int startDate, int endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        listThisMonth = backListMonthData(month, isNext);
        showHintYM(listThisMonth);
    }

    //初始化calendar Recycleview
    private void initListView() {
        Date date = new Date();
        year = DateUtil.getYear(date);
        month = DateUtil.getMonth(date);
    }

    //显示月份数据
    private void showHintYM(List<CalendarInfo> showMonth) {
        listShowMonth = showMonth;
        if (null != calanderAdapter) {
            calanderAdapter.setData(true, listShowMonth);
        } else {
            calanderAdapter = new CalanderAdapter(context, listShowMonth);
            calendarRecycleview.setAdapter(calanderAdapter);
        }
    }

    //返回显示的数据集合
    private List<CalendarInfo> backListMonthData(int monthNum, boolean monthIsNext) {
        LogUtil.showLog(tag, "monthNum:" + monthNum);
        List<CalendarInfo> listMonth = new ArrayList<>();
        String showYMData = year + "年" + monthNum + "月";
        showThisMonthTv.setText(showYMData);
        //由于外部显示月份是下标+1，所以取数据的时候再减一
        int getMonthEndDay = DateUtil.getMonthEndDay(year, monthNum);//返回选择月天数
        //优先判断开始第一天是周几
        int weekStart = DateUtil.getMonthBegin(monthNum);
        int getLastEnd = DateUtil.getMonthEndDay(year, monthNum - 1);//上个月天数
        int getNextEnd = DateUtil.getMonthEndDay(year, monthNum + 1);//下个月天数
        int getNextDay = 5 * 7 - weekStart - getMonthEndDay;//下个月显示几天数据
        LogUtil.showLog(tag, "weekStart:" + weekStart);
        LogUtil.showLog(tag, "getLastEnd:" + getLastEnd);
        LogUtil.showLog(tag, "getNextDay:" + getNextDay);
        LogUtil.showLog(tag, "monthIsNext:" + monthIsNext);
        /**
         * 第一步先加入，上个月月末的几天，根据当前月份第一天是周几去加入weekStart的天数,
         * 如果一号是周日，则不执行此操作
         */
        if (weekStart != 7) {
            for (int i = 0; i < weekStart; i++) {
                CalendarInfo calendarInfo = new CalendarInfo();
                calendarInfo.setYear(year);
                calendarInfo.setMonth(monthNum - 1);
                calendarInfo.setMonthDay(getLastEnd);
                calendarInfo.setData(getLastEnd - weekStart + (i + 1));
                calendarInfo.setClick(!monthIsNext);
                if (!monthIsNext) {
                    calendarInfo.setBgRes(R.drawable.shape_choose_un);
                } else {
                    calendarInfo.setBgRes(R.drawable.shape_default);
                }
                listMonth.add(calendarInfo);
            }
        }
        /**
         * 第二步添加，当前选择月份的天数，
         * isNext 确定显示的是当前月还是下一个月份
         * startDate ：当前时间的日期
         * endDate ：结束的日期
         */
        for (int i = 0; i < getMonthEndDay; i++) {
            CalendarInfo calendarInfo = new CalendarInfo();
            calendarInfo.setMonthDay(getMonthEndDay);
            calendarInfo.setYear(year);
            calendarInfo.setMonth(monthNum);
            calendarInfo.setData(i + 1);
            if (isNext) {
                if (i >= startDate - 1) {
                    calendarInfo.setClick(true);
                    calendarInfo.setBgRes(R.drawable.shape_choose_un);
                } else {
                    calendarInfo.setBgRes(R.drawable.shape_default);
                    calendarInfo.setClick(false);
                }
            } else {
                if (i < endDate) {
                    calendarInfo.setClick(true);
                    calendarInfo.setBgRes(R.drawable.shape_choose_un);
                } else {
                    calendarInfo.setBgRes(R.drawable.shape_default);
                    calendarInfo.setClick(false);
                }
            }
            listMonth.add(calendarInfo);
        }
        /**
         * 第三步：补充数据，如果显示的是当前月，则下个月份补充N天，可点击
         * 若果显示的是下一个月，则补充天数不接点击
         */
        for (int i = 0; i < getNextDay; i++) {
            CalendarInfo calendarInfo = new CalendarInfo();
            calendarInfo.setYear(year);
            calendarInfo.setMonth(monthNum + 1);
            calendarInfo.setData(i + 1);
            calendarInfo.setMonthDay(getNextEnd);
            if (monthIsNext) {
                if (i < endDate) {
                    calendarInfo.setClick(monthIsNext);
                    calendarInfo.setBgRes(R.drawable.shape_choose_un);
                } else {
                    calendarInfo.setClick(!monthIsNext);
                    calendarInfo.setBgRes(R.drawable.shape_default);
                }
            } else {
                calendarInfo.setBgRes(R.drawable.shape_default);
            }
            listMonth.add(calendarInfo);
        }
        return listMonth;
    }
}
