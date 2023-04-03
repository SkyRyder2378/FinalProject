package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NewsData.class}, version=1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDataDAO ndDAO();
}
