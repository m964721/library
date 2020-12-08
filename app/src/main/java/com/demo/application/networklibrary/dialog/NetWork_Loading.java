package com.demo.application.networklibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gobestsoft.applibrary.R;


/**
 * Created by PPH on 17/3/6.
 */

public class NetWork_Loading extends Dialog{
    private ProgressBar loadPro;
    private LinearLayout haveLoadLayout;
    private ImageView haveLoadIv;
    private TextView haveLoadTv;
    private Context context ;
    public NetWork_Loading(Context context) {
        super(context , R.style.network_dialog);
        this.context = context ;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_dialog_layout);
        initView();
    }

    private void initView() {

        loadPro = findViewById(R.id.load_pro);
        haveLoadLayout = findViewById(R.id.have_load_layout);
        haveLoadIv =  findViewById(R.id.have_load_iv);
        haveLoadTv =  findViewById(R.id.have_load_tv);

    }

    public void setShowHintTv(String hintData){
        haveLoadTv.setText(hintData);
    }

}
