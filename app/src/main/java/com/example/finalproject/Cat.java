package com.example.finalproject;

public class Cat {
    private int _id;
    private int width;
    private int height;
    private String url;
    private boolean favorite;
    private String date;

    public Cat(int _id, int width, int height, String url, boolean favorite, String date) {
        this._id = _id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.favorite = favorite;
        this.date = date;
    }

    public int getId() {
        return _id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDate() {
        return date;
    }
}

