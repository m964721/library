package com.app.customviewlibrary.recyclerviewlib;

public interface BaseRecycleItemViewCLick {
    /**
     * 部分item子view需要点击交互，可能会改变展示的UI数据或者网络请求
     * @param viewClickFlag 点击的view的标志，更具不同的标志执行不同的操作
     * @param position 点击的item position
     */
    public void onItemChildrenViewClickListener(int viewClickFlag, int position);
}
