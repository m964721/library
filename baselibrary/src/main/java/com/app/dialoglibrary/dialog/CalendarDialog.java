package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.customviewlibrary.calendar.CalendarLayout;
import com.gobestsoft.applibrary.R;

/**
 * 包含日历的选择弹窗
 */
public class CalendarDialog extends BaseDialog {
    private CalendarLayout haveCalendarlayout;//日历选择
    private ImageView calendarCloseIv;//关闭
    private TextView calendarChooseTv;//选择

    public CalendarDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_have_calender_layout);
        initView();
    }

    private void initView() {
        haveCalendarlayout = (CalendarLayout) findViewById(R.id.have_calendarlayout);
        calendarCloseIv = (ImageView) findViewById(R.id.calendar_close_iv);
        calendarChooseTv = (TextView) findViewById(R.id.calendar_choose_tv);

        calendarCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        calendarChooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnClickDialog().onRightButtonClicked();
            }
        });

    }

    //外部设置
    public void setShowData(int startDate, int endDate) {
        haveCalendarlayout.setShowData(startDate, endDate);
    }

    //获取选中的数据
    public String getChooseDate() {
        String chooseDate = haveCalendarlayout.getChooseDate();
        return chooseDate;
    }
}
