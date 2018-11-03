package com.example.mohamedsamir1495.inventoryapp;

import android.provider.BaseColumns;

public class DBContract {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "UDACITY_DB.db";
    private static final String TEXT_TYPE = " TEXT";
    private  static  final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private DBContract() {
    }

    public static class ProductTable implements BaseColumns {
        public static final String TABLE_NAME = "Products";
        public static final String PRODUCT_ID = "product_id";
        public static final String PRODUCT_NAME = "product_name";
        public static final String PRODUCT_PRICE = "product_price";
        public static final String PRODUCT_QUANTITY = "product_quantity";
        public static final String PRODUCT_SUPPLIER_NAME = "product_supplier_name";
        public static final String PRODUCT_SUPPLIER_PHONE = "product_supplier_phone";
        public static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                PRODUCT_ID + " " + TEXT_TYPE + " PRIMARY KEY," +
                PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
                PRODUCT_PRICE + TEXT_TYPE + COMMA_SEP +
                PRODUCT_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                PRODUCT_SUPPLIER_NAME + TEXT_TYPE + COMMA_SEP +
                PRODUCT_SUPPLIER_PHONE + TEXT_TYPE + " )";
        public static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
