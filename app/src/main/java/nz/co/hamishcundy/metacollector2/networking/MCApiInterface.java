package nz.co.hamishcundy.metacollector2.networking;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by hamish on 7/06/15.
 */
public interface MCApiInterface {

    @GET("/api/testCall")
    void testCall(String request, Callback<String> callback);

}
