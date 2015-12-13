package app.newbee.lib.adapter.abs;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import app.newbee.lib.adapter.AdapterItem;
import app.newbee.lib.adapter.util.AdapterItemUtil;
import com.workingchat.newbee.R;

import java.util.List;


/**
 * @author Jack Tony
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private final boolean DEBUG = false;

    private List<T> mDataList;

    private int mViewTypeCount;

    private Object mType;

    private LayoutInflater mInflater;

    private AdapterItemUtil util = new AdapterItemUtil();

    protected CommonAdapter(@NonNull List<T> data) {
        this(data, 1);
    }

    protected CommonAdapter(@NonNull List<T> data, int viewTypeCount) {
        mDataList = data;
        mViewTypeCount = viewTypeCount;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * 可以被复写用于单条刷新等
     */
    public void updateData(List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemViewType(Object)}
     */
    @Override
    @Deprecated
    public int getItemViewType(int position) {
        mType = getItemViewType(mDataList.get(position));
        //Log.d("ddd", "getType = " + util.getIntType(mType));
        // 如果不写这个方法，会让listView更换dataList后无法刷新数据
        return util.getIntType(mType);
    }

    public Object getItemViewType(T t) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Log.d("ddd", "getView");
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        AdapterItem<T> item;
        if (convertView == null) {
            item = getItemView(mType);
            convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
            convertView.setTag(R.id.tag_item, item);
            item.onBindViews(convertView);
            item.onSetViews();
            if (DEBUG) convertView.setBackgroundColor(0xffff0000);
        } else {
            item = (AdapterItem<T>) convertView.getTag(R.id.tag_item);
            if (DEBUG) convertView.setBackgroundColor(0xff00ff00);
        }
        item.onUpdateViews(mDataList.get(position), position);
        return convertView;
    }

    public abstract
    @NonNull AdapterItem<T> getItemView(Object type);

}
