package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sample.abhijeet.inventorymanager.Data.Database;
import com.sample.abhijeet.inventorymanager.Data.Purchase;
import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.beans.PurchaseRequestBean;
import com.sample.abhijeet.inventorymanager.beans.UserLoginBean;
import com.sample.abhijeet.inventorymanager.util.AppExecutors;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PurchaseRepositoryImpl implements PurchaseRepository
{
    private String LOG_TAG = PurchaseRepositoryImpl.class.getSimpleName();
    Webservice mWebservice;
    AppExecutors mAppExecutors;
    Database mDatabase;


    private LiveData<Purchase[]> data = new MutableLiveData<>();

    @Inject
    public PurchaseRepositoryImpl(Webservice webservice, AppExecutors appExecutors, Database database)
    {
        this.mWebservice = webservice;
        this.mAppExecutors = appExecutors;
        this.mDatabase = database;
    }

    public LiveData<Purchase[]> getObserveablePurchasesForUser(String userUUID) {


        try {
            /***********Try to load the data from the datasase first, meanwhile issue a REST call to get the latest data*************/
          loadPurchasesFromDatabase(userUUID);
         // ;
        } catch (Exception e) {
            Log.e(LOG_TAG,">>>>> IN Exception Block");
            e.printStackTrace();
        }
        return data;
    }


    public void loadSavePurchasesFromNetwork(String userUUID) {
        mWebservice.getAllPurchasesForUser(userUUID).enqueue(new Callback<Purchase[]>() {
            @Override
            public void onResponse(@NonNull Call<Purchase[]> call, Response<Purchase[]> response) {
                Purchase[] purchases = response.body();
                savePurchasesIntoDatabase(purchases);
            }

            @Override
            public void onFailure(@NonNull Call<Purchase[]> call, Throwable t) {
                Log.e(">>>>>",">>>>> IN Failure block", t);
            }
        });
    }

    @Override
    public void createPurchaseForUser(String barcode) {
        PurchaseRequestBean prb = new PurchaseRequestBean();
        prb.setItemBarCode(barcode);
        prb.setUserUUID(GlobalSettings.getCurrentUserUUID());
        mWebservice.createPurchaseForUser(prb).enqueue(new Callback<Purchase[]>() {
            @Override
            public void onResponse(Call<Purchase[]> call, Response<Purchase[]> response) {
                if(response.code() == 201)
                    savePurchasesIntoDatabase(response.body());
                else
                    Log.e(LOG_TAG,"Error getting correct response code");
            }

            @Override
            public void onFailure(Call<Purchase[]> call, Throwable t) {
                Log.e(LOG_TAG,"FAILED TO GET PURCHASES");
            }
        });
    }

    /***** Saves purchases into database, Runs in a background Thread*****/
    public void savePurchasesIntoDatabase(Purchase[] purchases)
    {
        savePurchasesIntoDatabaseNEW(purchases);
        /*mAppExecutors.diskIO().execute(
                () ->{
                    mDatabase.purchaseDao().deleteAll();
        for(Purchase currentPurchase: purchases)
        {
            if(mDatabase.purchaseDao().selectById(currentPurchase.purchaseSerial) != null) {
                int updateCount =   mDatabase.purchaseDao().update(currentPurchase);
                Log.e(LOG_TAG,">>>>> UPDATED PURCHASE " +  updateCount);
            }
            else {
                Long id = mDatabase.purchaseDao().insert(currentPurchase);
                Log.e(LOG_TAG,">>>>> INSERTED PURCHASE " +  id);
            }
        } });*/
    }

    private void savePurchasesIntoDatabaseNEW(Purchase[] purchases)
    {
        mAppExecutors.diskIO().execute(
                () ->{
                    mDatabase.purchaseDao().deleteAll();
                    mDatabase.purchaseDao().insertAll(purchases);
                });
    }

    private void loadPurchasesFromDatabase(String userUUID) {
        data = mDatabase.purchaseDao().selectAllBySerial(userUUID);
    }


    class LoginsyncTask extends AsyncTask<String, Void, LoginResponseBean>
    {

        @Override
    protected LoginResponseBean doInBackground(String... idToken)
    {
        Log.e(">>>>>>>> ",">>>>>> ENTER DO IN BACKGROUND: ");
        LoginResponseBean loginResponseBean =  dologin(idToken[0]);
        if(loginResponseBean != null) {
            String userUUID = loginResponseBean.getUserUUID();
            GlobalSettings.setCurrentUserUUID(userUUID);
        }
        return loginResponseBean;
    }


    }

   public LoginResponseBean loginUser(String idToken) {
       LoginsyncTask loginsyncTask =  new LoginsyncTask();
       loginsyncTask.execute(idToken);
       return null;
   }

    private  LoginResponseBean dologin(String idToken) {

        final List<String> data = new ArrayList<>();

        LoginResponseBean loginResponseBean = null;

        try {
            UserLoginBean ulb = new UserLoginBean();
            ulb.setIdToken(idToken);
            Call<LoginResponseBean> call = mWebservice.loginUser(ulb);
            Response<LoginResponseBean> response = call.execute();
            if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                Log.e(">>>>>",">>>>> IN LOGIN ERRORRRRRRR [" + response.errorBody().toString()+"]");
                //DO ERROR HANDLING HERE
                return null;
            }
            loginResponseBean = response.body();
            Log.e(">>>>>",">>>>> IN LOGINSUCCESSSSSSS [" + loginResponseBean+"]");
          //  ApplicationUtils.getInstance().showToast(loginResponseBean.getMessage(), Toast.LENGTH_SHORT);
        } catch (IOException e) {
            Log.e(">>>>>",">>>>> IN LOGIN EXCEPTION BLOCK");
            e.printStackTrace();
        }
        return loginResponseBean;
    }
}
