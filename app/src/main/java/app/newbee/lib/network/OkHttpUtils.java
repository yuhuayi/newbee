package app.newbee.lib.network;

import app.newbee.lib.NBConstants;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient自定义工具类
 */
public class OkHttpUtils {

    private static OkHttpClient singleton;

    public static OkHttpClient getInstance(Context context) {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    File cacheDir = new File(context.getCacheDir(), NBConstants.RESPONSE_CACHE);

                    singleton = new OkHttpClient();
                    try {
                        singleton.setCache(new Cache(cacheDir, NBConstants.RESPONSE_CACHE_SIZE));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    singleton.setConnectTimeout(NBConstants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                    singleton.setReadTimeout(NBConstants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                }
            }
        }
        return singleton;
    }
}