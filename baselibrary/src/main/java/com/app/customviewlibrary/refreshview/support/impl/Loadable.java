
package com.app.customviewlibrary.refreshview.support.impl;


import com.app.customviewlibrary.refreshview.layout.PullRefreshLayout;

public interface Loadable {

    void setPullRefreshLayout(PullRefreshLayout refreshLayout);

    boolean onScroll(float y);

    void onScrollChange(int state);

    boolean onStartFling(float offsetTop);

    void startLoad();

    void stopLoad();
}
