package com.example.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    protected int id;
    @ColumnInfo(name="headline")
    private String headline;
    @ColumnInfo(name="webUrl")
    private String webUrl;
    @ColumnInfo(name="pubDate")
    private String pubDate;

    public NewsData(){};

    public NewsData(String a, String w, String d){
        headline = a;
        webUrl = w;
        pubDate = d;
    }

    public String getHeadline() {
        return headline;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
