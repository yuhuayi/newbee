package app.newbee.lib.activity.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.util.LogUtil;
import butterknife.Bind;
import com.newbee.lib.R;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class RxSubPage extends BaseFragment {


    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.butto31)
    Button butto31;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.page_sub_rx, container, false);
    }


    @Override
    protected void processLogic() {
//        Observable.create(new Observable.OnSubscribe<Object>() {
//            @Override
//            public void call(Subscriber<? super Object> subscriber) {
//                subscriber.onNext(subscriber);
//                subscriber.onCompleted();
//            }
//        }).doOnSubscribe(new Action0() {
//            @Override
//            public void call() {
//                LogUtil.info(RxSubPage.class, "doOnSubscribe");
//            }
//        }).doOnCompleted(new Action0() {
//            @Override
//            public void call() {
//                LogUtil.info(RxSubPage.class, "doOnCompleted");
//            }
//        }).doOnNext(new Action1<Object>() {
//            @Override
//            public void call(Object o) {
//                LogUtil.info(RxSubPage.class, "doOnNext");
//            }
//        }).subscribe(new Observer<Object>() {
//            @Override
//            public void onCompleted() {
//                LogUtil.info(RxSubPage.class, "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtil.info(RxSubPage.class, "onError");
//            }
//
//            @Override
//            public void onNext(Object o) {
//                LogUtil.info(RxSubPage.class, "onNext");
//            }
//        });
// --------------------------------------//
        Observable.empty().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                LogUtil.info(RxSubPage.class, "doOnSubscribe");
            }
        }).subscribe();

    }

    @Override
    protected void click() {


    }

}
