package app.newbee.lib.activity;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import app.newbee.lib.activity.base.BaseActivity;
import app.newbee.lib.activity.base.SubActivity;
import app.newbee.lib.activity.demo.DebounceSubPage;
import app.newbee.lib.activity.demo.GsonSubPage;
import app.newbee.lib.activity.demo.RetrofitSubPage;
import app.newbee.lib.activity.demo.RxBindingPage;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.newbee.lib.R;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.lv_fragments)
    ListView lv_fragments;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @OnItemClick(R.id.lv_fragments)
    public void gotoSubFragment(int position) {
        gotoSubActivity(SubActivity.class, (String) arrayAdapter.getItem(position), null);
    }

    @Override
    protected void processLogic() {
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_fragment_text, R.id.tv_fragment_path);
        arrayAdapter.add(GsonSubPage.class.getName());
        arrayAdapter.add(DebounceSubPage.class.getName());
        arrayAdapter.add(RetrofitSubPage.class.getName());
        arrayAdapter.add(RxBindingPage.class.getName());
        lv_fragments.setAdapter(arrayAdapter);
    }

}
