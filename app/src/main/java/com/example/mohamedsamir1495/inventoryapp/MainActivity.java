package com.example.mohamedsamir1495.inventoryapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper mDatabaseHelper;
    SQLiteDatabase mWriteableDB, mReadableDB;
    @BindView(R.id.insertToDB)
    Button insertButton;
    @BindView(R.id.retriveFromDB)
    Button retrieveButton;
    @BindView(R.id.infoTv)
    TextView retrievedDataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDatabaseHelper = new DBHelper(this);
        mWriteableDB = mDatabaseHelper.getWritableDatabase();
        mReadableDB = mDatabaseHelper.getReadableDatabase();
        insertButton.setOnClickListener(this);
        retrieveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retriveFromDB: {
                List<Product> retrievedData = mDatabaseHelper.retriveAll(mReadableDB);
                break;
            }
            case R.id.insertToDB: {
                Product toBeInsertedProductModel = new Product("Toshiba Screen", "10000", 10,
                        new Product.Supplier("Souq", "01013615170"));
                mDatabaseHelper.insert(mWriteableDB, toBeInsertedProductModel);
                break;
            }
        }
    }
}
