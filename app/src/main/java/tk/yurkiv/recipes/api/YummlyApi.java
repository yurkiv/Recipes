package tk.yurkiv.recipes.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by yurkiv on 17.08.2015.
 */
public class YummlyApi {
    private static final String API_URL = "http://api.yummly.com/v1/api";
//    private static final String APP_ID = "07adeeb2"; //my
//    private static final String APP_KEY = "1d2751954d340b3d2e7b2d3515685c12";
    private static final String APP_ID = "3830e3d4";
    private static final String APP_KEY = "da74a7e8a27f10e29bc89e1841b2af41";

    private static final RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("X-Yummly-App-ID", APP_ID);
            request.addHeader("X-Yummly-App-Key", APP_KEY);
        }
    };

    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .setRequestInterceptor(requestInterceptor)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();

    private static final YummlyService SERVICE = REST_ADAPTER.create(YummlyService.class);

    public static YummlyService getService(){
        return SERVICE;
    }

}
