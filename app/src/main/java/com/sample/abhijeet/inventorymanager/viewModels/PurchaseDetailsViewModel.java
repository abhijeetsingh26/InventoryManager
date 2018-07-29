package com.sample.abhijeet.inventorymanager.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.sample.abhijeet.inventorymanager.Data.Purchase;
import com.sample.abhijeet.inventorymanager.modules.ApplicationModule;
import com.sample.abhijeet.inventorymanager.modules.DaggerApplicationComponent;
import com.sample.abhijeet.inventorymanager.network.PurchaseRepository;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import javax.inject.Inject;

public class PurchaseDetailsViewModel  extends ViewModel
{
  private  String LOG_TAG = PurchaseDetailsViewModel.class.getSimpleName();


    @Inject
    PurchaseRepository purchaseRepository;

    String userUUID = GlobalSettings.getCurrentUserUUID();

    //TODO: Remove field injection and implement a constructor injection
    {
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(new MyApplication()))
                .build() .inject(this);
    }

    /************ The object that we retun ************/
    private LiveData< Purchase[]> purchaseList;

    public LiveData< Purchase[]> getPurchaseList() {
        if (purchaseList == null) {
            purchaseList = new MutableLiveData<>();
            purchaseList =   purchaseRepository.getObserveablePurchasesForUser(userUUID);
            refreshPurchases();
        }
        return purchaseList;

    }

    private void refreshPurchases()
    {
        try {
            Log.e(LOG_TAG,"=========== Getting user purchases ==========");
            purchaseRepository.getObserveablePurchasesForUser(userUUID);

        }catch(Exception e)
        {
            Log.e(LOG_TAG,"Exception in getting purchases",e);
        }

    }

    public void savepurchases()
    {
        purchaseRepository.loadSavePurchasesFromNetwork(userUUID);
    }

    public void createPurchase(String barcode)
    {
        purchaseRepository.createPurchaseForUser(barcode);
    }
}
