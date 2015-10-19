package app.newbee.lib;

import android.app.Application;
import android.util.Pair;
import app.newbee.lib.util.Coder;
import app.newbee.lib.util.StringUtils;
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


    public static Pair<String, String> encryptionRequestparam(String query, String data) {
        String sig = "chaoliuli@" + query;
        if (!StringUtils.isEmpty(data)) {
            sig = sig + data;
        }
        try {
            String s = Coder.encryptMD5(sig.getBytes());
            sig = s.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String encryptBASE64_data = "";
        try {
            encryptBASE64_data = Coder.encryptBASE64(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pair<String, String> pair = new Pair<>(sig, encryptBASE64_data);
        return pair;
    }
}
