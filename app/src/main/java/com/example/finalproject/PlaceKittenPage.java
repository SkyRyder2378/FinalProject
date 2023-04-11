package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceKittenPage extends AppCompatActivity {

    private PlaceKittenInfoDao mDAO;
    private ImageView mImageView;
    private EditText mWidthEditText;
    private EditText mHeightEditText;
    private Button mSearchButton;
    //private ImageView mCatImageView;
    private RequestQueue mRequestQueue;

    private Button favourites;
    private View saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWidthEditText = findViewById(R.id.width);
        mHeightEditText = findViewById(R.id.height);
        mSearchButton = findViewById(R.id.searchButton);
        mImageView = findViewById(R.id.catView);
        saveButton = findViewById(R.id.favourite);
        favourites = findViewById(R.id.viewFavoritesButton);

        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent((PlaceKittenPage.this),PlaceKittenRecycler.class);
                startActivity(intent);
            }
        });
        mRequestQueue = Volley.newRequestQueue(this);
        mSearchButton.setOnClickListener(v -> searchCatImage());
        // Find the "View Favorites" button by ID

        saveButton.setOnClickListener(v -> onSave());



        PlaceKittenDatabase db = Room.databaseBuilder(getApplicationContext(), PlaceKittenDatabase.class, "PlaceKittenInfo").build();
        mDAO = db.cmDAO();
//        Executor thread = Executors.newSingleThreadExecutor();
//        thread.execute();
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

        String imageUrl = "https://placekitten.com/" + width + "/" + height;

        ImageRequest imageRequest = new ImageRequest(imageUrl,
                response -> {
                    Log.d("PlaceKittenPage", "Image loaded successfully");
                    mImageView.setImageDrawable(new BitmapDrawable(getResources(), response));
                },
                0,
                0,
                null,
                error -> {
                    Log.e("PlaceKittenPage", "Failed to load cat image: " + error.getMessage());
                    Toast.makeText(PlaceKittenPage.this, "Failed to load cat image", Toast.LENGTH_SHORT).show();
                });

        mRequestQueue.add(imageRequest);
    }

    public void onSave() {
        int width = Integer.parseInt(mWidthEditText.getText().toString());
        int height = Integer.parseInt(mHeightEditText.getText().toString());

        if (width <= 0 || height <= 0) {
            Toast.makeText(this, "Width and height must be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://placekitten.com/" + width + "/" + height;
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        PlaceKittenInfo item = new PlaceKittenInfo(width, height, url, timestamp);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                PlaceKittenDatabase db = Room.databaseBuilder(getApplicationContext(), PlaceKittenDatabase.class, "kitten").build();
                PlaceKittenInfoDao dao = db.cmDAO();
                dao.insert(item);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(PlaceKittenPage.this, "Image saved to favorites", Toast.LENGTH_SHORT).show();
            }


        }.execute();
    }

}
