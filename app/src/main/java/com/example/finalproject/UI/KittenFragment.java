package com.example.finalproject.UI;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.Data.Kitten;
import com.example.finalproject.R;
import com.example.finalproject.databinding.KittenDetailsBinding;


public class KittenFragment extends Fragment {



   Kitten selected;

    public KittenFragment(Kitten k) {

        selected = k;


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        return true;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        KittenDetailsBinding binding = KittenDetailsBinding.inflate(inflater);


        binding.getRoot().setBackgroundColor(Color.WHITE);
        binding.kittenDetailsId.setText("Id = " + selected.id);
        binding.kittenDetailsWidth.setText(getString(R.string.width)+" : " + selected.getWidth());
        binding.kittenDetailsHeight.setText(getString(R.string.height)+" : " + selected.getHeight());
        binding.kittenDetailsDate.setText(getString(R.string.date)+" : " + selected.getDate());
        binding.weatherDetailsIcon.setImageBitmap(BitmapFactory.decodeFile(selected.getImagePath()));


        return binding.getRoot();


    }
}