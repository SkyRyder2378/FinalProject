package com.example.finalproject.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/** This class
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
public class NasaViewModel extends ViewModel {

    /** This is a mutable live data version of the nasa photos array */
    public MutableLiveData<ArrayList<NasaPhotoInfo>> nasaPhotos = new MutableLiveData< >();

    /** This is a mutable live data version of a nasa photo object */
    public MutableLiveData<NasaPhotoInfo> selectedPhoto = new MutableLiveData<>();

    public int position;
}
