package app.newbee.lib.activity.demo.rxbus_demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.rxbus.RxBus;
import butterknife.OnClick;
import com.newbee.lib.R;

public class RxBusDemo_TopFragment extends BaseFragment {

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return View.inflate(mActivity, R.layout.fragment_rxbus_top, null);
    }

    @Override
    protected void processLogic() {

    }


    @OnClick(R.id.btn_demo_rxbus_tap)
    public void onTapButtonClicked() {
        RxBus.get().post("rxbus_tag", "hello RxBus!");
    }
}
