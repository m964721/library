package com.app.customviewlibrary.recyclerviewlib;

import java.io.Serializable;

public class BaseInfo implements Serializable {
    private boolean isShowCutLine = true ;//是否显示分割线
    private boolean isFirstLoad = false ;//第一次加载和没有数据显示区分开
    private int viewType = BaseRecycleConfig.defaultImageView ;//设置adapter显示view的type,默认无数据

    public BaseInfo(){

    }
    public BaseInfo(boolean isFirstLoad){
        setFirstLoad(isFirstLoad);
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    public void setFirstLoad(boolean firstLoad) {
        isFirstLoad = firstLoad;
    }

    public boolean isShowCutLine() {
        return isShowCutLine;
    }

    public void setShowCutLine(boolean showCutLine) {
        isShowCutLine = showCutLine;
    }
}
