package com.example.mohamedsamir1495.inventoryapp.Views;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamedsamir1495.inventoryapp.Database.DBContract.ProductTable;
import com.example.mohamedsamir1495.inventoryapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_INVENTORY_LOADER = 0;
    private Uri currentProductUri;

    @BindView(R.id.product_name_view_text)
    TextView productNameViewText;

    @BindView(R.id.product_price_view_text)
    TextView productPriceViewText;

    @BindView(R.id.product_quantity_view_text)
    TextView productQuantityViewText;

    @BindView(R.id.product_supplier_name_view_text)
    TextView productSupplierNameSpinner;

    @BindView(R.id.product_supplier_phone_number_view_text)
    TextView productSupplierPhoneNumberViewText;

    @BindView(R.id.decrease_button)
    Button productDecreaseButton;

    @BindView(R.id.increase_button)
    Button productIncreaseButton;

    @BindView(R.id.delete_button)
    Button productDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        currentProductUri = intent.getData();
        if (currentProductUri == null) {
            invalidateOptionsMenu();
        } else {
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ProductTable._ID,
                ProductTable.PRODUCT_NAME,
                ProductTable.PRODUCT_PRICE,
                ProductTable.PRODUCT_QUANTITY,
                ProductTable.PRODUCT_SUPPLIER_NAME,
                ProductTable.PRODUCT_SUPPLIER_PHONE
        };
        return new CursorLoader(this,
                currentProductUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {

            final int idColumnIndex = cursor.getColumnIndex(ProductTable._ID);
            int nameColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_SUPPLIER_PHONE);

            String currentName = cursor.getString(nameColumnIndex);
            final int currentPrice = cursor.getInt(priceColumnIndex);
            final int currentQuantity = cursor.getInt(quantityColumnIndex);
            int currentSupplierName = cursor.getInt(supplierNameColumnIndex);
            final int currentSupplierPhone = cursor.getInt(supplierPhoneColumnIndex);

            productNameViewText.setText(currentName);
            productPriceViewText.setText(Integer.toString(currentPrice));
            productQuantityViewText.setText(Integer.toString(currentQuantity));
            productSupplierPhoneNumberViewText.setText(Integer.toString(currentSupplierPhone));


            switch (currentSupplierName) {
                case ProductTable.SUPPLIER_AMAZON:
                    productSupplierNameSpinner.setText(getText(R.string.supplier_amazon));
                    break;
                case ProductTable.SUPPLIER_JUMIA:
                    productSupplierNameSpinner.setText(getText(R.string.supplier_jumia));
                    break;
                case ProductTable.SUPPLIER_SOUQ:
                    productSupplierNameSpinner.setText(getText(R.string.supplier_souq));
                    break;
                default:
                    productSupplierNameSpinner.setText(getText(R.string.supplier_unknown));
                    break;
            }

            productDecreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decreaseCount(idColumnIndex, currentQuantity);
                }
            });

            productIncreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    increaseCount(idColumnIndex, currentQuantity);
                }
            });

            productDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteConfirmationDialog();
                }
            });

            Button phoneButton = findViewById(R.id.phone_button);
            phoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = String.valueOf(currentSupplierPhone);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void decreaseCount(int productID, int productQuantity) {
        productQuantity = productQuantity - 1;
        if (productQuantity >= 0) {
            updateProduct(productQuantity);
            Toast.makeText(this, getString(R.string.quantity_change_msg), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, getString(R.string.quantity_finish_msg), Toast.LENGTH_SHORT).show();
        }
    }

    public void increaseCount(int productID, int productQuantity) {
        productQuantity = productQuantity + 1;
        if (productQuantity >= 0) {
            updateProduct(productQuantity);
            Toast.makeText(this, getString(R.string.quantity_change_msg), Toast.LENGTH_SHORT).show();

        }
    }


    private void updateProduct(int productQuantity) {

        if (currentProductUri == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(ProductTable.PRODUCT_QUANTITY, productQuantity);

        if (currentProductUri == null) {
            Uri newUri = getContentResolver().insert(ProductTable.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.insert_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(currentProductUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.update_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteProduct() {
        if (currentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(currentProductUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}