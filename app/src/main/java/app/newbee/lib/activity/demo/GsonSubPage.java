package app.newbee.lib.activity.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.newbee.lib.activity.base.BaseFragment;
import com.newbee.lib.R;

public class GsonSubPage extends BaseFragment {

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        View messageLayout = inflater.inflate(R.layout.page_web_view, container, false);
        return messageLayout;
    }


    @Override
    protected void processLogic() {
        testJson();
    }


    private void testJson() {

    }

}
