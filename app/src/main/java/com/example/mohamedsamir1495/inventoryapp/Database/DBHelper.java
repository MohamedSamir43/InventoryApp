package com.example.mohamedsamir1495.inventoryapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohamedsamir1495.inventoryapp.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    public static long insert(SQLiteDatabase db, Product toBeInsertedProduct) {
        ContentValues dataToBeInserted = new ContentValues();

        dataToBeInserted.put(DBContract.ProductTable.PRODUCT_ID, toBeInsertedProduct.getProductId());
        dataToBeInserted.put(DBContract.ProductTable.PRODUCT_NAME, toBeInsertedProduct.getProductName());
        dataToBeInserted.put(DBContract.ProductTable.PRODUCT_PRICE, toBeInsertedProduct.getProductPrice());
        dataToBeInserted.put(DBContract.ProductTable.PRODUCT_QUANTITY, toBeInsertedProduct.getProductQuantitiy());
        dataToBeInserted.put(DBContract.ProductTable.PRODUCT_SUPPLIER_NAME, toBeInsertedProduct.getProductSupplier().getSupplierName());
        dataToBeInserted.put(DBContract.ProductTable.PRODUCT_SUPPLIER_PHONE, toBeInsertedProduct.getProductSupplier().getSupplierPhone());
        return db.insert(DBContract.ProductTable.TABLE_NAME, null, dataToBeInserted);
    }

    public static List<Product> retriveAll(SQLiteDatabase db) {
        List<Product> retrievedInfo = new ArrayList<>();

        String[] dataColumns = new String[]{
                DBContract.ProductTable.PRODUCT_ID,
                DBContract.ProductTable.PRODUCT_NAME,
                DBContract.ProductTable.PRODUCT_PRICE,
                DBContract.ProductTable.PRODUCT_QUANTITY,
                DBContract.ProductTable.PRODUCT_SUPPLIER_NAME,
                DBContract.ProductTable.PRODUCT_SUPPLIER_PHONE
        };

        Cursor returnedQueryData = db.query(DBContract.ProductTable.TABLE_NAME,
                dataColumns, null, null, null, null, null);

        if (returnedQueryData != null && returnedQueryData.isAfterLast() == false) {
            returnedQueryData.moveToFirst();
            while (returnedQueryData.isAfterLast() == false) {
                String productId = returnedQueryData.getString(returnedQueryData.getColumnIndex(DBContract.ProductTable.PRODUCT_ID));
                String productName = returnedQueryData.getString(returnedQueryData.getColumnIndex(DBContract.ProductTable.PRODUCT_NAME));
                String productPrice = returnedQueryData.getString(returnedQueryData.getColumnIndex(DBContract.ProductTable.PRODUCT_PRICE));
                int productQuantity = returnedQueryData.getInt(returnedQueryData.getColumnIndex(DBContract.ProductTable.PRODUCT_QUANTITY));

                String productSupplierName = returnedQueryData.getString(returnedQueryData.getColumnIndex(DBContract.ProductTable.PRODUCT_SUPPLIER_NAME));
                String productSupplierPhone = returnedQueryData.getString(returnedQueryData.getColumnIndex(DBContract.ProductTable.PRODUCT_SUPPLIER_PHONE));
                retrievedInfo.add(new Product(productId, productName, productPrice, productQuantity, new Product.Supplier(productSupplierName, productSupplierPhone)));

                returnedQueryData.moveToNext();

            }
        }
        returnedQueryData.close();
        return retrievedInfo;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.ProductTable.CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.ProductTable.DROP_PRODUCT_TABLE);
        onCreate(db);
    }
}