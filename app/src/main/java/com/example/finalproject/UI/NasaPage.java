package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.Data.NasaPhotoInfo;
import com.example.finalproject.Data.NasaPhotoInfoDAO;
import com.example.finalproject.Data.NasaViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.ActivityNasaPageBinding;

import java.util.ArrayList;

/** This class accesses the saved photos class and the nasa activity class
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
public class NasaPage extends AppCompatActivity {

    /** This hold the nasa page activity */
    private ActivityNasaPageBinding binding;

    /** This method creates and controls the nasa page
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNasaPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button searchButton = binding.nasaSearchPhotos;

        searchButton.setOnClickListener(clk -> {
            Intent nasaPage = new Intent(NasaPage.this, NasaActivity.class);
            startActivity(nasaPage);
        });

        Button savesButton = binding.nasaSavedPhotos;

        savesButton.setOnClickListener(clk -> {
            Intent savesPage = new Intent(NasaPage.this, NasaSavedPhotos.class);
            startActivity(savesPage);
        });

    }
}