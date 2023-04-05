package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={NasaPhotoInfo.class}, version = 1)
public abstract class NasaPhotoDatabase extends RoomDatabase{

    public abstract NasaPhotoInfoDAO nPDAO();
}
