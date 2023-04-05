package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.finalproject.databinding.NasaPhotoInformationBinding;

public class NasaPhotoDetailFragment extends Fragment {

    NasaPhotoInfo selected;

    public NasaPhotoDetailFragment(){}

    public NasaPhotoDetailFragment(NasaPhotoInfo photo){selected = photo;}

    public void displayPhoto(NasaPhotoInfo photo){selected = photo;}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        NasaPhotoInformationBinding binding = NasaPhotoInformationBinding.inflate(getLayoutInflater());

        binding.roverImageFull.setImageBitmap(selected.getRoverImage());
        binding.roverNameD.setText(selected.getCameraName());
        binding.imageURL.setText(selected.getImageURL());

        return binding.getRoot();
    }



}
