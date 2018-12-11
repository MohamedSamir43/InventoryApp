package com.example.mohamedsamir1495.inventoryapp.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {
    public static final String CONTENT_AUTHORITY =  "com.example.anrdoid.inventoryappstage2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INVENTORY = "product";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "UDACITY_DB.db";
    private static final String TEXT_TYPE = " TEXT";
    private  static  final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private DBContract() {
    }

    public static class ProductTable implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +  PATH_INVENTORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        // SUPPLIER_NAME LIST VALUES
        public final static int SUPPLIER_UNKNOWN = 0;
        public final static int SUPPLIER_AMAZON = 1;
        public final static int SUPPLIER_JARIRR = 2;
        public final static int SUPPLIER_OBEIKAN = 3;

        public static boolean isValidSupplierName(int suppliername) {
            if (suppliername == SUPPLIER_UNKNOWN || suppliername == SUPPLIER_AMAZON || suppliername == SUPPLIER_JARIRR || suppliername == SUPPLIER_OBEIKAN) {
                return true;
            }
            return false;
        }

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
