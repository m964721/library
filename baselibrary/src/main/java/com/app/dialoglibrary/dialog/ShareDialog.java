package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gobestsoft.applibrary.R;
import com.app.toolboxlibrary.InfoDialogListener;

/**
 * 分享dialog
 */
public class ShareDialog extends BaseDialog {

    private LinearLayout shareToWxFriendLayout;
    private LinearLayout shareToWxLayout;
    private TextView cancleDialogTv;//取消

    private ShareDialog(Context context , Builder builder){
        super(context);
        this.setOnClickDialog(builder.onClickDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_layout);
        initView();
    }

    private void initView() {

        shareToWxFriendLayout = findViewById(R.id.share_to_wx_friend_layout);
        shareToWxLayout = findViewById(R.id.share_to_wx_layout);
        cancleDialogTv = findViewById(R.id.cancle_dialog_tv);

        cancleDialogTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        shareToWxFriendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getOnClickDialog().onLeftButtonClicked();
            }
        });

        shareToWxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getOnClickDialog().onRightButtonClicked();
            }
        });
    }

    public static class Builder {

        private InfoDialogListener onClickDialog;

        public ShareDialog.Builder setOnClickDialog(InfoDialogListener onClickDialog) {
            this.onClickDialog = onClickDialog;
            return this;
        }

        public ShareDialog build(Context context) {
            return new ShareDialog(context, this);
        }
    }

}
