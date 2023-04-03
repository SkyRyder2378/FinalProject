package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.finalproject.databinding.DetailsLayoutBinding;

public class NewsDetailsFragment extends Fragment {

    /** declaration of class object */
    NewsData selected;
    NewsDetailsFragment binding2;

    /**
     * One-arg constructor to load the class object
     * @param n class object
     */
    public NewsDetailsFragment(NewsData n){
        selected = n;
    }

    /**
     * Method to create and inflate fragment view and to set text to views on fragment
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.nameId.setText("Title : " + selected.getHeadline());
        binding.dateId.setText("Date : " + selected.getPubDate());
        binding.urlId.setText("URL : " + selected.getWebUrl());

        return binding.getRoot();
    }



}
