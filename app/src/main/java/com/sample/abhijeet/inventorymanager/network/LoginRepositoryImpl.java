package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;
import android.widget.Toast;

import com.sample.abhijeet.inventorymanager.Data.Database;
import com.sample.abhijeet.inventorymanager.beans.LoginResponseBean;
import com.sample.abhijeet.inventorymanager.beans.UserLoginBean;
import com.sample.abhijeet.inventorymanager.util.AppExecutors;
import com.sample.abhijeet.inventorymanager.util.ApplicationUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository {

    private String LOG_TAG = LoginRepositoryImpl.class.getSimpleName();
    Webservice mWebservice;
    AppExecutors mAppExecutors;
    Database mDatabase;


    private MediatorLiveData<LoginResponseBean> data = new MediatorLiveData<>();

    @Inject
    public LoginRepositoryImpl(Webservice webservice, AppExecutors appExecutors, Database database)
    {
        this.mWebservice = webservice;
        this.mAppExecutors = appExecutors;
        this.mDatabase = database;
    }

    public MediatorLiveData<LoginResponseBean> doLoginUser(String idToken) {
        UserLoginBean ulb = new UserLoginBean();
        ulb.setIdToken(idToken);
        mWebservice.loginUser(ulb).enqueue(new Callback<LoginResponseBean>() {
            @Override
            public void onResponse(Call<LoginResponseBean> call, Response<LoginResponseBean> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponseBean> call, Throwable t) {
                ApplicationUtils.getInstance().showToast("Authentication Failed !", Toast.LENGTH_SHORT);
                Log.e(LOG_TAG,"IN Login Failed Block" + call.request(),t);
            }
        });

        return data;
    }

}
