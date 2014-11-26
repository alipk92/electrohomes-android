package com.contactali.sparkrelay;

import com.loopj.android.http.*;

public class SparkRestClient {
    private static final String ACCESS_TOKEN = "5bb63ad8bd830c79390ae66452686fb05838515c";
    private static final String DEVICE_ID    = "53ff6a066667574826522467";
    private static final String RELAY_FN     = "/relay";
    private static final String BASE_URL     = "https://api.spark.io/v1/devices/";

    private static AsyncHttpClient client    = new AsyncHttpClient();

    public static void switchRelay(int relay, boolean mode, AsyncHttpResponseHandler responseHandler) {
        client.post(
            BASE_URL + DEVICE_ID + RELAY_FN,
            relayParams(relay, mode),
            responseHandler
        );
    }

    private static RequestParams relayParams(Integer relay, boolean mode) {
        String smode = "LOW";

        if (mode)
            smode = "HIGH";

        RequestParams params = new RequestParams();
        params.put("access_token", ACCESS_TOKEN);
        params.put("params",       "r" + relay.toString() + "," + smode);

        return params;
    }

}
