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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.provider.BaseColumns;

import com.sample.abhijeet.inventorymanager.util.DateConverter;

import java.util.Date;


/**
 * Represents one record of the Purchase table.
 */
@Entity(tableName = Purchase.TABLE_NAME)
public class Purchase {

    /** The name of the Purchase table. */
    public static final String TABLE_NAME = "purchases";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The names of the name columns. */
    public static final String COLUMN_PURCHASE_SERIAL = "purchaseSerial";
    public static final String COLUMN_USER_UUID = "userUuid";
    public static final String COLUMN_ITEM_BARCODE = "itemBarcode";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_ITEM_PRICE = "itemPrice";
    public static final String COLUMN_CREATED_AT = "createdAt";
    public static final String COLUMN_MODIFIED_AT = "modifiedAt";

    /** The unique ID of the purchases. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_PURCHASE_SERIAL)
    public  int purchaseSerial;
    /** The names of the different database columns. */
    @ColumnInfo(name = COLUMN_USER_UUID)
    public String userUuid;
    @ColumnInfo(name = COLUMN_ITEM_BARCODE)
    public String itemBarcode;
    @ColumnInfo(name = COLUMN_ITEM_NAME)
    public String itemName;
    @ColumnInfo(name = COLUMN_ITEM_PRICE)
    public int itemPrice;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = COLUMN_CREATED_AT)
    public Date createdAt;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = COLUMN_MODIFIED_AT)
    public Date modifiedAt;

    /**
     * Create a new {@link Purchase} from the specified {@link ContentValues}.
     *
     * @return A newly created {@link Purchase} instance.
     */
    public static Purchase fromContentValues(ContentValues values) {
        final Purchase purchase = new Purchase();

        if (values.containsKey(COLUMN_PURCHASE_SERIAL)) {
            purchase.purchaseSerial = values.getAsInteger(COLUMN_PURCHASE_SERIAL);
        }
        if (values.containsKey(COLUMN_USER_UUID)) {
            purchase.userUuid = values.getAsString(COLUMN_USER_UUID);
        }
        if (values.containsKey(COLUMN_ITEM_BARCODE)) {
            purchase.itemBarcode = values.getAsString(COLUMN_ITEM_BARCODE);
        }
        if (values.containsKey(COLUMN_ITEM_PRICE)) {
            purchase.itemPrice = values.getAsInteger(COLUMN_ITEM_PRICE);
        }
        if (values.containsKey(COLUMN_ITEM_NAME)) {
            purchase.itemName = values.getAsString(COLUMN_ITEM_NAME);
        }
        if (values.containsKey(COLUMN_CREATED_AT)) {
            purchase.createdAt = (Date)values.get(COLUMN_CREATED_AT);
        }
        if (values.containsKey(COLUMN_MODIFIED_AT)) {
            purchase.modifiedAt = (Date)values.get(COLUMN_MODIFIED_AT);
        }
        return purchase;
    }

    public int getPurchaseSerial() {
        return purchaseSerial;
    }

    public void setPurchaseSerial(int purchaseSerial) {
        this.purchaseSerial = purchaseSerial;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
