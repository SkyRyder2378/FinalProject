package com.example.finalproject.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * This class extends ViewModel for storing mutable live data
 * @author Taeung Park
 * @version 1.0
 */
public class NewYorkTimesViewModel extends ViewModel {
    /**
     * A MutableLiveData object that holds an ArrayList of NewsData for titles
     */
    public MutableLiveData<ArrayList<NewsData>> titles = new MutableLiveData< >();
    /**
     * A MutableLiveData object that holds a single NewsData object for selected article
     */
    public MutableLiveData<NewsData> selectedArticle = new MutableLiveData< >();
}
