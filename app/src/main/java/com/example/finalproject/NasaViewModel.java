package com.example.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NasaViewModel extends ViewModel {

    public MutableLiveData<ArrayList<NasaPhotoInfo>> nasaPhotos = new MutableLiveData< >();

    public MutableLiveData<NasaPhotoInfo> selectedPhoto = new MutableLiveData<>();
}
