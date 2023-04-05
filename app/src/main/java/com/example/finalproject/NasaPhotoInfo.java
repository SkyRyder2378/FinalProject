package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;

@Entity
public class NasaPhotoInfo {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="image")
    public byte[] roverImage;

    @ColumnInfo(name="roverName")
    public String roverName;

    @ColumnInfo(name="cameraName")
    public String cameraName;

    @ColumnInfo(name="imageURL")
    public String imageURL;

    public NasaPhotoInfo (Bitmap rImage, String rName, String cName, String iURL){

        roverImage = bitmapToByteArray(rImage);
        roverName = rName;
        cameraName = cName;
        imageURL = iURL;
    }

    public NasaPhotoInfo(){}

    public Bitmap getRoverImage(){
        Bitmap img = byteArrayToBitmap(roverImage);
        return img;
    }

    public String getRoverName(){
        return cameraName;
    }

    public String getCameraName(){
        return cameraName;
    }

    public String getImageURL(){
        return imageURL;
    }

    public Bitmap byteArrayToBitmap(byte[] img){
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }

    public byte[] bitmapToByteArray(Bitmap img){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

}
