package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproject.R;

/**
 * @author IQRAM
 * @version 1.0
 The PlaceKittenPage class represents an activity that displays a layout for placing a kitten image.
 */
public class PlaceKittenPage extends AppCompatActivity {

    /**

     Called when the activity is starting. This method sets the content view to the activity_place_kitten_page layout.
     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_kitten_page);
    }
}