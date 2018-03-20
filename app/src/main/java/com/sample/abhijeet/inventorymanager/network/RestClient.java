package com.sample.abhijeet.inventorymanager.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by abhi2 on 3/19/2018.
 */

public class RestClient
{
    private static final String BASE_URL = "http://192.168.1.5:8080/inventorywebservice/api";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.e(">>>>>>>>>>>>URL",getAbsoluteUrl(url));
        client.post(getAbsoluteUrl(url), null, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
