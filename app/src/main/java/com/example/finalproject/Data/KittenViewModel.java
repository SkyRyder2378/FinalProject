package com.example.finalproject.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * @author IQRAM
 * @version 1.0

 ViewModel class for Kitten objects.

 This class is used to hold and manage UI-related data for Kitten objects.
 */
public class KittenViewModel extends ViewModel {

    /**

     MutableLiveData object containing an ArrayList of Kitten objects.
     This object is used to hold a list of Kitten objects retrieved from the database and update the UI accordingly.
     */
    public MutableLiveData<ArrayList<Kitten>> kittenItems = new MutableLiveData<>();
    /**

     MutableLiveData object containing the currently selected Kitten object.
     This object is used to hold a single Kitten object selected by the user and update the UI accordingly.
     */
    public MutableLiveData<Kitten> selectedKittenItem = new MutableLiveData<>();
}