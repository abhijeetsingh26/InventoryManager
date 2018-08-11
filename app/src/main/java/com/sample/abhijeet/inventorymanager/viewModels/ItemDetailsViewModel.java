package com.sample.abhijeet.inventorymanager.viewModels;

import android.arch.lifecycle.ViewModel;

import com.sample.abhijeet.inventorymanager.Data.ItemDetails;
import com.sample.abhijeet.inventorymanager.modules.ApplicationModule;
import com.sample.abhijeet.inventorymanager.modules.DaggerApplicationComponent;
import com.sample.abhijeet.inventorymanager.network.ItemDetailsRepository;
import com.sample.abhijeet.inventorymanager.util.GlobalSettings;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

public class ItemDetailsViewModel extends ViewModel {
    @Inject
    ItemDetailsRepository itemDetailsRepository;

    String userUUID = GlobalSettings.getCurrentUserUUID();

    //TODO: Remove field injection and implement a constructor injection
    {
       DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(new MyApplication())).build().inject(this);
    }

    public void fetchAndSaveItemDetails()
    {
        itemDetailsRepository.fetchAndSaveItemDetails();
    }

    public ItemDetails fetchItemByBarcode(String barcode)
    {
         return itemDetailsRepository.fetchItemByBarcode(barcode);
    }
}
