package com.sample.abhijeet.inventorymanager.models;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.sample.abhijeet.inventorymanager.beans.PurchaseDetailResponseBean;
import com.sample.abhijeet.inventorymanager.network.Repository;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PurchaseDetailsViewModel  extends ViewModel
{
    Logger logger =  Logger.getLogger(PurchaseDetailsViewModel.class.getSimpleName());
    private String TAG = PurchaseDetailsViewModel.class.getSimpleName();

    private MutableLiveData< PurchaseDetailResponseBean[]> purhcaseList;
    List<String> fruitsStringList = new ArrayList<>();
    Handler myHandler = new Handler();

    public LiveData< PurchaseDetailResponseBean[]> getPurchaseList() {
        String userUUID = GlobalSettings.getCurrentUserUUID();
        if (purhcaseList == null) {
            purhcaseList = new MutableLiveData<>();
            loadPurchases(userUUID);
        }
        return purhcaseList;
    }

        private void loadPurchases(String userUUID) {

        Log.e(">>>>>>>> ",">>>>>> BEFORE DO IN BACKGROUND: ");
        class JSONAsyncTask extends AsyncTask<String, Void,  PurchaseDetailResponseBean[]> {

            private ProgressDialog progressDialog;

            @Override
            protected   PurchaseDetailResponseBean[] doInBackground(String... userUUID) {
                Log.e(">>>>>>>> ",">>>>>> ENTER DO IN BACKGROUND: ");
                Repository repository =  new Repository();
                PurchaseDetailResponseBean[] liveData =  repository.getAllPurchasesForUser(userUUID[0]);
                return liveData;
            }

            @Override
            protected void onPostExecute( PurchaseDetailResponseBean[] liveData) {
                Log.e(">>>>>>>> ",">>>>>> LIVE DATA OBJECT: "+ liveData);
                purhcaseList.postValue(liveData);
            }
        }

        JSONAsyncTask task = new JSONAsyncTask();
        task.execute(userUUID);


    }

    public  void refreshPurchaseDetails()
    {
        loadPurchases(GlobalSettings.getCurrentUserUUID());
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "on cleared called");
    }
}
