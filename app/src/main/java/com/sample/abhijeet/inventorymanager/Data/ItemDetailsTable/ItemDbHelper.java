package com.sample.abhijeet.inventorymanager.Data.ItemDetailsTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Faberwork on 14-03-2018.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "inventorymanager.db";

    //This version needs to be updated whenever a new database is created
    public static int DATABASE_VERSION = 1;

    //Public so that any class can access it and use it to create a new database instance
    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " ("
                + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemContract.ItemEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + ItemContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_VALUE + " INTEGER NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_OWNER + " TEXT NOT NULL;";

        //Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_TODO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
