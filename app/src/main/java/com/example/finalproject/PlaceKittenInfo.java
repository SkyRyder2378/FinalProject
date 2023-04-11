package com.example.finalproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
import java.util.Date;

@Entity(tableName = "PlaceKittenInfo")
public class PlaceKittenInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="height")
    public int height;

    @ColumnInfo(name="width")
    public int width;

    @ColumnInfo(name="webURL")
    public String webURL;

    @ColumnInfo(name="date")
    public static long date;




    public PlaceKittenInfo(int width, int height, String url, long date) {
        this.width = width;
        this.height = height;
        this.webURL = url;

        this.date = date;
    }

    public PlaceKittenInfo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return webURL;
    }

    public void setUrl(String url) {
        this.webURL = url;
    }


    public static long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
