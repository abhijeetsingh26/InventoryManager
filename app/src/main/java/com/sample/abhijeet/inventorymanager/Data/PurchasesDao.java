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
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;


/**
 * Data access object for Purchase.
 */
@Dao
public interface PurchasesDao {

    /**
     * Counts the number of cheeses in the table.
     *
     * @return The number of cheeses.
     */
    @Query("SELECT COUNT(*) FROM " + Purchase.TABLE_NAME)
    int count();

    /**
     * Inserts a purchase into the table.
     *
     * @param purchase A new purchase.
     * @return The row ID of the newly inserted purchase.
     */
    @Insert
    long insert(Purchase purchase);

    /**
     * Inserts multiple purchases into the database
     *
     * @param purchases An array of new purchases.
     * @return The row IDs of the newly inserted purchases.
     */
    @Insert
    long[] insertAll(Purchase[] purchases);

    /**
     * Select all cheeses.
     *
     * @return A {@link Cursor} of all the cheeses in the table.
     */
    @Query("SELECT * FROM " + Purchase.TABLE_NAME)
    LiveData<Purchase[] > selectAll();

    /**
     * Select a purchaseDao by the ID.
     *
     * @param id The row ID.
     * @return A {@link Cursor} of the selected purchaseDao.
     */
    @Query("SELECT * FROM " + Purchase.TABLE_NAME + " WHERE " + Purchase.COLUMN_PURCHASE_SERIAL + " = :id")
    Purchase selectById(long id);

    /**
     * Delete a purchaseDao by the ID.
     *
     * @param id The row ID.
     * @return A number of cheeses deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Purchase.TABLE_NAME + " WHERE " + Purchase.COLUMN_PURCHASE_SERIAL + " = :id")
    int deleteById(long id);

    /**
     * Update the purchase. The purchase is identified by the row ID.
     *
     * @param purchase The purchase to update.
     * @return A number of purchase updated. This should always be {@code 1}.
     */
    @Update
    int update(Purchase purchase);

    /**
     * Select all purchases by user ID.
     *
     * @return A {@link Cursor} of all the cheeses in the table.
     */
    @Query("Select * FROM " + Purchase.TABLE_NAME + " WHERE " + Purchase.COLUMN_USER_UUID + " = :userUUID")
    LiveData<Purchase[] > selectAllBySerial(String userUUID);

    /**
     * delete all purchases.
     *
     * @return A {@link Integer} which is number of rows deleted.
     */
    @Query("Delete FROM " + Purchase.TABLE_NAME)
   int deleteAll();

}
