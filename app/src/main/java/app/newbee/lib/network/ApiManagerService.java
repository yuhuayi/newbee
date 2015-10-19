package app.newbee.lib.network;

import app.newbee.lib.model.ResultWrap;
import app.newbee.lib.model.UserBean;
import com.newbee.lib.BuildConfig;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by cao_ruixiang on 15/8/16.
 */

public interface ApiManagerService {
    ApiManagerService apiManager
            = new RestAdapter
            .Builder()
            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
            .setEndpoint("http://123.57.253.189/chaoliuli")
            .build()
            .create(ApiManagerService.class);

    //           ---   user   start---//

    @GET("/index.php?g=api&query=login")
    Observable<ResultWrap<UserBean>> login(@Query("query") String query, @Query("sig") String sig, @Query("data") String data);

    //           ---   user    end ---//



}