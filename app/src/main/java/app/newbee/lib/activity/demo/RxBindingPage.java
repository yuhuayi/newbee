package app.newbee.lib.activity.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.util.LogUtil;
import butterknife.Bind;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.newbee.lib.R;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;


public class RxBindingPage extends BaseFragment {


    @Bind(R.id.bt_throttleFirst)
    Button btThrottleFirst;
    @Bind(R.id.et_login_mobile)
    EditText etLoginMobile;
    @Bind(R.id.et_login_psd)
    EditText etLoginPsd;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.page_sub_rxbinding, container, false);
    }


    @Override
    protected void processLogic() {

    }

    @Override
    protected void click() {
        //-------  防止多次点击  start  ←  ↑   →  ↓  ↖  ↙  ↗  ↘  ↕  ←  ↑   →  ↓  ↖  ↙  ↗  ↘  --------------//
        RxView.clickEvents(btThrottleFirst)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .map(new Func1<ViewClickEvent, Long>() {
                    @Override
                    public Long call(ViewClickEvent viewClickEvent) {
                        return System.currentTimeMillis();
                    }
                })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        LogUtil.info(RxBindingPage.class, "clickEvents  ---> : " + aLong);
                    }
                });


        RxView.clicks(btThrottleFirst)
                .throttleFirst(500, TimeUnit.MILLISECONDS) // 防止多次点击
                .map(new Func1<Object, Long>() {
                    @Override
                    public Long call(Object o) {
                        return System.currentTimeMillis();
                    }
                })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        LogUtil.info(RxBindingPage.class, "clicks ---> : " + aLong);
                    }
                });
        //-------  防止多次点击  end  ---------------//



        //-------  输入控制  start  ---------------//
        WidgetObservable.text(etLoginMobile)
                .map(new Func1<OnTextChangeEvent, String>() {
                    @Override
                    public String call(OnTextChangeEvent onTextChangeEvent) {
                        String s = etLoginMobile.getText().toString();
                        return s;
                    }
                })
//                .debounce(1000 , TimeUnit.MILLISECONDS) // 效果, 发射数据要等到1秒后 , 使用场景待考虑
//                .doOnCompleted(new Action0() {  // 加了此opt subscribe() 没有执行
//                    @Override
//                    public void call() {
//                        LogUtil.info(RxBindingPage.class, "doOnCompleted  " );
//                    }
//                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        LogUtil.info(RxBindingPage.class, "doOnSubscribe  ");
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.info(RxBindingPage.class, "doOnNext" + s);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.info(RxBindingPage.class, "input --->" + s);
                    }
                });

      /*
           打印结果
        10-19 17:40:20.489 8367-8367/com.newbee.lib I/RxBindingPage: doOnSubscribe
        10-19 17:40:22.839 8367-8367/com.newbee.lib I/RxBindingPage: doOnNextt
        10-19 17:40:22.839 8367-8367/com.newbee.lib I/RxBindingPage: input --->t
        10-19 17:40:24.179 8367-8367/com.newbee.lib I/RxBindingPage: doOnNextty
        10-19 17:40:24.179 8367-8367/com.newbee.lib I/RxBindingPage: input --->ty
        10-19 17:40:25.769 8367-8367/com.newbee.lib I/RxBindingPage: doOnNexttyh
        10-19 17:40:25.769 8367-8367/com.newbee.lib I/RxBindingPage: input --->tyh
        10-19 17:40:31.509 8367-8367/com.newbee.lib I/RxBindingPage: doOnNexttyhu
        10-19 17:40:31.509 8367-8367/com.newbee.lib I/RxBindingPage: input --->tyhu
        10-19 17:40:31.679 8367-8367/com.newbee.lib I/RxBindingPage: doOnNexttyhuu
        10-19 17:40:31.679 8367-8367/com.newbee.lib I/RxBindingPage: input --->tyhuu
        10-19 17:40:32.439 8367-8367/com.newbee.lib I/RxBindingPage: doOnNexttyhuuu
        10-19 17:40:32.439 8367-8367/com.newbee.lib I/RxBindingPage: input --->tyhuuu
        10-19 17:40:32.609 8367-8367/com.newbee.lib I/RxBindingPage: doOnNexttyhuuuu
        10-19 17:40:32.609 8367-8367/com.newbee.lib I/RxBindingPage: input --->tyhuuuu*/

        // -------------- input  end -------------//


    }

}
