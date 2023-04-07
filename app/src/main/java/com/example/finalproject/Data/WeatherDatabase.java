package com.example.finalproject.Data;
import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {WeatherItem.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherItemDAO cmDAO();


}
