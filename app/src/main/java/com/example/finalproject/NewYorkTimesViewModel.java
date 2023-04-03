package com.example.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NewYorkTimesViewModel extends ViewModel {
    public MutableLiveData<ArrayList<NewsData>> titles = new MutableLiveData< >();
    public MutableLiveData<NewsData> selectedArticle = new MutableLiveData< >();
}
