package com.example.mohamedsamir1495.inventoryapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohamedsamir1495.inventoryapp.Database.DBContract.ProductTable;



public class DBHelper extends SQLiteOpenHelper {

    //DB name
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + ProductTable.TABLE_NAME + " ("
                + ProductTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductTable.PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductTable.PRODUCT_PRICE + " INTEGER NOT NULL, "
                + ProductTable.PRODUCT_QUANTITY + " INTEGER NOT NULL, "
                + ProductTable.PRODUCT_SUPPLIER_NAME + " INTEGER NOT NULL DEFAULT 0, "
                + ProductTable.PRODUCT_SUPPLIER_PHONE + " INTEGER );";

        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductTable.TABLE_NAME);
    }
}
