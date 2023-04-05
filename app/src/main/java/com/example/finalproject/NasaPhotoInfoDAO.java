package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NasaPhotoInfoDAO {

    @Insert
    void insertImage(NasaPhotoInfo photo);

    @Query("Select * from NasaPhotoInfo")
    List<NasaPhotoInfo> getAllImages();

    @Delete
    void deleteImage(NasaPhotoInfo photo);

}
