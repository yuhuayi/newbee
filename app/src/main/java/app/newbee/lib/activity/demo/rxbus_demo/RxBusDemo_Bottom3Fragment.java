package app.newbee.lib.activity.demo.rxbus_demo;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.rxbus.RxBus;
import app.newbee.lib.util.LogUtil;
import butterknife.Bind;
import com.newbee.lib.R;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class RxBusDemo_Bottom3Fragment extends BaseFragment {

    @Bind(R.id.demo_rxbus_tap_txt)
    TextView _tapEventTxtShow;
    @Bind(R.id.demo_rxbus_tap_count)
    TextView _tapEventCountShow;


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return View.inflate(mActivity, R.layout.fragment_rxbus_bottom, null);
    }

    @Override
    protected void processLogic() {
        Observable<String> register = RxBus.get().register("rxbus_tag", String.class);

        Subscription subscribe = register.subscribe(new Action1<Object>() {
            @Override
            public void call(Object event) {
                LogUtil.info(this.getClass().getCanonicalName(), event.toString());
                _showTapText();
            }
        });

        compositeSubscription.add(subscribe);
    }

    // -----------------------------------------------------------------------------------
    // Helper to show the text via an animation

    private void _showTapText() {
        _tapEventTxtShow.setVisibility(View.VISIBLE);
        _tapEventTxtShow.setAlpha(1f);
        ViewCompat.animate(_tapEventTxtShow).alphaBy(-1f).setDuration(400);
    }

    private void _showTapCount(int size) {
        _tapEventCountShow.setText(String.valueOf(size));
        _tapEventCountShow.setVisibility(View.VISIBLE);
        _tapEventCountShow.setScaleX(1f);
        _tapEventCountShow.setScaleY(1f);
        ViewCompat.animate(_tapEventCountShow)
                .scaleXBy(-1f)
                .scaleYBy(-1f)
                .setDuration(800)
                .setStartDelay(100);
    }
}
