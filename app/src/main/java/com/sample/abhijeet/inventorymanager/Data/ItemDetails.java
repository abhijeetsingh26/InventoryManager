package com.sample.abhijeet.inventorymanager.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = ItemDetails.TABLE_NAME)
public class ItemDetails {
    /** The name of the Purchase table. */
    public static final String TABLE_NAME = "item_details";


    /** The names of the name columns. */
    public static final String COLUMN_ITEM_BARCODE = "itemBarcode";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_ITEM_PRICE = "itemPrice";
    public static final String COLUMN_ITEM_EXTRA = "extra";

    /** The unique ID is the barcode of the item. */
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ITEM_BARCODE)
    @NonNull private String itemBarcode;
    /** The names of the different database columns. */
    @ColumnInfo(name = COLUMN_ITEM_NAME)
    private String itemName;
    @ColumnInfo(name = COLUMN_ITEM_PRICE)
    private int itemPrice;
    @ColumnInfo(name = COLUMN_ITEM_EXTRA)
    private String extra;

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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
