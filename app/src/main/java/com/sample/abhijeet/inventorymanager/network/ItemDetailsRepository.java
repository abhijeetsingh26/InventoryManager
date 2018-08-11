package com.sample.abhijeet.inventorymanager.network;

import com.sample.abhijeet.inventorymanager.Data.ItemDetails;

import java.util.concurrent.CountDownLatch;

public interface ItemDetailsRepository {
    void fetchAndSaveItemDetails();

    ItemDetails fetchItemByBarcode(String barcode);

}
