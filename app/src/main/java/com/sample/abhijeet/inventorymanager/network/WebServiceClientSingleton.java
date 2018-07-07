package com.sample.abhijeet.inventorymanager.network;

import com.sample.abhijeet.inventorymanager.R;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


class WebServiceClientSingleton {



    private static final Retrofit ourInstance = new Retrofit.Builder().baseUrl(MyApplication.getAppContext().getResources().getString(R.string.SERVER_BASE_URL)) .addConverterFactory(JacksonConverterFactory.create()).build();

    static Retrofit getInstance() {
        return ourInstance;
    }

    private WebServiceClientSingleton() {
    }
}
