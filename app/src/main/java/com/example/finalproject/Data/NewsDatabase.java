package com.example.finalproject.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * An abstract class for NewsDatabase
 * @author Taeung Park
 * @version 1.0
 */
@Database(entities = {NewsData.class}, version=1)
public abstract class NewsDatabase extends RoomDatabase {
    /**
     * Abstract method to return a NewsDataDAO object
     * @return NewsDataDAO object
     */
    public abstract NewsDataDAO ndDAO();
}
