package app.newbee.lib;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class App extends Application {

    private static App _instance;
    private RefWatcher _refWatcher;

    public static App get() {
        return _instance;
    }

    public static RefWatcher getRefWatcher() {
        return App.get()._refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = (App) getApplicationContext();
        /**
         * 检测内存泄露的
         */
        _refWatcher = LeakCanary.install(this);
    }
}
