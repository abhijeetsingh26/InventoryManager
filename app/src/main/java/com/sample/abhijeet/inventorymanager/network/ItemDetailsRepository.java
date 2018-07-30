package com.sample.abhijeet.inventorymanager.network;

import android.arch.lifecycle.MediatorLiveData;

import com.sample.abhijeet.inventorymanager.Data.ItemDetails;

import java.util.List;

public interface ItemDetailsRepository {
    MediatorLiveData<List<ItemDetails>> fetchAndSaveItemDetails();
}
