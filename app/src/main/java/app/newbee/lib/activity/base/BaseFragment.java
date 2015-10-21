package app.newbee.lib.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.newbee.lib.App;
import app.newbee.lib.listener.OnFragmentResultListener;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.newbee.lib.R;
import com.squareup.leakcanary.RefWatcher;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment implements OnFragmentResultListener, Parcelable {
    public static BaseActivity mActivity;
    protected BaseFragment backFragment;
    protected CompositeSubscription compositeSubscription;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @OnClick(R.id.title_back_img)
    void onBackPress() {
        mActivity.onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return init(inflater, container);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        if (arguments != null) {
            backFragment = arguments.getParcelable("currentFragment");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) getActivity();
    }

    /**
     * 初始activity方法
     */
    private View init(LayoutInflater inflater, ViewGroup container) {
        View loadViewLayout = loadViewLayout(inflater, container);
        /**
         *    将loadViewLayout 加入注解库
         */
        ButterKnife.bind(this, loadViewLayout);
        /**
         *  实例化 CompositeSubscription
         */
        if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
            compositeSubscription = new CompositeSubscription();
        }
        click();
        processLogic();
        return loadViewLayout;
    }

    /**
     * 加载页面layout
     */
    protected abstract View loadViewLayout(LayoutInflater inflater, ViewGroup container);


    /**
     * 业务逻辑处理，主要与后端交互
     */
    protected abstract void processLogic();

    /**
     * 业务逻辑处理，主要与后端交互
     */
    protected void click() {
    }

    /**
     * 模拟后退键
     */
    public void popBackStack() {
        if (mActivity != null && mActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (mActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
                mActivity.getSupportFragmentManager().popBackStackImmediate();
            }
        } else {
            if (mActivity != null) {
                mActivity.finish();
                mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        }
    }

    /**
     * 模拟后退键 , 并携带参数
     *
     * @param bundle page回调的参数Pr
     */
    public void popBackStack(Bundle bundle) {
        if (mActivity != null && mActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (backFragment != null && bundle != null) {
                backFragment.onFragmentResult(this, bundle);
            }
            if (mActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
                mActivity.getSupportFragmentManager().popBackStackImmediate();
            }
        } else {
            if (mActivity != null) {
                mActivity.finish();
                mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * 检测是否发生内存泄露
         */
        RefWatcher refWatcher = App.getRefWatcher();
        refWatcher.watch(this);
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }

    }

    public void onFragmentResult(Fragment fragment, Bundle bundle) {
    }
}
