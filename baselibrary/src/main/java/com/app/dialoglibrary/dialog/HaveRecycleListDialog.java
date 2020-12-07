package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.customviewlibrary.recyclerviewlib.BaseRecycleItemClick;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleView;
import com.gobestsoft.applibrary.R;
import com.app.dialoglibrary.adapter.DialogItemAdapter;
import com.app.dialoglibrary.bean.ShareInfo;
import com.app.toolboxlibrary.InfoDialogListener;
import com.app.toolboxlibrary.LogUtil;

import java.util.List;

//功能块
public class HaveRecycleListDialog extends BaseDialog {

    private BaseRecycleView baserecycleview ;
    private DialogItemAdapter dialogItemAdapter ;
    private  int spanCount = 1 ;//显示几个（表格布局）
    private int showLayout ;//显示格式
    private List<ShareInfo> shareInfoList ;

    private HaveRecycleListDialog(Context context , Builder builder ) {
        super(context);
        this.shareInfoList = builder.shareInfoList ;
        this.showLayout = builder.showLayout ;
        this.spanCount = builder.spanCount ;
        this.onClickDialog = builder.infoDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recycle_layout);
        initView();
    }

    //初始化布局
    private void initView(){
        baserecycleview = findViewById(R.id.baserecycleview);
        if(1 == spanCount){
            baserecycleview.setLayoutManager(new LinearLayoutManager(context));
        } else if(1 < spanCount){
            baserecycleview.setLayoutManager(new GridLayoutManager(context,spanCount));
        }
        dialogItemAdapter = new DialogItemAdapter(context,shareInfoList) ;
        baserecycleview.setAdapter(dialogItemAdapter);
        baserecycleview.setOnRecycleItemClick(new BaseRecycleItemClick() {
            @Override
            public void OnItemClickListener(View view, int position) {
                LogUtil.showLog("initView","setOnRecycleItemClick : position() " +position);
                getOnClickDialog().onDialogItemClicked(position);
                dismiss();
            }
        });
    }

    public static class Builder{

        private  int spanCount = 1 ;//显示几个（表格布局）
        private int showLayout = 0 ;//显示格式
        private List<ShareInfo> shareInfoList ;
        private InfoDialogListener infoDialogListener ;

        public Builder setSpanCount(int spanCount) {
            this.spanCount = spanCount;
            return this ;
        }

        public Builder setShowLayout(int showLayout) {
            this.showLayout = showLayout;
            return this ;
        }

        public Builder setShareInfoList(List<ShareInfo> shareInfoList) {
            this.shareInfoList = shareInfoList;
            return this ;
        }

        public Builder setInfoDialogListener(InfoDialogListener infoDialogListener) {
            this.infoDialogListener = infoDialogListener;
            return this ;
        }

        public HaveRecycleListDialog build(Context context) {
            return new HaveRecycleListDialog(context, this);
        }
    }
}
