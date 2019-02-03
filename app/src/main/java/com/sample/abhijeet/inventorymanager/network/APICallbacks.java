package com.sample.abhijeet.inventorymanager.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface APICallbacks extends Callback {

    @Override
    default  void onResponse(Call call, Response response) {

    }

    @Override
    default  void onFailure(Call call, Throwable t) {

    }
}
