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
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mohamedsamir1495.inventoryapp.Database.DBContract.ProductTable;
import com.example.mohamedsamir1495.inventoryapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_INVENTORY_LOADER = 0;
    private Uri currentProductUri;

    @BindView(R.id.product_name_edit_text)
    EditText productNameEditText;

    @BindView(R.id.product_price_edit_text)
    EditText productPriceEditText;

    @BindView(R.id.product_quantity_edit_text)
    EditText productQuantityEditText;

    @BindView(R.id.product_supplier_name_spinner)
    Spinner  productSupplieNameSpinner;

    @BindView(R.id.product_supplier_phone_number_edit_text)
    EditText productSupplierPhoneNumberEditText;

    private int supplierName = ProductTable.SUPPLIER_UNKNOWN;

    private boolean productHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            productHasChanged = true;

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        currentProductUri = intent.getData();

        if (currentProductUri == null) {
            setTitle(getString(R.string.add_product));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.edit_product));
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);
        }


        productNameEditText.setOnTouchListener(mTouchListener);
        productPriceEditText.setOnTouchListener(mTouchListener);
        productQuantityEditText.setOnTouchListener(mTouchListener);
        productSupplieNameSpinner.setOnTouchListener(mTouchListener);
        productSupplierPhoneNumberEditText.setOnTouchListener(mTouchListener);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter productSupplieNameSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_options, android.R.layout.simple_spinner_item);

        productSupplieNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        productSupplieNameSpinner.setAdapter(productSupplieNameSpinnerAdapter);

        productSupplieNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier_amazon))) {
                        supplierName = ProductTable.SUPPLIER_AMAZON;
                    } else if (selection.equals(getString(R.string.supplier_jumia))) {
                        supplierName = ProductTable.SUPPLIER_JUMIA;
                    } else if (selection.equals(getString(R.string.supplier_souq))) {
                        supplierName = ProductTable.SUPPLIER_SOUQ;
                    } else {
                        supplierName = ProductTable.SUPPLIER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                supplierName = ProductTable.SUPPLIER_UNKNOWN;
            }
        });
    }


    private void saveProduct() {
        String productNameString = productNameEditText.getText().toString().trim();
        String productPriceString = productPriceEditText.getText().toString().trim();
        String productQuantityString = productQuantityEditText.getText().toString().trim();
        String productSupplierPhoneNumberString = productSupplierPhoneNumberEditText.getText().toString().trim();
        if (currentProductUri == null) {
            if (TextUtils.isEmpty(productNameString)) {
                Toast.makeText(this, getString(R.string.product_name_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(productPriceString)) {
                Toast.makeText(this, getString(R.string.price_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(productQuantityString)) {
                Toast.makeText(this, getString(R.string.quantity_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (supplierName == ProductTable.SUPPLIER_UNKNOWN) {
                Toast.makeText(this, getString(R.string.supplier_name_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(productSupplierPhoneNumberString)) {
                Toast.makeText(this, getString(R.string.supplier_phone_requires), Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();

            values.put(ProductTable.PRODUCT_NAME, productNameString);
            values.put(ProductTable.PRODUCT_PRICE, productPriceString);
            values.put(ProductTable.PRODUCT_QUANTITY, productQuantityString);
            values.put(ProductTable.PRODUCT_SUPPLIER_NAME, supplierName);
            values.put(ProductTable.PRODUCT_SUPPLIER_PHONE, productSupplierPhoneNumberString);

            Uri newUri = getContentResolver().insert(ProductTable.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.insert_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_successful),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{

            if (TextUtils.isEmpty(productNameString)) {
                Toast.makeText(this, getString(R.string.product_name_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(productPriceString)) {
                Toast.makeText(this, getString(R.string.price_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(productQuantityString)) {
                Toast.makeText(this, getString(R.string.quantity_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (supplierName == ProductTable.SUPPLIER_UNKNOWN) {
                Toast.makeText(this, getString(R.string.supplier_name_requires), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(productSupplierPhoneNumberString)) {
                Toast.makeText(this, getString(R.string.supplier_phone_requires), Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();

            values.put(ProductTable.PRODUCT_NAME, productNameString);
            values.put(ProductTable.PRODUCT_PRICE, productPriceString);
            values.put(ProductTable.PRODUCT_QUANTITY, productQuantityString);
            values.put(ProductTable.PRODUCT_SUPPLIER_NAME, supplierName);
            values.put(ProductTable.PRODUCT_SUPPLIER_PHONE, productSupplierPhoneNumberString);


            int rowsAffected = getContentResolver().update(currentProductUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.update_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.update_successful),
                        Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveProduct();
                return true;
            case android.R.id.home:
                if (!productHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!productHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
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
            int nameColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(ProductTable.PRODUCT_SUPPLIER_PHONE);

            String currentName = cursor.getString(nameColumnIndex);
            int currentPrice = cursor.getInt(priceColumnIndex);
            int currentQuantity = cursor.getInt(quantityColumnIndex);
            int currentSupplierName = cursor.getInt(supplierNameColumnIndex);
            int currentSupplierPhone = cursor.getInt(supplierPhoneColumnIndex);

            productNameEditText.setText(currentName);
            productPriceEditText.setText(Integer.toString(currentPrice));
            productQuantityEditText.setText(Integer.toString(currentQuantity));
            productSupplierPhoneNumberEditText.setText(Integer.toString(currentSupplierPhone));

            switch (currentSupplierName) {
                case ProductTable.SUPPLIER_AMAZON:
                    productSupplieNameSpinner.setSelection(1);
                    break;
                case ProductTable.SUPPLIER_JUMIA:
                    productSupplieNameSpinner.setSelection(2);
                    break;
                case ProductTable.SUPPLIER_SOUQ:
                    productSupplieNameSpinner.setSelection(3);
                    break;
                default:
                    productSupplieNameSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productNameEditText.setText("");
        productPriceEditText.setText("");
        productQuantityEditText.setText("");
        productSupplierPhoneNumberEditText.setText("");
        productSupplieNameSpinner.setSelection(0);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
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