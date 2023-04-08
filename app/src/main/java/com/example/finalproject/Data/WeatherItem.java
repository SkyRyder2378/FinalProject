package com.example.finalproject.Data;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
/**
 * This class contains the elements of the weatherstack
 * @author Abdullah Sabbagh
 * @version 01
 */
public class WeatherItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate=true)
    public int id;
    @ColumnInfo(name="name")
    protected String name;
    @ColumnInfo(name="locateTime")
    protected String locateTime;
    @ColumnInfo(name="temperature")
    protected String temperature;
    @ColumnInfo(name="icon")
    protected String icon;
    @ColumnInfo(name="description")
    protected String description;

    /**
     * Method: getPathName
     * @return PathName
     */
    public String getPathName() {
        return pathName;
    }
    @ColumnInfo(name="humidity")
    protected String humidity;
    @ColumnInfo(name="pathName")
    protected String pathName;

    /**
     * This is a constructor
     * @param name
     * @param locateTime
     * @param temperature
     * @param icon
     * @param description
     * @param humidity
     * @param pathName
     */
    public WeatherItem(String name, String locateTime, String temperature, String icon, String description, String humidity, String pathName) {
        this.name = name;
        this.locateTime = locateTime;
        this.temperature = temperature;
        this.icon = icon;
        this.description = description;
        this.humidity = humidity;
        this.pathName = pathName;
    }

    /**
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Local time
     */
    public String getLocateTime() {
        return locateTime;
    }

    /**
     * @return temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * @return Icon.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return description of the weather
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Humidity
     */
    public String getHumidity() {
        return humidity;
    }
}