
package com.app.customviewlibrary.refreshview.support.impl;


import com.app.customviewlibrary.refreshview.layout.PullRefreshLayout;

public interface Refreshable {

    void setPullRefreshLayout(PullRefreshLayout refreshLayout);

    boolean onScroll(float y);

    void onScrollChange(int state);

    boolean onStartFling(float offsetTop);

    void startRefresh();

    void stopRefresh();
}
