package com.example.finalproject;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaceKittenInfoDao {

    @Insert
    void insert(PlaceKittenInfo k);

    @Query("Select * from PlaceKittenInfo")
    List<PlaceKittenInfo> getAllPlaceKittenItems();

    @Delete
    void deletePlaceKittenItem(PlaceKittenInfo w);

}
