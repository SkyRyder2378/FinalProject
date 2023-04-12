package com.example.finalproject.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;

/** This class is the object that can be inserted into the database
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
@Entity
public class NasaPhotoInfo {

    /** This holds the id of the object for the database */
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    /** This holds the image of the object for the database */
    @ColumnInfo(name="image")
    public byte[] roverImage;

    /** This holds the rover name of the object for the database */
    @ColumnInfo(name="roverName")
    public String roverName;

    /** This holds the camera name of the object for the database */
    @ColumnInfo(name="cameraName")
    public String cameraName;

    /** This holds the image URL of the object for the database */
    @ColumnInfo(name="imageURL")
    public String imageURL;

    /** This constructor initiates all the variables of the object
     *
     * @param rImage this fills the roverImage variable
     * @param rName this fills the roverName variable
     * @param cName this fills the cameraName variable
     * @param iURL this fills the imageURL variable
     */
    public NasaPhotoInfo (Bitmap rImage, String rName, String cName, String iURL){

        roverImage = bitmapToByteArray(rImage);
        roverName = rName;
        cameraName = cName;
        imageURL = iURL;
    }

    public NasaPhotoInfo (String rName, String cName, String iURL){

        roverName = rName;
        cameraName = cName;
        imageURL = iURL;
    }

    /** This constructed allows simple initiation of the class
     *
     */
    public NasaPhotoInfo(){}

    /** This method allows retrieval of the rover image
     *
     * @return This returns a Bitmap of the byte array image
     */
    public Bitmap getRoverImage(){
        Bitmap img = byteArrayToBitmap(roverImage);
        return img;
    }

    /** This method allows retrieval of the rover name
     *
     * @return This returns the rover name
     */
    public String getRoverName(){
        return roverName;
    }

    /** This method allows retrieval of the rover name
     *
     * @return This returns the camera name
     */
    public String getCameraName(){
        return cameraName;
    }

    /** This method allows retrieval of the image URL
     *
     * @return This returns the image URL
     */
    public String getImageURL(){
        return imageURL;
    }

    /** This method transforms a byte array into a Bitmap
     *
     * @param img This is a byte array of the rover image
     * @return This returns a bitmap version of the byte array rover image
     */
    public Bitmap byteArrayToBitmap(byte[] img){
        if(img!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            return bitmap;
        }
        else{
            return null;
        }
    }

    /** This method transforms a Bitmap into a byte array
     *
     * @param img This is a Bitmap of the rover image
     * @return This returns a byte array version of the bitmap rover image
     */
    public byte[] bitmapToByteArray(Bitmap img){
        if(img != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            return outputStream.toByteArray();
        }
        else{
            return null;
        }

    }

}
