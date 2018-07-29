package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.LiveData;

import com.sample.abhijeet.inventorymanager.Data.Purchase;

public interface Repository
{
    LiveData<Purchase[]> getObserveablePurchasesForUser(String userUUID);
    void loadSavePurchasesFromNetwork(String userUUID);
    void createPurchaseForUser(String barcode);
}
