package app.newbee.lib.network;

import app.newbee.lib.model.Contributor;
import app.newbee.lib.model.User;
import com.newbee.lib.BuildConfig;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

import java.util.List;

import static java.lang.String.format;

/**
 * Created by cao_ruixiang on 15/8/16.
 */

public interface ApiManagerService {
    ApiManagerService apiManager
            = new RestAdapter
            .Builder()
            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
            .setEndpoint("https://api.github.com")
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Authorization", format("token %s", "aa9ff446490e06789ebe5c908148713d05782571"));
                }
            })
            .build()
            .create(ApiManagerService.class);


    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(@Path("owner") String owner,
                                               @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors")
    List<Contributor> getContributors(@Path("owner") String owner, @Path("repo") String repo);

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{user}")
    Observable<User> user(@Path("user") String user);

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{user}")
    User getUser(@Path("user") String user);
}