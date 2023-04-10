package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class CatCursorAdapter extends CursorAdapter {
    private LayoutInflater mInflater;

    public CatCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 1);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the list item layout
        return mInflater.inflate(R.layout.activity_favourites, parent, false);
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Populate the list item with the cat image details
        int width = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_WIDTH));
        int height = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_HEIGHT));
        String url = cursor.getString(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_URL));
        int favorite = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_FAVORITE));

        TextView sizeTextView = view.findViewById(R.id.newWidth);
        TextView sizeView = view.findViewById(R.id.newHeight);

        ImageView catImageView = view.findViewById(R.id.catImageView);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url,
                response -> catImageView.setImageDrawable(new BitmapDrawable(context.getResources(), response)),
                0,
                0,
                null,
                error -> Toast.makeText(context, "Failed to load cat image", Toast.LENGTH_SHORT).show());
        requestQueue.add(imageRequest);



//        CheckBox favoriteCheckBox = view.findViewById(R.id.viewFavoritesButton);
//        favoriteCheckBox.setChecked(favorite ==1);
    }
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        // get the CheckBox view from the view holder
//
//        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) view.getTag();
//        CheckBox checkBox = holder.checkBox;
//
//        // check if the view is null before calling its methods
//        if (checkBox != null) {
//            // set the checked state of the CheckBox based on the cursor data
//            int checkedValue = cursor.getInt(cursor.getColumnIndexOrThrow(MainActivity.CatDatabaseHelper.COLUMN_CHECKED));
//            boolean isChecked = (checkedValue != 0);
//            checkBox.setChecked(isChecked);
//        }

        // set other views in the list item as needed
        // ...
//    }


}
