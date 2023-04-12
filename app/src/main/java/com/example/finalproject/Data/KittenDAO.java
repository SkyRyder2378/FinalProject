package com.example.finalproject.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * @author IQRAM
 * @version 1.0
 Data Access Object for Kitten objects.

 This interface provides methods for inserting, retrieving, and deleting Kitten objects from a Room database.
 */
@Dao
public interface KittenDAO {

    /**

     Inserts a new Kitten object into the database.
     @param k The Kitten object to be inserted.
     */
    @Insert
    void insertKittenItem(Kitten k);
    /**

     Retrieves all Kitten objects from the database.
     @return A list of all Kitten objects in the database.
     */
    @Query("SELECT * FROM Kitten")
    List<Kitten> getAllKittenItem();
    /**

     Deletes a Kitten object from the database.
     @param k The Kitten object to be deleted.
     */
    @Delete
    void deleteKittenItem(Kitten k);
}