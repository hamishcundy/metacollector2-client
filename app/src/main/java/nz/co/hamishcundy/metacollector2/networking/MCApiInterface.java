package nz.co.hamishcundy.metacollector2.networking;

import nz.co.hamishcundy.metacollector2.data.SurveyDetails;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by hamish on 7/06/15.
 */
public interface MCApiInterface {

    @GET("/api/testCall")
    void testCall(@Query("request") String request, Callback<String> callback);


    @GET("/api/GetSurveyDetails")
    void getSurveyDetails(Callback<SurveyDetails> callback);

}
