package com.app.dialoglibrary.wheel;

import android.content.Context;

import com.app.library.R;

import java.util.List;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {
    
    // items
    private List<T> items ;
    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ArrayWheelAdapter(Context context, List<T> items) {
        super(context, R.layout.wheel_adapter_layout,R.id.wheel_layout_tv);
        this.items = items;
    }
    
    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            T item = items.get(index);
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return "" + item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}
