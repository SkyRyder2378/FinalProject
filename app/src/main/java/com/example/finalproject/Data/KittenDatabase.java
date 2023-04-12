package com.example.finalproject.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author IQRAM
 * @version 1.0

 Room database class for Kitten objects.

 This class represents the database for storing Kitten objects using the Room persistence library.
 */
@Database(entities = {Kitten.class}, version = 1)
public abstract class KittenDatabase extends RoomDatabase {

    /**

     Returns a KittenDAO object for accessing the database.
     @return A KittenDAO object for accessing the database.
     */
    public abstract KittenDAO cmDAO();
}