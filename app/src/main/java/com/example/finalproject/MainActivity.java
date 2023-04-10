package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    public static ImageView mCatImageView;
    private EditText mWidthEditText;
    private EditText mHeightEditText;
    private Button mSearchButton;
    //private ImageView mCatImageView;
    private RequestQueue mRequestQueue;

    private View favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mWidthEditText = findViewById(R.id.width);
        mHeightEditText = findViewById(R.id.height);
        mSearchButton = findViewById(R.id.searchButton);
        mCatImageView = findViewById(R.id.catView);
        favButton = findViewById(R.id.favourite);

        mRequestQueue = Volley.newRequestQueue(this);


        mSearchButton.setOnClickListener(v -> searchCatImage());
        // Find the "View Favorites" button by ID
        Button viewFavorites = findViewById(R.id.viewFavoritesButton);
        viewFavorites.setOnClickListener(v -> viewFavorites());

        Button saveToFavoritesButton = findViewById(R.id.favourite);
        saveToFavoritesButton.setOnClickListener(v -> saveToDatabase());


    }


    private void searchCatImage() {
        String widthStr = mWidthEditText.getText().toString();
        String heightStr = mHeightEditText.getText().toString();

        if (widthStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter width and height", Toast.LENGTH_SHORT).show();
            return;
        }

        int width = Integer.parseInt(widthStr);
        int height = Integer.parseInt(heightStr);
        String url = "https://placekitten.com/" + width + "/" + height;

        mRequestQueue.add(new ImageRequest(url,
                response -> mCatImageView.setImageDrawable(new BitmapDrawable(getResources(), response)),
                0,
                0,
                null,
                error -> Toast.makeText(MainActivity.this, "Failed to load cat image", Toast.LENGTH_SHORT).show())
        );
//        favButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveFavoritePicture(url);
//            }
//        });

    }

    public static class CatDatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "kitten.db";
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME = "cat";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_WIDTH = "width";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_DATE = "date";


        private static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_WIDTH + " INTEGER, " +
                        COLUMN_HEIGHT + " INTEGER, " +
                        COLUMN_URL + " TEXT, " +
                        COLUMN_FAVORITE + " INTEGER, " +
                        COLUMN_DATE + " TEXT)";
        public static int catId;


        public CatDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Not implemented in this example
        }


        public long insertCat(int width, int height, String url, int favorite, String date) {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_WIDTH, width);
            values.put(COLUMN_HEIGHT, height);
            values.put(COLUMN_URL, url);
            values.put(COLUMN_FAVORITE, favorite);
            values.put(COLUMN_DATE, getCurrentDateTime());
            return db.insert(TABLE_NAME, null, values);
        }

        private String getCurrentDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }


        public Cursor getAllCats(int catId) {
            SQLiteDatabase db = getReadableDatabase();
            return db.query(TABLE_NAME, null, null, null, null, null, null);
        }

    }

    private void viewFavorites() {
        Intent intent = new Intent(this, ViewFavoritesActivity.class);
        startActivity(intent);
    }

    public List<Cat> getAllCatsFromDatabase() {
        List<Cat> cats = new ArrayList<>();
        CatDatabaseHelper mDbHelper = null;
        Cursor cursor = null;
        int catId = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_ID));
        cursor = mDbHelper.getAllCats(catId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_ID));
                int width = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_WIDTH));
                int height = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_HEIGHT));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_URL));
                boolean favorite = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_FAVORITE)) == 1;
                String date = cursor.getString(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_DATE));
                Cat cat = new Cat(_id, width, height, url, favorite, date);
                cats.add(cat);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return cats;
    }

    private void saveToDatabase() {
        String widthStr = mWidthEditText.getText().toString();
        String heightStr = mHeightEditText.getText().toString();
        String date = DateFormat.getDateTimeInstance().format(new Date());
        CatDatabaseHelper dbHelper = null;

//        long id = dbHelper.insertCat(width, height, url, 1, date);

        if (widthStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter width and height", Toast.LENGTH_SHORT).show();
            return;
        }


        int width = Integer.parseInt(widthStr);
        int height = Integer.parseInt(heightStr);
        String url = "https://placekitten.com/" + width + "/" + height;

        dbHelper = new CatDatabaseHelper(this);
        long id = dbHelper.insertCat(width, height, url, 1, date);
        if (id == -1) {
            Toast.makeText(this, "Failed to save cat image", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cat image saved to favorites", Toast.LENGTH_SHORT).show();
        }

    }

}


