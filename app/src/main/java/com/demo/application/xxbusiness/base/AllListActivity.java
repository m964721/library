package com.demo.application.xxbusiness.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.app.customviewlibrary.recyclerviewlib.BaseRecycleView;
import com.app.customviewlibrary.refreshview.layout.BaseFooterView;
import com.app.customviewlibrary.refreshview.layout.BaseHeaderView;
import com.app.customviewlibrary.refreshview.layout.PullRefreshLayout;
import com.app.customviewlibrary.refreshview.view.FootView;
import com.app.customviewlibrary.refreshview.view.HeadView;
import com.gobestsoft.applibrary.R;

public abstract class AllListActivity extends AllBaseUIActivity {
    protected PullRefreshLayout listDataPullrefreshlayout;
    private HeadView listDataHeadView;
    private FootView listDataFootView;
    protected BaseRecycleView listDataRecycleView;//显示数据的recycleview
    protected boolean isRefresh = false;
    public static final int REFRESH_DATA = 0;//刷新数据
    public static final int LOAD_DATA = 1;//上拉加载更多
    public static final int STOP_REFRESH = 2;//停止加载
    public static final int STOP_LOAD = 4;//停止加载
    public int page = 1 ;//页码
    public int size = 10 ;//每次请求数据的数量10条

    //配合刷新使用
    @SuppressLint("HandlerLeak")
    private Handler listHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_DATA:
                    startRefreshData();
                    break;
                case LOAD_DATA:
                    startLoadData();
                    break;
                case STOP_REFRESH:
                    if (null != listDataHeadView) listDataHeadView.stopRefresh();
                    break;
                case STOP_LOAD:
                    if (null != listDataFootView) listDataFootView.stopLoad();
                    break;
            }
        }
    };

    @Override
    protected void init() {
        initRefreshView();
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.act_list_layout;
    }

    //初始化
    protected void initRefreshView() {
        listDataPullrefreshlayout = findViewById(R.id.list_data_pullrefreshlayout);
        listDataHeadView = findViewById(R.id.list_data_headView);
        listDataFootView = findViewById(R.id.list_data_footView);
        listDataRecycleView = findViewById(R.id.list_data_recycleView);
        listDataHeadView.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
            @Override
            public void onRefresh(BaseHeaderView baseHeaderView) {
                isRefresh = true;
                listHandler.sendEmptyMessage(REFRESH_DATA);
            }
        });
        listDataFootView.setOnLoadListener(new BaseFooterView.OnLoadListener() {
            @Override
            public void onLoad(BaseFooterView baseFooterView) {
                isRefresh = false;
                listHandler.sendEmptyMessage(LOAD_DATA);
            }
        });
        setNeedLoadAndRefresh(isNeedLoadAndRefresh());
    }
    //设置是否需要刷新
    private void setNeedLoadAndRefresh(boolean isNeed){
        listDataPullrefreshlayout.setHasFooter(isNeed);
        listDataPullrefreshlayout.setHasFooter(isNeed);
    }

    //下拉刷新数据
    public abstract void startRefreshData() ;

    //上拉加载更多
    public abstract void startLoadData() ;

    public abstract boolean isNeedLoadAndRefresh();

    //停止刷新数据
    public void stopRefreshData() {
        listHandler.sendEmptyMessage(STOP_REFRESH);
    }

    //停止上拉加载更多
    public void stopLoadData() {
        listHandler.sendEmptyMessage(STOP_LOAD);
    }

    @Override
    public void doAfterRequestSuccess(String application, String resultData) {
        super.doAfterRequestSuccess(application, resultData);
        stopLoadData();
        stopRefreshData();
    }

    @Override
    public void doAfterRequestFail(String application, Object resultCode, String data) {
        super.doAfterRequestFail(application, resultCode, data);
        stopLoadData();
        stopRefreshData();
    }

}
