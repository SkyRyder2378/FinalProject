package com.example.finalproject;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class ViewFavoritesActivity extends AppCompatActivity {

    private ListView mlistView;
    private CatAdapter mCatAdapter;
    private CatCursorAdapter mAdapter;
    private MainActivity.CatDatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mlistView = findViewById(R.id.ListView);

        MainActivity.CatDatabaseHelper dbHelper = new MainActivity.CatDatabaseHelper(this);
        Cursor cursor = dbHelper.getAllCats(MainActivity.CatDatabaseHelper.catId); //replace catId with the actual cat ID value
        mAdapter = new CatCursorAdapter(this, cursor);
        mlistView.setAdapter(mAdapter);


    }

    private List<Cat> getAllCatsFromDatabase() {
        Cursor cursor = null;
        String date = cursor.getString(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_DATE));

        List<Cat> cats = new ArrayList<>();
        int catId = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_ID));
        cursor = mDbHelper.getAllCats(catId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_ID));
                int width = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_WIDTH));
                int height = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_HEIGHT));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_URL));
                boolean favorite = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_FAVORITE)) == 1;
                Cat cat = new Cat(_id, width, height, url, favorite, date);
                cats.add(cat);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return cats;
    }

    private class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

        private List<Cat> mCats;

        public CatAdapter(List<Cat> cats) {
            mCats = cats;
        }

        @NonNull
        @Override
        public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_favourites, parent, false);
            return new CatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
            Cat cat = mCats.get(position);
            holder.bind(cat);
        }

        @Override
        public int getItemCount() {
            return mCats.size();
        }

        private class CatViewHolder extends RecyclerView.ViewHolder {

            private ImageView mCatImageView;

            public CatViewHolder(@NonNull View itemView) {
                super(itemView);
                mCatImageView = itemView.findViewById(R.id.catImageView);
            }

            public void bind(Cat cat) {
                // Load the cat image using Volley
                ImageRequest imageRequest = new ImageRequest(cat.getUrl(), response -> {
                    Drawable drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(response, cat.getWidth(), cat.getHeight(), false));
                    mCatImageView.setImageDrawable(drawable);
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, error -> {
                    // Handle error
                });
                Volley.newRequestQueue(itemView.getContext()).add(imageRequest);
            }

        }
    }
}
