package com.example.finalproject.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
/**
 * This is the view model of the Weatherstack
 * @author Abdullah Sabbagh
 * @version 01
 */
public class WeatherViewModel extends ViewModel {
    public MutableLiveData<ArrayList<WeatherItem>> weatherItems = new MutableLiveData<>();
    public MutableLiveData<WeatherItem> selectedWeatherItem = new MutableLiveData< >();





}