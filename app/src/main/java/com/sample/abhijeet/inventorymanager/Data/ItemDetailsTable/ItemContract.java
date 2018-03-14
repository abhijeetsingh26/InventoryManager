package com.sample.abhijeet.inventorymanager.Data.ItemDetailsTable;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Faberwork on 14-03-2018.
 */

public final class ItemContract {
    private  ItemContract(){};

    public static final String CONTENT_AUTHORITY = "com.sample.abhijeet.inventorymanager";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String  PATH_ITEM_DETAILS = "item_details";

    public static final class ItemEntry implements BaseColumns
    {
        public final static String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "item_details";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_ITEM_VALUE = "item_value";
        public static final String COLUMN_OWNER = "owner";
    }
}
