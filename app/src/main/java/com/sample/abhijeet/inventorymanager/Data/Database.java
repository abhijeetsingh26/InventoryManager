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

import com.sample.abhijeet.inventorymanager.util.MyApplication;

import javax.inject.Singleton;


/**
 * The Room database.
 */
@android.arch.persistence.room.Database(entities = {Purchase.class, ItemDetails.class}, version = 2, exportSchema = false)
@Singleton
public abstract class Database extends RoomDatabase {

    /**
     * @return The DAO for the Purchase table.
     */
    @SuppressWarnings("WeakerAccess")
    public abstract PurchasesDao purchaseDao();

    public abstract ItemDetailsDao itemDetailsDao();

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
                    .databaseBuilder(MyApplication.getAppContext(), Database.class, "InventoryManager").fallbackToDestructiveMigration().build();
        }
        return sInstance;
    }

}
