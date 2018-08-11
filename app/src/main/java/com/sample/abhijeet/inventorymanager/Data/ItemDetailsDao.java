/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.abhijeet.inventorymanager.Data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Data access object for Purchase.
 */
@Dao
public interface ItemDetailsDao {

    @Query("SELECT COUNT(*) FROM " + ItemDetails.TABLE_NAME)
    int count();

    @Query("SELECT * FROM " + ItemDetails.TABLE_NAME)
    LiveData<List<ItemDetails> > selectAll();

    @Insert
    long[] insertAll(List<ItemDetails> allItems);

    @Query("Delete FROM " + ItemDetails.TABLE_NAME)
    int deleteAll();

    @Query("Select * FROM " + ItemDetails.TABLE_NAME + " WHERE " + ItemDetails.COLUMN_ITEM_BARCODE + " = :barcode")
    ItemDetails findItemByBarcode(String barcode);
}
