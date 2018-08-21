package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import com.sample.abhijeet.inventorymanager.Data.Database;
import com.sample.abhijeet.inventorymanager.Data.ItemDetails;
import com.sample.abhijeet.inventorymanager.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    public void fetchAndSaveItemDetails() {
        mWebservice.getAllItems().enqueue(new Callback<List<ItemDetails>>() {
            @Override
            public void onResponse(Call<List<ItemDetails>> call, Response<List<ItemDetails>> response) {
                if (response.code() >= 200 && response.code() <300)
                    saveItemToDatabase(response.body());
                else
                    Log.e(LOG_TAG,"IN response block" + call.request());
            }

            @Override
            public void onFailure(Call<List<ItemDetails>> call, Throwable t) {
                Log.e(LOG_TAG,"IN Get all items Failed Block" + call.request(),t);
            }
        });
    }

    @Override
    public ItemDetails fetchItemByBarcode(String barcode) {
        // Blocks current thread to make a sync Call
        CountDownLatch latch = new CountDownLatch(1);
      final ArrayList <ItemDetails> itemDetailsList = new ArrayList<>();
        mAppExecutors.diskIO().execute(()->{
            itemDetailsList.add( mDatabase.itemDetailsDao().findItemByBarcode(barcode));
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(itemDetailsList.size() > 0)
            return itemDetailsList.get(0);
        else
            return null;
    }

    private void saveItemToDatabase(List<ItemDetails> itemDetails) {
        mAppExecutors.diskIO().execute(()->{
            if(null != itemDetails) {
                mDatabase.itemDetailsDao().deleteAll();
                mDatabase.itemDetailsDao().insertAll(itemDetails);
            }
        });
    }
}
