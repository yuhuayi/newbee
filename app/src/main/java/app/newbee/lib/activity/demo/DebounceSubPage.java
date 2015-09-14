package app.newbee.lib.activity.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.util.LogUtil;
import butterknife.InjectView;
import com.newbee.lib.R;
import rx.Subscription;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;

/**
 * 防反跳, 多次点击触发事件多次问题
 */
public class DebounceSubPage extends BaseFragment {

    @InjectView(R.id.bt_debounce)
    Button bt_debounce;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.page_debounce_layout, container, false);
    }

//    @OnClick(R.id.bt_debounce)
//    public void oneDbounce() {
//        LogUtil.info(DebounceSubPage.class, "--->  ");
//
//    }

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
//                .delay(1000 ,TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long integer) {
                        LogUtil.info(DebounceSubPage.class, "---> : " + integer);
                    }
                });
        compositeSubscription.add(subscribe);

    }

}
