package com.example.finalproject.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This interface for the databse
 * @author Abdullah Sabbagh
 * @version 01
 */
@Dao
public interface WeatherItemDAO {

    @Insert
    void insertMessage(WeatherItem w);

    @Query("Select * from WeatherItem")
    List<WeatherItem> getAllWeatherItems();

    @Delete
    void deleteWeatherItem(WeatherItem w);

}