package com.app.dialoglibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.customviewlibrary.recyclerviewlib.BaseInfo;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleAdapter;
import com.app.customviewlibrary.recyclerviewlib.BaseRecycleViewHolder;
import com.app.dialoglibrary.bean.ShareInfo;
import com.gobestsoft.applibrary.R;

import java.util.List;

public class DialogItemAdapter extends BaseRecycleAdapter {

    public DialogItemAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    /**
     * 创建你的视图
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ShareItemViewHolder(backView(R.layout.item_horizontal_layout, viewGroup));
    }

    /**
     * 重写此方法，可以放置空的
     * @return
     */
    @Override
    public void onBindViewHolder(BaseRecycleViewHolder baseRecycleViewHolder, int i) {
        super.onBindViewHolder(baseRecycleViewHolder, i);
    }

    /**
     * 当你的BaseRecycleAdapter只用一种布局的时候，可以不用重写getItemViewType（）方法
     * 多个布局需要重写，根据自己的UI布局去增加不同的viewtype(建议继承BaseInfo 设置viewtyepe)
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position) instanceof BaseInfo){
            BaseInfo baseInfo = (BaseInfo) dataList.get(position);
            return baseInfo.getViewType();
        }
        return 0 ;
    }


    /**
     * 继承BaseRecycleViewHolder，初始化itemview 以及需要显示的信息
     */
    class ShareItemViewHolder extends BaseRecycleViewHolder{
        private ImageView itemIv;
        private TextView itemTv;
        private ShareItemViewHolder(View itemView) {
            super(itemView);
            itemIv = itemView.findViewById(R.id.item_iv);
            itemTv = itemView.findViewById(R.id.item_tv);

        }

        @Override
        public void setItemDataToShow(int position, Object object) {
            if(null != object ){
                if( object instanceof ShareInfo ){
                    ShareInfo shareInfo = (ShareInfo) object ;
                    itemIv.setImageResource(shareInfo.getShareRes());
                    itemTv.setText(shareInfo.getShareHint());
                }
            }
        }
    }
}
