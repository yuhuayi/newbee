package app.newbee.lib.activity.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.model.ResultWrap;
import app.newbee.lib.model.UserBean;
import app.newbee.lib.network.engine.TemplateEngine;
import app.newbee.lib.util.Coder;
import app.newbee.lib.util.LogUtil;
import app.newbee.lib.util.PromptManager;
import app.newbee.lib.util.StringUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newbee.lib.R;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.schedulers.Schedulers;


public class RetrofitSubPage extends BaseFragment {


    @InjectView(R.id.et_login_mobile)
    EditText etLoginMobile;
    @InjectView(R.id.et_login_psd)
    EditText etLoginPsd;
    @InjectView(R.id.bt_login)
    Button btLogin;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.page_sub_retrofit, container, false);
    }


    @Override
    protected void processLogic() {
        Subscription subscribe = ViewObservable.clicks(btLogin)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(onClickEvent -> {
                    if (StringUtils.isEmpty(etLoginMobile.getText().toString())) {
                        PromptManager.showCustomToast(mActivity, "请输入手机号");
                        return;
                    }

                    if (StringUtils.isEmpty(etLoginPsd.getText().toString())) {
                        PromptManager.showCustomToast(mActivity, "请输入密码");
                        return;
                    }

                    testJson(etLoginMobile.getText().toString().trim(), etLoginPsd.getText().toString().trim());
                });


        compositeSubscription.add(subscribe);
    }

    private void testJson(String mobile, String psd) {
        String encryptMD5Psd = "";
        try {
            encryptMD5Psd = Coder.encryptMD5(psd.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Subscription subscribe = TemplateEngine.getInstance().login(mobile, encryptMD5Psd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultWrap<UserBean>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.info("-->  onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.info("-->  onError");
                    }

                    @Override
                    public void onNext(ResultWrap<UserBean> userBeanResultWrap) {
                        LogUtil.info("-->  onNext");
                        LogUtil.info("-->  " + userBeanResultWrap.toString());
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
