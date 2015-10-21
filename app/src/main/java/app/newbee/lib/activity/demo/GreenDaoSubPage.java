package app.newbee.lib.activity.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.util.LogUtil;
import butterknife.Bind;
import com.newbee.lib.R;
import rx.Subscription;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;

/**
 * https://github.com/wangjiegulu/RxAndroidEventsSample
 */
public class GreenDaoSubPage extends BaseFragment {

    @Bind(R.id.bt_debounce)
    Button bt_debounce;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.page_debounce_layout, container, false);
    }

    @Override
    protected void processLogic() {
        Subscription subscribe = ViewObservable.clicks(bt_debounce)
                .map(new Func1<OnClickEvent, Long>() {
                    @Override
                    public Long call(OnClickEvent onClickEvent) {
                        return System.currentTimeMillis();
                    }
                })
                .debounce(400, TimeUnit.MILLISECONDS)
//              .delay(1000 ,TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long integer) {
                        LogUtil.info(GreenDaoSubPage.class, "---> : " + integer);
                    }
                });
        compositeSubscription.add(subscribe);

    }


}
