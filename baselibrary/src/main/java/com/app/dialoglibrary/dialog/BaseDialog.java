package com.app.dialoglibrary.dialog;

import android.app.Dialog;
import android.content.Context;

import com.app.dialoglibrary.InfoDialogListener;
import com.gobestsoft.applibrary.R;


public class BaseDialog extends Dialog {

    protected float wigthSize = 0.7f ;
    protected Context context ;
    InfoDialogListener onClickDialog ;
    public BaseDialog(Context context) {
        this(context, R.style.my_dialog);
        this.context = context ;
        setCanceledOnTouchOutside(false);

    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context ;
        setCanceledOnTouchOutside(false);
    }

    public InfoDialogListener getOnClickDialog() {
        if(null == onClickDialog){
            onClickDialog = new InfoDialogListener() {
                @Override
                public void onLeftButtonClicked() {

                }

                @Override
                public void onRightButtonClicked() {

                }

                @Override
                public void onDialogItemClicked(int otherClickedFlag) {

                }
            };
        }
        return onClickDialog;
    }

    public void setOnClickDialog(InfoDialogListener onClickDialog) {
        this.onClickDialog = onClickDialog;
    }
    public interface onChooseClick {
        void onChooseData(int newValue, String chooseData);
    }
}
