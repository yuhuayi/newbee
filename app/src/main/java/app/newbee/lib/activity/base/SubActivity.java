package app.newbee.lib.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.newbee.lib.R;

public class SubActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SubActivity";
    public FragmentManager subFragmentManager;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.sub_activity_layout);
    }

    @Override
    protected void processLogic() {
        subFragmentManager = getSupportFragmentManager();
        showTargetPage();
    }

    @Override
    public void onClick(View view) {

    }

    public void showTargetPage() {
        try {
            Intent intent = getIntent();
            Bundle targetPageBundle = intent.getBundleExtra("targetPageBundle");
            String targetPage = targetPageBundle.getString("targetPage");
            Class<?> targetPageClazz = Class.forName(targetPage);
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
