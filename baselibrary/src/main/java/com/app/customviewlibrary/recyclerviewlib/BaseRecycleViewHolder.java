package com.app.customviewlibrary.recyclerviewlib;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.app.toolboxlibrary.DateUtil;

import java.util.Date;

/**
 * setItemDataToShow需重写，返回对应item自定义的bean对象，有外部去控制显示内容
 */
public abstract class BaseRecycleViewHolder extends RecyclerView.ViewHolder {
    public static final int MIN_CLICK_DELAY_TIME = 300;
    private long lastClickTime = 0;
    protected View chooseItemView;
    BaseRecycleItemClick baseRecycleItemClick;
    BaseRecycleItemLongClick baseRecycleItemLongClick;

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
        chooseItemView = itemView;
    }

    public abstract void setItemDataToShow(int position, Object object);

    public void setBaseRecycleItemClick(BaseRecycleItemClick baseRecycleItemClick, int itemViewType) {
        if (null != baseRecycleItemClick && BaseRecycleConfig.defaultImageView != itemViewType) {
            this.baseRecycleItemClick = baseRecycleItemClick;
            chooseItemView.setOnClickListener(onClickListener);
        }
    }

    public void setBaseRecycleItemLongClick(BaseRecycleItemLongClick baseRecycleItemLongClick, int itemViewType) {
        if (null != baseRecycleItemLongClick && BaseRecycleConfig.defaultImageView != itemViewType) {
            this.baseRecycleItemLongClick = baseRecycleItemLongClick;
            chooseItemView.setOnLongClickListener(onLongClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long currentTime = DateUtil.getMillis(new Date());
            if (currentTime - lastClickTime <= MIN_CLICK_DELAY_TIME) {
                return;
            } else {
                lastClickTime = currentTime;
                baseRecycleItemClick.OnItemClickListener(v, getLayoutPosition());
            }

        }
    };

    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            baseRecycleItemLongClick.OnItemLongClickListener(v, getLayoutPosition());
            return true;
        }
    };
}
