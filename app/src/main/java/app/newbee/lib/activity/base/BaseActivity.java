package app.newbee.lib.activity.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;
import com.newbee.lib.R;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    /**
     * 初始activity方法
     */
    private void initView() {
        loadViewLayout();
        ButterKnife.inject(this);
        processLogic();
    }

    /**
     * 加载页面layout
     */
    protected abstract void loadViewLayout();


    /**
     * 业务逻辑处理，主要与后端交互
     */
    protected abstract void processLogic();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    /**
     * 添加动画效果
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 收起软键盘
     */
    public void colseSoftInputMethod(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 收起软键盘
     */
    public void showSoftInput(View v) {
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

    public void gotoSubActivity(Class targetActivity, String targetPage, Bundle bundle) {
        Intent intent = new Intent(this, targetActivity);
        Bundle targetPageBundle = new Bundle();
        targetPageBundle.putString("targetPage", targetPage);
        intent.putExtra("targetPageBundle", targetPageBundle);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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

    /**
     * 切换Fragment
     *
     * @param _bundle 界面间传递数据 切换目标Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void changeSubFragment(BaseFragment currentFragment, int content_id, String targetFragmentString, Bundle _bundle) {
        changeSubFragmentWithAnim(currentFragment, content_id, targetFragmentString, "", _bundle, true);
    }

    /**
     * 当前页面和跳转页面是一个时候的处理
     * 切换Fragment
     *
     * @param _bundle 界面间传递数据 切换目标Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void changeSubFragment(BaseFragment currentFragment, int content_id, String targetFragmentString, String targetFragmentId, Bundle _bundle) {
        changeSubFragmentWithAnim(currentFragment, content_id, targetFragmentString, targetFragmentId, _bundle, true);
    }

    /**
     * @param currentFragment      当前page
     * @param content_id           包裹page的id
     * @param targetFragmentString 切换到该page的class全路径
     * @param _bundle              传递的bundle
     * @param isShowAnimation      是否展示动画
     */
    private void changeSubFragmentWithAnim(BaseFragment currentFragment, int content_id, String targetFragmentString, String targetFragmentId, Bundle _bundle, boolean isShowAnimation) {
        Bundle bundle = new Bundle();
        if (_bundle != null)
            bundle.putAll(_bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isShowAnimation)
            ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_right_in, R.anim.push_right_out); // 左右推进推出

        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        String key = targetFragmentString + "_" + targetFragmentId;
        BaseFragment targetFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(key);
        if (targetFragment == null) {
            try {
                Class<?> targetPageClazz = Class.forName(targetFragmentString);
                targetFragment = (BaseFragment) targetPageClazz.newInstance();
                ft.add(content_id, targetFragment, key);
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
     * 切换Fragment
     *
     * @param _bundle 界面间传递数据 切换目标Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void changeSubFragment(BaseFragment currentFragment, int content_id, String targetFragmentString,
                                  Bundle _bundle, boolean isShowAnimation) {
        changeSubFragmentWithAnim(currentFragment, content_id, targetFragmentString, "", _bundle, isShowAnimation);
    }
}
