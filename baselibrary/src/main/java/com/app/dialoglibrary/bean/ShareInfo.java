package com.app.dialoglibrary.bean;

import com.app.customviewlibrary.recyclerviewlib.BaseInfo;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleConfig;

public class ShareInfo extends BaseInfo {

    int shareRes ;
    String shareHint ;

    public ShareInfo(int shareRes, String shareHint) {
        this.shareRes = shareRes;
        this.shareHint = shareHint;
    }

    public int getShareRes() {
        return shareRes;
    }

    public void setShareRes(int shareRes) {
        this.shareRes = shareRes;
    }

    public String getShareHint() {
        return shareHint;
    }

    public void setShareHint(String shareHint) {
        this.shareHint = shareHint;
    }

    @Override
    public int getViewType() {
        return BaseRecycleConfig.position0;
    }
}
