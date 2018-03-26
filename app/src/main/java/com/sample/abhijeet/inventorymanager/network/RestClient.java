package com.sample.abhijeet.inventorymanager.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by abhi2 on 3/19/2018.
 */

public class RestClient
{
    private static final String BASE_URL =  MyApplication.getAppContext().getResources().getString(R.string.SERVER_BASE_URL);

    private static  final AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.e(">>>>>>>>>>>>URL",getAbsoluteUrl(url));
        client.post(getAbsoluteUrl(url), null, responseHandler);
    }

    public static void post(String url, HttpEntity httpEntity, String contentType, AsyncHttpResponseHandler responseHandler) {
        client.post(MyApplication.getAppContext(),getAbsoluteUrl(url),httpEntity,contentType,responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public  synchronized static AsyncHttpClient getAsyncHttpClientInstance()
    {
        return  client;
    }
}
