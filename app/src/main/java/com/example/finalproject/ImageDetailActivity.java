package com.example.finalproject;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mWidthTextView;
    private TextView mHeightTextView;
    private TextView mDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mImageView = findViewById(R.id.catImageView);
        mWidthTextView = findViewById(R.id.newWidth);
        mHeightTextView = findViewById(R.id.newHeight);
        mDateTextView = findViewById(R.id.newDate);

        // Get the image details from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bitmap image = extras.getParcelable("image");
            int width = extras.getInt("width");
            int height = extras.getInt("height");
            String date = extras.getString("date");

            // Display the image details
            mImageView.setImageBitmap(image);
            mWidthTextView.setText(String.format("Width: %d", width));
            mHeightTextView.setText(String.format("Height: %d", height));
            mDateTextView.setText(date);
        }
    }
}
