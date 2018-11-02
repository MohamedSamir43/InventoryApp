package com.example.mohamedsamir1495.inventoryapp;

import java.util.UUID;

public class Product {


    public Supplier mProductSupplier;
    private String mProductId, mProductName, mProductPrice;
    private int mProductQuantitiy;

    public Product(String mProductName, String mProductPrice, int mProductQuantity, Supplier mProductSupplier) {
        this.mProductName = mProductName;
        this.mProductPrice = mProductPrice;
        this.mProductQuantitiy = mProductQuantity;
        this.mProductSupplier = mProductSupplier;
        this.mProductId = UUID.randomUUID().toString();
    }

    public Product(String mProductId, String mProductName, String mProductPrice, int mProductQuantity, Supplier mProductSupplier) {
        this.mProductName = mProductName;
        this.mProductPrice = mProductPrice;
        this.mProductQuantitiy = mProductQuantity;
        this.mProductSupplier = mProductSupplier;
        this.mProductId = mProductId;
    }

    @Override
    public String toString() {

        String objectStr = "ProductName : " + mProductName + "\n"
                + "ProductPrice : " + mProductPrice + "\n"
                + "Product Quantity : " + mProductQuantitiy + "\n"
                + "Product Supplier Name : " + mProductSupplier.getSupplierName() + "\n"
                + "Product Supplier Phone : " + mProductSupplier.mSupplierPhone + "\n";
        return objectStr;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getProductPrice() {
        return mProductPrice;

    }

    public void setProductPrice(String productPrice) {
        mProductPrice = productPrice;
    }

    public int getProductQuantitiy() {
        return mProductQuantitiy;
    }

    public void setProductQuantitiy(int productQuantitiy) {
        mProductQuantitiy = productQuantitiy;
    }

    public Supplier getProductSupplier() {
        return mProductSupplier;
    }

    public void setProductSupplier(Supplier productSupplier) {
        mProductSupplier = productSupplier;
    }

    public String getProductId() {
        return mProductId;
    }

    public static class Supplier {
        private String mSupplierName, mSupplierPhone;

        public Supplier(String mSupplierName, String mSupplierPhoneNumber) {
            this.mSupplierName = mSupplierName;
            this.mSupplierPhone = mSupplierPhoneNumber;
        }

        public String getSupplierName() {
            return mSupplierName;
        }

        public void setSupplierName(String supplierName) {
            mSupplierName = supplierName;
        }

        public String getSupplierPhone() {
            return mSupplierPhone;
        }

        public void setSupplierPhone(String supplierPhone) {
            mSupplierPhone = supplierPhone;
        }

    }


}
