package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import com.sample.abhijeet.inventorymanager.Data.Database;
import com.sample.abhijeet.inventorymanager.Data.ItemDetails;
import com.sample.abhijeet.inventorymanager.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsRepositoryImpl implements ItemDetailsRepository {

    private String LOG_TAG = ItemDetailsRepositoryImpl.class.getSimpleName();
    private Webservice mWebservice;
     private AppExecutors mAppExecutors;
    private  Database mDatabase;


    private MediatorLiveData<ItemDetails> data = new MediatorLiveData<>();

    @Inject
    public ItemDetailsRepositoryImpl(Webservice webservice, AppExecutors appExecutors, Database database)
    {
        this.mWebservice = webservice;
        this.mAppExecutors = appExecutors;
        this.mDatabase = database;
    }


    @Override
    public MediatorLiveData<List<ItemDetails>> fetchAndSaveItemDetails() {
        mWebservice.getAllItems().enqueue(new Callback<List<ItemDetails>>() {
            @Override
            public void onResponse(Call<List<ItemDetails>> call, Response<List<ItemDetails>> response) {
                saveAllItems(response.body());
            }

            @Override
            public void onFailure(Call<List<ItemDetails>> call, Throwable t) {
                Log.e(LOG_TAG,"IN Get all items Failed Block" + call.request(),t);
            }
        });
        return null;
    }

    private void saveAllItems(List<ItemDetails> itemDetails) {
        mAppExecutors.diskIO().execute(()->{
            mDatabase.itemDetailsDao().insertAll(itemDetails);
        });
    }
}
