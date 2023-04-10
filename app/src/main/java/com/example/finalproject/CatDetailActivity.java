package com.example.finalproject;



import static com.example.finalproject.MainActivity.CatDatabaseHelper.COLUMN_ID;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class CatDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CAT_ID = "com.example.finalproject.EXTRA_CAT_ID";

    private ImageView mImageView;
    private TextView mWidthTextView;
    private TextView mHeightTextView;
    private TextView mDateTextView;

    private MainActivity.CatDatabaseHelper mDbHelper;
    private Cat mCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mDbHelper = new MainActivity.CatDatabaseHelper(this);

        mImageView = findViewById(R.id.catImageView);
        mWidthTextView = findViewById(R.id.newWidth);
        mHeightTextView = findViewById(R.id.newHeight);
        mDateTextView = findViewById(R.id.newDate);

        int catId = getIntent().getIntExtra(EXTRA_CAT_ID, -1);
        if (catId == -1) {
            Toast.makeText(this, "Invalid cat ID", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            mCat = (Cat) mDbHelper.getAllCats(catId);
            if (mCat == null) {
                Toast.makeText(this, "Could not retrieve cat details", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                ImageRequest imageRequest = new ImageRequest(mCat.getUrl(),
                        response -> mImageView.setImageDrawable(new BitmapDrawable(getResources(), response)),
                        0,
                        0,
                        null,
                        error -> Toast.makeText(this, "Failed to load cat image", Toast.LENGTH_SHORT).show());
                requestQueue.add(imageRequest);

                mWidthTextView.setText("Width: " + mCat.getWidth());
                mHeightTextView.setText("Height: " + mCat.getHeight());
                mDateTextView.setText("Date: " + mCat.getDate());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }
}
