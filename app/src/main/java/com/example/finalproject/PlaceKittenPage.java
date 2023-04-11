package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceKittenPage extends AppCompatActivity {

    private PlaceKittenInfoDao mDAO;
    private ImageView mImageView;
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
        mImageView = findViewById(R.id.catView);
        favButton = findViewById(R.id.favourite);

        mRequestQueue = Volley.newRequestQueue(this);
        mSearchButton.setOnClickListener(v -> searchCatImage());
        // Find the "View Favorites" button by ID

        favButton.setOnClickListener(v -> onSave());

        Button viewFavorites = findViewById(R.id.viewFavoritesButton);
        viewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceKittenPage.this, PlaceKittenRecycler.class);
                startActivity(intent);
            }
        });




        PlaceKittenDatabase db = Room.databaseBuilder(getApplicationContext(), PlaceKittenDatabase.class, "kitten").build();
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
        String url = "https://placekitten.com/" + width + "/" + height;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String imageUrl = response.getString("file");
                        String fullUrl = "https://placekitten.com" + imageUrl.substring(1);
                        mRequestQueue.add(new ImageRequest(fullUrl,
                                imageResponse -> {
                                    Log.d("PlaceKittenPage", "Image loaded successfully");
                                    mImageView.setImageDrawable(new BitmapDrawable(getResources(), imageResponse));
                                },
                                0,
                                0,
                                null,
                                error -> {
                                    Log.e("PlaceKittenPage", "Failed to load cat image: " + error.getMessage());
                                    Toast.makeText(PlaceKittenPage.this, "Failed to load cat image", Toast.LENGTH_SHORT).show();
                                }));
                    } catch (JSONException e) {
                        Log.e("PlaceKittenPage", "Failed to parse JSON response", e);
                        Toast.makeText(PlaceKittenPage.this, "Failed to load cat image", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("PlaceKittenPage", "Failed to load JSON response: " + error.getMessage());
                    Toast.makeText(PlaceKittenPage.this, "Failed to load cat image", Toast.LENGTH_SHORT).show();
                });

        mRequestQueue.add(request);
    }
    public void onSave() {
       // Bitmap image = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        int width = Integer.parseInt(mWidthEditText.getText().toString());
        int height = Integer.parseInt(mHeightEditText.getText().toString());
        String url = "https://placekitten.com/" + width + "/" + height;
        long timestamp = PlaceKittenInfo.getDate();
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        PlaceKittenInfo item = new PlaceKittenInfo(width, height, url, timestamp);
        mDAO.insert(item);

        Toast.makeText(this, "Image saved to favorites", Toast.LENGTH_SHORT).show();
    }





}