package com.example.finalproject.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.databinding.ActivityNewYorkPageBinding;

public class NewYorkPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityNewYorkPageBinding binding=ActivityNewYorkPageBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());
    }
}