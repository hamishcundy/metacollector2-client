package nz.co.hamishcundy.metacollector2.networking;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by hamish on 7/06/15.
 */
public interface MCApiInterface {

    @GET("/api/testCall")
    void testCall(@Query("request") String request, Callback<String> callback);

}
