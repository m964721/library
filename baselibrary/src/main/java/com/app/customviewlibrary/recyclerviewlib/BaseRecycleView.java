package com.app.customviewlibrary.recyclerviewlib;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView:如果项目中需要刷新，则可以继承刷新的XXRecyclerView;
 * 增加item点击，长按监听事件方法
 */
public class BaseRecycleView extends RecyclerView {
    BaseRecycleAdapter baseRecycleAdapter ;
    BaseRecycleItemClick baseRecycleItemClick ;
    BaseRecycleItemLongClick baseRecycleItemLongClick ;
    BaseRecycleItemChildrenViewCLick baseRecycleItemChildrenViewCLick ;


    public BaseRecycleView(Context context) {
      super(context,null);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public BaseRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public BaseRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public void setAdapter( Adapter adapter) {
        baseRecycleAdapter = (BaseRecycleAdapter) adapter ;
        toRefresh();
        super.setAdapter(adapter);
    }

    public void setOnRecycleItemClick(BaseRecycleItemClick baseRecycleItemClick) {
        this.baseRecycleItemClick = baseRecycleItemClick;
        toRefresh();
    }

    public void setOnRecycleItemLongClick(BaseRecycleItemLongClick baseRecycleItemLongClick) {
        this.baseRecycleItemLongClick = baseRecycleItemLongClick;
        toRefresh();
    }
    public void setBaseRecycleItemChildrenViewCLick(BaseRecycleItemChildrenViewCLick baseRecycleItemViewCLick) {
        this.baseRecycleItemChildrenViewCLick = baseRecycleItemViewCLick;
        toRefresh();
    }

    private void toRefresh(){
        if(null != baseRecycleAdapter && null != baseRecycleItemClick){
            baseRecycleAdapter.setBaseRecycleItemClick(baseRecycleItemClick);
        }
        if(null != baseRecycleAdapter && null != baseRecycleItemLongClick){
            baseRecycleAdapter.setBaseRecycleItemLongClick(baseRecycleItemLongClick);
        }
        if(null != baseRecycleAdapter && null != baseRecycleItemChildrenViewCLick){
            baseRecycleAdapter.setBaseRecycleItemChildrenViewCLick(baseRecycleItemChildrenViewCLick);
        }
    }

    //移动到指定的位置
    public void moveToAppointPosition( int position ){
        if(getLayoutManager() instanceof LinearLayoutManager){
            if (position != -1) {
                scrollToPosition(position);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager)getLayoutManager();//这里的LinearLayoutManager对象只能是动态获取，不能用全局的。
                mLayoutManager.scrollToPositionWithOffset(position, 0);
            }
        }
    }
}
