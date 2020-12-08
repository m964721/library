package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.library.R;

/**
 * Created by PPH on 18/3/13.
 */

public class FileDownloadDialog extends BaseDialog {
    private ProgressBar pro_state;
    private TextView tv_state;

    private int process = 0;

    public FileDownloadDialog(Context context) {
        super(context, R.style.my_dialog);
    }

    public FileDownloadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setTip(int percent) {
        pro_state.setProgress(percent);
        tv_state.setText("已完成" + percent + "%");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_updateapk_layout);
        pro_state =  findViewById(R.id.pro_state);
        tv_state =  findViewById(R.id.tv_state);
        pro_state.setProgress(0);
        tv_state.setText("已完成" + 0 + "%");
    }
}
