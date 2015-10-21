package app.newbee.lib.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import app.newbee.lib.activity.base.BaseActivity;
import app.newbee.lib.activity.base.SubActivity;
import app.newbee.lib.activity.demo.*;
import app.newbee.lib.activity.demo.bufferKnife.BufferKnifePage;
import butterknife.Bind;
import butterknife.OnItemClick;
import com.newbee.lib.R;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    @Bind(R.id.lv_fragments)
    ListView lv_fragments;
    private ArrayList<MainData> mainDatas;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @OnItemClick(R.id.lv_fragments)
    public void gotoSubFragment(int position) {
        gotoSubActivity(SubActivity.class, (String) mainDatas.get(position).getFragmentName(), null);
    }

    @Override
    protected void processLogic() {
        mainDatas = new ArrayList<MainData>();
        mainDatas.add(new MainData(GsonSubPage.class.getName(), "gson序列化反序列化"));
        mainDatas.add(new MainData(BufferKnifePage.class.getName(), "BufferKnife 样例"));
        mainDatas.add(new MainData(DebounceSubPage.class.getName(), "rxjava 运算符"));
        mainDatas.add(new MainData(RetrofitSubPage.class.getName(), "retrofit 样例"));
        mainDatas.add(new MainData(RxBindingPage.class.getName(), "rxbinding 样例"));
        mainDatas.add(new MainData(RxSubPage.class.getName(), "rx 样例"));

        MainAdapter mainAdapter = new MainAdapter(this);
        mainAdapter.resetData(mainDatas);
        lv_fragments.setAdapter(mainAdapter);
    }

    private static class MainAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<MainData> mainDatas;

        public MainAdapter(Context context) {
            this.mContext = context;
        }

        public void resetData(ArrayList<MainData> mainDatas) {
            this.mainDatas = mainDatas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mainDatas.size();
        }

        @Override
        public Object getItem(int i) {
            return mainDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_fragment_text, null);
            }
            TextView tv_fragment_path = (TextView) view.findViewById(R.id.tv_fragment_path);
            tv_fragment_path.setText(mainDatas.get(i).getDescribe());
            return view;
        }
    }

    private static class MainData {
        private String fragmentName;
        private String describe;

        public MainData(String fragmentName, String describe) {
            this.fragmentName = fragmentName;
            this.describe = describe;
        }

        public String getFragmentName() {
            return fragmentName;
        }

        public void setFragmentName(String fragmentName) {
            this.fragmentName = fragmentName;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }

}
