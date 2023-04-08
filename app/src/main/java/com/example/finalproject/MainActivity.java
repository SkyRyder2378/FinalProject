package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.UI.NasaPage;
import com.example.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());


        Button nasaButton = variableBinding.nasaButton;

        nasaButton.setOnClickListener(clk -> {
            Intent nasaPage = new Intent(MainActivity.this, NasaPage.class);
            startActivity(nasaPage);
        });


    }
}