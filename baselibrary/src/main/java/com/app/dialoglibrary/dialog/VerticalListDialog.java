package com.app.dialoglibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gobestsoft.applibrary.R;
import com.app.dialoglibrary.InfoDialogListener;
import com.app.toolboxlibrary.ListUtils;

import java.util.List;

/**
 * 多个选项的弹窗
 */
public class VerticalListDialog extends BaseDialog {
    private ListView dialogHaveListview;
    private List<String> list ;
    private DialogAdapter dialogAdapter ;
    boolean isShowItemBg = true ;//控制第一个 item bg
    public boolean isShowItemBg() {
        return isShowItemBg;
    }
    public void setShowItemBg(boolean showItemBg) {
        isShowItemBg = showItemBg;
    }


    public VerticalListDialog(Context context , List<String> list) {
        super(context);
        this.list = list ;
    }
    private VerticalListDialog(Context context , Builder builder) {
        super(context);
        this.list = builder.list ;
        this.isShowItemBg = builder.isShowItemBg;
        this.setOnClickDialog(builder.infoDialogListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_have_list_layout);
        initView();
    }

    private void initView() {
        dialogHaveListview = findViewById(R.id.dialog_have_listview);
        dialogAdapter = new DialogAdapter();
        dialogHaveListview.setAdapter(dialogAdapter);
    }

    class DialogAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return ListUtils.backArrayListSize(list);
        }

        @Override
        public Object getItem(int position) {
            return ListUtils.getDataFormPosition(list,position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (null == viewHolder) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_listview_layout, viewGroup, false);

                viewHolder.choose_bt = convertView.findViewById(R.id.choose_bt);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(isShowItemBg()){
                if(position==0){
                    viewHolder.choose_bt.setBackgroundResource(R.drawable.dialog_top_shape);
                }else {
                    viewHolder.choose_bt.setBackgroundResource(R.drawable.dialog_hint_bg);
                }
            }
            viewHolder.choose_bt.setText((String)list.get(position));

            viewHolder.choose_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnClickDialog().onDialogItemClicked(position);
                }
            });

            return  convertView ;
        }
    }

    class ViewHolder {
        TextView choose_bt;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public static class Builder {
        private List<String> list ;
        private InfoDialogListener infoDialogListener ;
        boolean isShowItemBg = true ;//控制第一个 item bg

        public Builder setList(List<String> list) {
            this.list = list;
            return this ;
        }

        public Builder setInfoDialogListener(InfoDialogListener infoDialogListener) {
            this.infoDialogListener = infoDialogListener;
            return this ;
        }

        public Builder setShowItemBg(boolean showItemBg) {
            isShowItemBg = showItemBg;
            return this ;
        }

        public VerticalListDialog build(Context context){
            return new VerticalListDialog(context, this);
        }
    }
}
