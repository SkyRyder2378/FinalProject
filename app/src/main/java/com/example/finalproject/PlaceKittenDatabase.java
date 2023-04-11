package com.example.finalproject;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlaceKittenInfo.class}, version = 1)
public abstract class PlaceKittenDatabase extends RoomDatabase {
    public abstract PlaceKittenInfoDao cmDAO();


}
