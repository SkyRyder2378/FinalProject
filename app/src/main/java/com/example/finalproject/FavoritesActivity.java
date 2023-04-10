package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoritesActivity extends AppCompatActivity {

    private ListView mListView;
    private CatCursorAdapter mAdapter;
    private MainActivity.CatDatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

//        mListView = findViewById(R.id.ListView);
//        mDbHelper = new MainActivity.CatDatabaseHelper(this);
//        mAdapter = new CatCursorAdapter(this, mDbHelper.getAllCats(catId));
//        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener((parentview, view, position, id) -> {
            Cursor cursor = (Cursor) mAdapter.getItem(position);
            int catId = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_ID));
            mListView = findViewById(R.id.ListView);
            mDbHelper = new MainActivity.CatDatabaseHelper(this);
            mAdapter = new CatCursorAdapter(this, mDbHelper.getAllCats(catId));
            mListView.setAdapter(mAdapter);
            Intent intent = new Intent(FavoritesActivity.this, CatDetailActivity.class);
            intent.putExtra(CatDetailActivity.EXTRA_CAT_ID, catId);
            startActivity(intent);
        });
    }

    private void viewFavorites() {
        int catId = 0;
        Cursor cursor = mDbHelper.getAllCats(catId);
        List<String> catDetails = new ArrayList<>();
        while (cursor.moveToNext()) {
            int width = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_WIDTH));
            int height = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_HEIGHT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_DATE));
            String details = String.format("Width: %d, Height: %d, Date: %s", width, height, date);
            catDetails.add(details);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catDetails);
        mListView.setAdapter(adapter);
    }
}






