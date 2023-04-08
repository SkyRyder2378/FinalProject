package com.example.finalproject.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/** This class is the Database Access object for the rover image database
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
@Dao
public interface NasaPhotoInfoDAO {

    /** This method inserts a rover photo object into the database
     *
     * @param photo This is a rover photo object in input into the database
     */
    @Insert
    void insertImage(NasaPhotoInfo photo);

    /** This method retrieves all the objects from the database
     *
     * @return This returns an array of all the database objects
     */
    @Query("Select * from NasaPhotoInfo")
    List<NasaPhotoInfo> getAllImages();

    /** This method deletes an object from the database
     *
     * @param photo This is the object to be deleted from the database
     */
    @Delete
    void deleteImage(NasaPhotoInfo photo);

}
