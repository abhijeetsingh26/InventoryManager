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

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.sample.abhijeet.inventorymanager.util.GlobalSettings;
import com.sample.abhijeet.inventorymanager.util.MyApplication;

import java.util.Date;

import javax.inject.Singleton;


/**
 * The Room database.
 */
@android.arch.persistence.room.Database(entities = {Purchase.class}, version = 1, exportSchema = false)
@Singleton
public abstract class Database extends RoomDatabase {

    /**
     * @return The DAO for the Purchase table.
     */
    @SuppressWarnings("WeakerAccess")
    public abstract PurchasesDao purchaseDao();

    /** The only instance */
    private static Database sInstance;

    /**
     * Gets the singleton instance of Database.
     *
     * //@param context The context.
     * @return The singleton instance of Database.
     */
    public static synchronized Database getInstance() {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(MyApplication.getAppContext(), Database.class, "InventoryManager").build();
            //sInstance.populateInitialData();
        }
        return sInstance;
    }

    /**
     * Switches the internal implementation with an empty in-memory database.
     *
     * @param context The context.
     */
    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                Database.class).build();
    }

    /**
     * Inserts the dummy data into the database if it is currently empty.
     */
    private void populateInitialData() {
        if (purchaseDao().count() == 0) {
            int SIZE = 5;
            Purchase[] dummyPurchases = new Purchase[SIZE];
            beginTransaction();
            try {
                for (int i = 0; i < SIZE; i++)
                {
                    Purchase purchase = new Purchase();
                    purchase.purchaseSerial = i;
                    purchase.userUuid = GlobalSettings.getCurrentUserUUID();
                    purchase.itemBarcode = "9000"+ i;
                    purchase.itemName = "item0"+i;
                    purchase.itemPrice = i;
                    purchase.createdAt =  new Date();
                    purchase.modifiedAt = new Date();
                    dummyPurchases[i] = purchase;
                }
                purchaseDao().insertAll(dummyPurchases);
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }

}
