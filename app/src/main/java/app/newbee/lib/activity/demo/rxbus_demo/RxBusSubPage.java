package app.newbee.lib.activity.demo.rxbus_demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.newbee.lib.activity.base.BaseFragment;
import butterknife.OnClick;
import com.newbee.lib.R;

public class RxBusSubPage extends BaseFragment {


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return View.inflate(mActivity, R.layout.fragment_rxbus_demo, null);
    }

    @OnClick(R.id.title_back_img)
    void onBackPress() {
        mActivity.onBackPressed();
    }

    @Override
    protected void processLogic() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.demo_rxbus_frag_1, new RxBusDemo_TopFragment())
                .replace(R.id.demo_rxbus_frag_2, new RxBusDemo_Bottom3Fragment())
                .commit();
    }


}