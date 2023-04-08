package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.example.finalproject.UI.NasaPage;
import com.example.finalproject.UI.NewYorkPage;
import com.example.finalproject.UI.PlaceKittenPage;
import com.example.finalproject.UI.WeatherStackPage;
import com.example.finalproject.databinding.ActivityMainBinding;

/**
 * This is the landing page of the projecct
 * @author Abdullah Sabbagh
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navigation();
    }
    void navigation(){
        binding.nasa.setOnClickListener(clk->{
            Intent NasaPage=new Intent(this,NasaPage.class);
            startActivity(NasaPage);
        });
        binding.newYork.setOnClickListener(clk->{
            Intent NewYorkPage=new Intent(this, NewYorkPage.class);
            startActivity(NewYorkPage);
        });
        binding.kitten.setOnClickListener(clk->{
            Intent PlaceKittenPage=new Intent(this,PlaceKittenPage.class);
            startActivity(PlaceKittenPage);
        });
        binding.weather.setOnClickListener(clk->{
            Intent WeatherStackPage=new Intent(this, WeatherStackPage.class);
            startActivity(WeatherStackPage);
        });
    }
}