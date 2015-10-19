package app.newbee.lib.network.engine;

import android.util.Pair;
import app.newbee.lib.App;
import app.newbee.lib.model.ResultWrap;
import app.newbee.lib.model.UserBean;
import app.newbee.lib.network.ApiManagerService;
import app.newbee.lib.util.SingletonUtils;
import com.google.gson.JsonObject;
import rx.Observable;


public class TemplateEngine {

    private static TemplateEngine instance;

    public static TemplateEngine getInstance() {
        if (instance == null) {
            synchronized (SingletonUtils.class) {
                if (instance == null) {
                    instance = new TemplateEngine();
                }
            }
        }
        return instance;
    }

    public Observable<ResultWrap<UserBean>> login(String mobile, String psd) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile", mobile);
        jsonObject.addProperty("password", psd);

        Pair<String, String> pair = App.encryptionRequestparam("login", jsonObject.toString());
        return ApiManagerService.apiManager.login("login", pair.first, pair.second);
    }

}
