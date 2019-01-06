package com.sample.abhijeet.inventorymanager.network;

import com.sample.abhijeet.inventorymanager.Data.ItemDetails;
import com.sample.abhijeet.inventorymanager.Data.Purchase;
import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.beans.PurchaseRequestBean;
import com.sample.abhijeet.inventorymanager.beans.UserLoginBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservice
{
    /* Add purchase by a USER*/
    @GET("item/purchase/{userUUID}")
    Call<Purchase[]> getAllPurchasesForUser(@Path("userUUID") String userUUID);

    /*User login*/
    @POST("Login/user/")
    Call<LoginResponseBean> loginUser(@Body UserLoginBean ulb);


    /*POST a new Purchase*/ //TODO: Modify such that only the recent purchase is returned back
    @POST("item/purchaseNEW/")
    Call<Purchase[]> createPurchaseForUser(@Body PurchaseRequestBean purchaseRequestBean);


    /* Get a list of all the available items*/
    @GET("item/details/")
    Call <List<ItemDetails>> getAllItems();

    @DELETE("item/purchase")
    Call<Void> deletePurchaseById(@Query("purchaseId") int purchaseId);
}
