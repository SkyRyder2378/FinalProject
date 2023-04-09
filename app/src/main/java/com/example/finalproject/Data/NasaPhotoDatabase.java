package com.example.finalproject.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/** This class creates the database
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
@Database(entities={NasaPhotoInfo.class}, version = 2)
public abstract class NasaPhotoDatabase extends RoomDatabase{

    /** This method gives access to the database
     *
     * @return the nasaPhotoInfoDAO
     */
    public abstract NasaPhotoInfoDAO nPDAO();
}
