package com.sample.abhijeet.inventorymanager.network;

import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.beans.PurchaseDetailResponseBean;
import com.sample.abhijeet.inventorymanager.beans.UserLoginBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Webservice
{
    /* Add purchase by a USER*/
    @GET("item/purchase/{userUUID}")
    Call<PurchaseDetailResponseBean[]> getAllPurchasesForUser(@Path("userUUID") String userUUID);

    /*User login*/
    @POST("Login/user/")
    Call<LoginResponseBean> loginUser(@Body UserLoginBean ulb);

}
