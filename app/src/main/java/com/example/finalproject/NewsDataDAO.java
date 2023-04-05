package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Interface class to interact with the the database
 */
@Dao
public interface NewsDataDAO {
    /**
     * To insert object into database
     * @param nd class object
     * @return id for inserted row
     */
    @Insert
    public long insertList(NewsData nd);

    /**
     * To return all lists in database
     * @return all lists in database
     */
    @Query("select * from NewsData")
    public List<NewsData> getAllList();

    /**
     * To delete list in database
     * @param nd object to delete
     */
    @Delete
    void deleteList(NewsData nd);
}
