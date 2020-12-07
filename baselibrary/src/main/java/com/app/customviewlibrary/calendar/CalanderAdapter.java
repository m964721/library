package com.app.customviewlibrary.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.customviewlibrary.recyclerviewlib.BaseInfo;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleAdapter;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleConfig;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleViewHolder;
import com.gobestsoft.applibrary.R;

import java.util.List;

public class CalanderAdapter extends BaseRecycleAdapter {

    int chooseIndex = -1;

    public CalanderAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new CalanderViewHolder(backView(
                R.layout.calendar_item_layout,
                viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder((BaseRecycleViewHolder) holder, position);
        } else {
            BaseInfo baseInfo = (BaseInfo) payloads.get(0);
            if (baseInfo instanceof CalendarInfo) {
                CalendarInfo calendarInfo = (CalendarInfo) baseInfo;
                CalanderViewHolder calanderViewHolder = (CalanderViewHolder) holder;
                if (calendarInfo.getIsChoose()) {
                    calanderViewHolder.calendarHintTv.setBackgroundResource(R.drawable.shape_choose);
                } else {
                    calanderViewHolder.calendarHintTv.setBackgroundResource(calendarInfo.getBgRes());
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        BaseInfo baseInfo = (BaseInfo) dataList.get(position);
        return baseInfo.getViewType();
    }

    //日历数据item
    class CalanderViewHolder extends BaseRecycleViewHolder {
        private TextView calendarHintTv ;
        View chooseItemView;
        public CalanderViewHolder(View itemView) {
            super(itemView);
            chooseItemView = itemView ;
            calendarHintTv = itemView.findViewById(R.id.calendar_hint_tv);
        }

        @Override
        public void setItemDataToShow(final int position, Object object) {
            if (null != object) {
                CalendarInfo calendarInfo = (CalendarInfo) object;
                calendarHintTv.setText(""+calendarInfo.getData());
                calendarHintTv.setBackgroundResource(calendarInfo.getBgRes());
                if (calendarInfo.getIsClick()) {
                    chooseItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //不等-1说明已有选中的
                            if (chooseIndex != -1 && chooseIndex != position) {
                                isChooseView(false, chooseIndex);
                            }
                            isChooseView(true, position);
                            chooseIndex = position;
                        }
                    });
                }
            }
        }

        //设置选中
        private void isChooseView(boolean ischoose, int position) {
            CalendarInfo info = (CalendarInfo) dataList.get(position);
            info.setChoose(ischoose);
            dataList.set(position, info);
            calendarHintTv.setBackgroundResource(R.drawable.shape_choose);
            getBaseRecycleItemViewCLick().onItemChildrenViewClickListener(BaseRecycleConfig.viewClickFlag1000,
                    position);
        }
    }
}
