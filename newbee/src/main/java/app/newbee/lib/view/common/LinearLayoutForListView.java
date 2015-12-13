package app.newbee.lib.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


public class LinearLayoutForListView extends LinearLayout {

    private BaseAdapter adapter;

    public LinearLayoutForListView(Context context) {
        super(context);
    }

    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        bindLinearLayout();
    }


    /**
     * 绑定布局
     */
    public void bindLinearLayout() {
        int count = adapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);
            addView(v, i);
        }
    }

    /**
     * 添加
     */
    public void addLayout() {
        View v = adapter.getView(adapter.getCount() - 1, null, null);
        addView(v, adapter.getCount() - 1);
    }
}
