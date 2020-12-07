package com.app.customviewlibrary.recyclerviewlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.toolboxlibrary.ListUtils;

import java.util.List;

/**
 * 外部继承此类重写onCreateViewHolder，创建view布局，显示item
 * 外部调用传入的bean对象,集成BaseInfo
 *
 * @param <E> 外部继承BaseRecycleViewHolder
 */
public abstract class BaseRecycleAdapter< E extends BaseRecycleViewHolder>
        extends RecyclerView.Adapter<E>{
    BaseRecycleItemClick baseRecycleItemClick;
    BaseRecycleItemLongClick baseRecycleItemLongClick;
    BaseRecycleItemViewCLick baseRecycleItemViewCLick;
    BaseRecycleViewHolder baseRecycleViewHolder;
    protected LayoutInflater layoutInflater;
    protected Context context;
    protected List<Object> dataList;
    public BaseRecycleAdapter(Context context, List<Object> dataList) {
        this.context = context;
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);

    }

    public List<Object> getDataList() {
        return dataList;
    }

    /**
     * 外部数据传递
     *
     * @param refreshAll  全部刷新还是加载更多
     * @param newDataList 新的数据
     */
    public void setData(boolean refreshAll, List newDataList) {
        if (refreshAll) {
            if (0 < ListUtils.backArrayListSize(newDataList)) {
                this.dataList = newDataList;
                notifyDataSetChanged();
            }
        } else {
            if (0 < ListUtils.backArrayListSize(newDataList)) {
                int size = dataList.size();
                this.dataList.addAll(newDataList);
                notifyItemRangeChanged(size, newDataList.size());
            }
        }
    }

    @Override
    public abstract E onCreateViewHolder(ViewGroup viewGroup, int viewType);


    /**
     * itemView 添加点击 长按 以及子view 的点击事件
     * @param baseRecycleViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(BaseRecycleViewHolder baseRecycleViewHolder, int i) {
        this.baseRecycleViewHolder = baseRecycleViewHolder;
        if (null != dataList && i < dataList.size()) {
            this.baseRecycleViewHolder.setItemDataToShow(i, dataList.get(i));
            this.baseRecycleViewHolder.setBaseRecycleItemClick(baseRecycleItemClick,getItemViewType(i));
            this.baseRecycleViewHolder.setBaseRecycleItemLongClick(baseRecycleItemLongClick, getItemViewType(i));
        }
    }

    @Override
    public int getItemCount() {
        if (null != dataList && dataList.size() > 0) {
            return dataList.size();
        }
        return 0;
    }

    public void setBaseRecycleItemClick(BaseRecycleItemClick baseRecycleItemClick) {
        this.baseRecycleItemClick = baseRecycleItemClick;
    }

    public void setBaseRecycleItemLongClick(BaseRecycleItemLongClick baseRecycleItemLongClick) {
        this.baseRecycleItemLongClick = baseRecycleItemLongClick;
    }

    public void setBaseRecycleItemViewCLick(BaseRecycleItemViewCLick baseRecycleItemViewCLick) {
        this.baseRecycleItemViewCLick = baseRecycleItemViewCLick;
    }

    public BaseRecycleItemClick getBaseRecycleItemClick() {
        if(null == baseRecycleItemClick){
            baseRecycleItemClick = new BaseRecycleItemClick() {
                @Override
                public void OnItemClickListener(View view, int position) {

                }
            };
        }
        return baseRecycleItemClick;
    }

    public BaseRecycleItemLongClick getBaseRecycleItemLongClick() {
        if(null == baseRecycleItemLongClick){
            baseRecycleItemLongClick = new BaseRecycleItemLongClick() {
                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            };
        }
        return baseRecycleItemLongClick;
    }

    public BaseRecycleItemViewCLick getBaseRecycleItemViewCLick() {
        if (null == baseRecycleItemViewCLick) {
            baseRecycleItemViewCLick = new BaseRecycleItemViewCLick() {
                @Override
                public void onItemChildrenViewClickListener(int viewClickFlag, int position) {

                }
            };
        }
        return baseRecycleItemViewCLick;
    }

    //初始化view布局
    protected View backView(int layoutRes,ViewGroup viewGroup){
        return layoutInflater.inflate(layoutRes, viewGroup, false);
    }

}
