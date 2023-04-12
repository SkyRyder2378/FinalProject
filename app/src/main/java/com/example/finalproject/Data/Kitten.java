package com.example.finalproject.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * @author IQRAM
 * @version 1.0

 Represents a Kitten object.

 This class is used to create and store information about a kitten, including its id, width, height, date, and image path.
 */
@Entity
public class Kitten {

    /**

     The unique id of the kitten.
     */
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate=true)
    public int id;
    /**

     The width of the kitten image.
     */
    @ColumnInfo(name="width")
    protected String width;
    /**

     The height of the kitten image.
     */
    @ColumnInfo(name="height")
    protected String height;
    /**

     The date when the kitten information was stored.
     */
    @ColumnInfo(name="date")
    protected String date;
    /**

     The path to the image file of the kitten.
     */
    @ColumnInfo(name="imagePath")
    protected String imagePath;
    /**

     Constructs a new Kitten object with the specified parameters.
     @param width The width of the kitten image.
     @param height The height of the kitten image.
     @param date The date when the kitten information was stored.
     @param imagePath The path to the image file of the kitten.
     */
    public Kitten(String width, String height, String date, String imagePath) {
        this.width = width;
        this.height = height;
        this.date = date;
        this.imagePath = imagePath;
    }
    /**

     Returns the id of the kitten.
     @return The id of the kitten.
     */
    public int getId() {
        return id;
    }
    /**

     Returns the width of the kitten image.
     @return The width of the kitten image.
     */
    public String getWidth() {
        return width;
    }
    /**

     Returns the height of the kitten image.
     @return The height of the kitten image.
     */
    public String getHeight() {
        return height;
    }
    /**

     Returns the date when the kitten information was stored.
     @return The date when the kitten information was stored.
     */
    public String getDate() {
        return date;
    }
    /**

     Returns the path to the image file of the kitten.
     @return The path to the image file of the kitten.
     */
    public String getImagePath() {
        return imagePath;
    }
}


