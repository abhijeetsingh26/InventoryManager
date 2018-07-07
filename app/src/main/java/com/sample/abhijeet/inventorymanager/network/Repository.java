package com.sample.abhijeet.inventorymanager.network;

import android.os.AsyncTask;
import android.util.Log;

import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.beans.PurchaseDetailResponseBean;
import com.sample.abhijeet.inventorymanager.beans.UserLoginBean;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class Repository
{
    private Webservice webservice = WebServiceClientSingleton.getInstance().create(Webservice.class);
    private String LOG_TAG = Repository.class.getSimpleName();

    public PurchaseDetailResponseBean[] getAllPurchasesForUser(String userUUID) {

        final List<String> data = new ArrayList<>();

        PurchaseDetailResponseBean[] responseArray = null;

        try {
            Call<PurchaseDetailResponseBean[]> call = webservice.getAllPurchasesForUser(userUUID);
            Response<PurchaseDetailResponseBean[]> response = call.execute();
            if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                Log.e(">>>>>",">>>>> IN PDRB ERRORRRRRRR [" + response.errorBody().toString()+"]");
                //DO ERROR HANDLING HERE
                return null;
            }
            responseArray = response.body();

            Log.e(">>>>>",">>>>> IN PDRB SUCCESSSSSSS [" + responseArray+"]");
        } catch (IOException e) {
            Log.e(">>>>>",">>>>> IN EXCEPTION BLOCK");
            e.printStackTrace();
        }
        return responseArray;
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
            Call<LoginResponseBean> call = webservice.loginUser(ulb);
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
