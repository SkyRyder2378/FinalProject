package com.example.finalproject.Data;
import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This class represent the database of WeatherStack
 * @author Abdullah Sabbagh
 * @version 01
 */
@Database(entities = {WeatherItem.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherItemDAO cmDAO();


}
