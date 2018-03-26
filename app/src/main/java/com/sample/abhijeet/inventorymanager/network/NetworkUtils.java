package com.sample.abhijeet.inventorymanager.network;

import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.beans.PurchaseRequestBean;
import com.sample.abhijeet.inventorymanager.beans.PurchaseResponseBean;
import com.sample.abhijeet.inventorymanager.beans.UserLoginBean;
import com.sample.abhijeet.inventorymanager.util.LogUtils;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by abhi2 on 3/18/2018.
 */

public class NetworkUtils {
    public static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final NetworkUtils ourInstance = new NetworkUtils();

    static PurchaseResponseBean returnPRB = null;

    public static NetworkUtils getInstance() {
        return ourInstance;
    }

    private NetworkUtils() {
    }


    public static String getJSONDataFromUrl(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makehttpRequest(url);
        } catch (IOException e) {
            LogUtils.logDebug( "Error in making http request", e);
        }
        return jsonResponse;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in Creating URL", e);
        }
        return url;
    }

    private static String makehttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error in connection!! Bad Response ");
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(3);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    public static void samplePOST(){
        String name = "androidUser" + random();
        RequestParams params = new RequestParams();
        params.put("id", "7");
        params.put("name", name);
        params.put("age", "30");
        params.put("salary", "3000");
        RestClient.post("/user/", params, new JsonHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                LogUtils.logError(">>>>>>>>>>>>>Failed: "+statusCode);
                LogUtils.logError(">>>>>>>>>>>>>Error : " + throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e(">>>>>>>>>>>>>Success: ", ""+statusCode);
            }

        });
    }


    public static void LoginPost(String idToken){
//        ProgressDialog progress = new ProgressDialog(MyApplication.getAppContext());
//        progress.setTitle("Authenticating");
//        progress.setMessage("Connecting to Server, Please wait.");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();

        RequestParams params = new RequestParams();
        AsyncHttpClient client =  RestClient.getAsyncHttpClientInstance();
        UserLoginBean ulb = new UserLoginBean();
        ulb.setIdToken(idToken);
        ulb.setUser_email("xyz@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        String jsonAsString ="";
        StringEntity entity = null;
        try {
            jsonAsString = mapper.writeValueAsString(ulb);
            entity = new StringEntity(jsonAsString);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String contentType = "application/json";
        RestClient.post("/Login/user/",entity,contentType, new JsonHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                LogUtils.logError(">>>>>>>>>>>>>Failed: "+statusCode);
                LogUtils.logError(">>>>>>>>>>>>>Error : " + throwable);
                ObjectMapper om = new ObjectMapper();
                LoginResponseBean lrb = null;
                try {
                    lrb = om.readValue(errorResponse.toString(), LoginResponseBean.class);
                } catch (IOException e)
                {
                    LogUtils.logError("Failed To parse response",e);
                }
                Toast.makeText(MyApplication.getAppContext(), lrb.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LogUtils.logError(">>>>>>>>>>>>>Success 2: "+statusCode);
               ObjectMapper om = new ObjectMapper();
                LoginResponseBean lrb = null;
                try {
                    lrb = om.readValue(response.toString(), LoginResponseBean.class);
                } catch (IOException e)
                {
                    LogUtils.logError("Failed To parse response",e);
                }
                Toast.makeText(MyApplication.getAppContext(), "Logged-In as  "+ lrb.getUserFname(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public static PurchaseResponseBean purchasePost(String itemBarcode, String userUUID){

//        ProgressDialog progress = new ProgressDialog(MyApplication.getAppContext());
//        progress.setTitle("Authenticating");
//        progress.setMessage("Connecting to Server, Please wait.");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();
        RequestParams params = new RequestParams();
        SyncHttpClient client =   new SyncHttpClient();
        PurchaseRequestBean prb = new PurchaseRequestBean();
        prb.setItemBarCode(itemBarcode);
        prb.setUserUUID(userUUID);
        ObjectMapper mapper = new ObjectMapper();
        String jsonAsString ="";
        StringEntity entity = null;
        try {
            jsonAsString = mapper.writeValueAsString(prb);
            entity = new StringEntity(jsonAsString);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String contentType = "application/json";
        client.post(MyApplication.getAppContext(),"http://192.168.1.102:8080/inventorywebservice/item/purchase/",entity,contentType, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ObjectMapper om = new ObjectMapper();
             //   om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                try {
                    String response =  new String(responseBody);
                    Log.e("TAGGG", response);
                    returnPRB = om.readValue(response, PurchaseResponseBean.class);
                } catch (IOException e)
                {
                    LogUtils.logError("Failed To parse response",e);
                    Log.e("TAGGGGGG","meessage", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ObjectMapper om = new ObjectMapper();
                try {
                    returnPRB = om.readValue(responseBody.toString(), PurchaseResponseBean.class);
                } catch (IOException e)
                {
                    LogUtils.logError("Failed To parse response",e);
                }
            }
        });
        return returnPRB;
    }
}




