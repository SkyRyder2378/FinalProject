package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDataDAO {

    @Insert
    public long insertList(NewsData nd);

    @Query("select * from NewsData")
    public List<NewsData> getAllList();

    @Delete
    void deleteList(NewsData nd);
}
