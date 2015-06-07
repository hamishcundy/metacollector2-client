package nz.co.hamishcundy.metacollector2.networking;

import retrofit.RestAdapter;

/**
 * Created by hamish on 7/06/15.
 */
public class CommsHelper {

    private static MCApiInterface mcai;

    public static MCApiInterface getCommsInterface(){
        if(mcai != null) {
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://salty-tundra-4797.herokuapp.com").setLogLevel(RestAdapter.LogLevel.FULL).build();
            mcai = restAdapter.create(MCApiInterface.class);
        }
        return mcai;

    }
}
