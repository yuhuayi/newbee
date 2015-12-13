package app.newbee.lib.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import app.newbee.lib.loading.VaryViewHelperController;
import app.newbee.lib.netstatus.NetChangeObserver;
import app.newbee.lib.netstatus.NetStateReceiver;
import app.newbee.lib.netstatus.NetUtils;
import app.newbee.lib.util.SmartBarUtils;
import butterknife.ButterKnife;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.workingchat.newbee.R;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseFragmentActivity extends FragmentActivity {
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;
    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;
    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;
    private NetChangeObserver mNetChangeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    protected abstract boolean isApplyKitKatTranslucency();

    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }

    /**
     * 初始activity方法
     */

    private void initView() {
        BaseAppManager.getInstance().addActivity(this);
        TAG_LOG = this.getClass().getSimpleName();

        SmartBarUtils.hide(getWindow().getDecorView());
        setTranslucentStatus(isApplyStatusBarTranslucency());

        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        // init NetChangeObserver start
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);
        // init NetChangeObserver end

        // view init start
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        ButterKnife.bind(this);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        // view init end
        processLogic();
    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * is applyStatusBarTranslucency
     *
     * @return
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * get bundle data
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * network connected
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * 业务逻辑处理，主要与后端交互
     */
    protected abstract void processLogic();

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 收起软键盘
     */
    public void showSoftInput(final View v) {
        if (v != null) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInputFromInputMethod(v.getWindowToken(), InputMethodManager.RESULT_SHOWN);
                    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }, 200);
        }
    }

    public void gotoSubActivity(Class targetActivity, Bundle bundle) {
        gotoSubActivity(targetActivity, null, bundle, false);
    }

    public void gotoSubActivity(Class targetActivity, String targetPage, Bundle bundle, boolean finishActivity) {
        gotoSubActivity(targetActivity, targetPage, bundle);
        if (finishActivity) {
            finish();
        }
    }

    public void gotoSubActivity(Class targetActivity, String targetPage, Bundle bundle) {
        Intent intent = new Intent(this, targetActivity);
        Bundle targetPageBundle = new Bundle();
        targetPageBundle.putString("targetPage", targetPage);
        intent.putExtra("targetPageBundle", targetPageBundle);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 切换Fragment
     *
     * @param _bundle 界面间传递数据 切换目标Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void changeSubFragment(BaseFragment currentFragment, Class<? extends BaseFragment> targetPageClazz, Bundle _bundle) {
        changeSubFragmentWithAnim(currentFragment, targetPageClazz, "", _bundle, true);
    }

    /**
     * @param currentFragment 当前page
     * @param targetPageClazz 切换到该page的class全路径
     * @param _bundle         传递的bundle
     * @param isShowAnimation 是否展示动画
     */
    private void changeSubFragmentWithAnim(BaseFragment currentFragment, Class<? extends BaseFragment> targetPageClazz, String targetFragmentId, Bundle _bundle, boolean isShowAnimation) {
        Bundle bundle = new Bundle();
        if (_bundle != null)
            bundle.putAll(_bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isShowAnimation)
//            ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_right_in, R.anim.push_right_out); // 左右推进推出

        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        String key = targetPageClazz.getCanonicalName() + "_" + targetFragmentId;
        BaseFragment targetFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(key);
        if (targetFragment == null) {
            try {
                targetFragment = targetPageClazz.newInstance();
                ft.add(R.id.sub_page_container, targetFragment, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ft.show(targetFragment);
        }
        ft.addToBackStack(null);
        if (currentFragment != null) {
            bundle.putParcelable("currentFragment", currentFragment);
        }
        if (targetFragment != null) {
            if (targetFragment.isAdded()) {
                Bundle arguments = targetFragment.getArguments();
                if (arguments != null) {
                    arguments.clear();
                    arguments.putAll(bundle);
                }
            } else {
                if (!targetFragment.isVisible())
                    targetFragment.setArguments(bundle);
            }
        }

        ft.commitAllowingStateLoss();

    }

    /**
     * 当前页面和跳转页面是一个时候的处理
     * 切换Fragment
     *
     * @param _bundle 界面间传递数据 切换目标Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void changeSubFragment(BaseFragment currentFragment, Class<? extends BaseFragment> targetPageClazz, String targetFragmentId, Bundle _bundle) {
        changeSubFragmentWithAnim(currentFragment, targetPageClazz, targetFragmentId, _bundle, true);
    }

    /**
     * 切换Fragment
     *
     * @param _bundle 界面间传递数据 切换目标Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void changeSubFragment(BaseFragment currentFragment, Class<? extends BaseFragment> targetPageClazz,
                                  Bundle _bundle, boolean isShowAnimation) {
        changeSubFragmentWithAnim(currentFragment, targetPageClazz, "", _bundle, isShowAnimation);
    }

    /**
     * show toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        closeSoftInputMethod(getCurrentFocus());
        //防止遮盖虚拟按键
        if (null != msg && !TextUtils.isEmpty(msg)) {
            Snackbar sBar = Snackbar.make(getLoadingTargetView(), msg, Snackbar.LENGTH_SHORT);
//            Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout) sBar.getView();
//            ve.setBackgroundColor(0xfff44336);
//            ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
//            ViewGroup.LayoutParams vl = ve.getLayoutParams();
//            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(vl.width, vl.height);
//            ll.gravity = Gravity.TOP;
//            ve.setLayoutParams(ll);
//            ve.setAnimation(new AlphaAnimation(0.3f, 0.8f));
            sBar.show();
        }
    }

    /**
     * 收起软键盘
     */
    public void closeSoftInputMethod(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

}
