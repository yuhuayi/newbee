package app.newbee.lib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import app.newbee.lib.netstatus.NetUtils;
import butterknife.ButterKnife;
import com.workingchat.newbee.R;

public class BaseSubActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {

    }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override protected void onNetworkDisConnected() {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.sub_activity_layout;
    }

    @Override public View getLoadingTargetView() {
        return ButterKnife.findById(this, R.id.sub_page_container);
    }

    @Override
    protected void processLogic() {
        showTargetPage();
    }

    public void showTargetPage() {
        try {
            Intent intent = getIntent();
            Bundle targetPageBundle = intent.getBundleExtra("targetPageBundle");
            String targetPage = targetPageBundle.getString("targetPage");
            Class<?> targetPageClazz = Class.forName(targetPage);
            FragmentManager subFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = subFragmentManager.beginTransaction();
            BaseFragment targetPageFragment = (BaseFragment) targetPageClazz.newInstance();

            ft.add(R.id.sub_page_container, targetPageFragment, targetPage);
            if (intent.getExtras() != null) {
                targetPageFragment.setArguments(intent.getExtras());
            }
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
