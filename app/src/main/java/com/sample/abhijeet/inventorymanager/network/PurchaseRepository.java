package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.LiveData;

import com.sample.abhijeet.inventorymanager.Data.Purchase;

public interface PurchaseRepository
{
    LiveData<Purchase[]> getObserveablePurchasesForUser(String userUUID);
    void fetchAndSavePurchasesFromNetwork(String userUUID);
    void createPurchaseForUser(String barcode);
    void deletePurchaseForUser(int purhcaseId);
    void deletePurchaseForUserWithCallback(int purchaseId, APICallbacks apiCallbacks);
}
