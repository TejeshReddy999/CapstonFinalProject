package com.example.sunny.moviemagazine;

import android.database.Cursor;

/**
 * Created by sunny on 10-05-2018.
 */

public class Movie {
    private String Rating;
    private String Description;
    private String Title;
    private String Tumbnail;
    private String Release;
    private String BackDrop;
    private String Id;

    public Movie(String tumbnail, String backDrop, String title, String rating, String release, String description, String id) {
        Rating = rating;
        Description = description;
        Title = title;
        BackDrop = backDrop;
        Tumbnail = tumbnail;
        Release = release;
        Id = id;

    }

    public Movie(Cursor cursor) {
        this.Id = cursor.getString(0);
        this.Title = cursor.getString(1);
        this.Tumbnail = cursor.getString(2);
        this.Release = cursor.getString(4);
        this.Rating = cursor.getString(3);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTumbnail() {
        return Tumbnail;
    }

    public void setTumbnail(String tumbnail) {
        Tumbnail = tumbnail;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRelease() {
        return Release;
    }

    public void setRelease(String release) {
        Release = release;
    }

    public String getBackDrop() {
        return BackDrop;
    }

    public void setBackDrop(String backDrop) {
        BackDrop = backDrop;
    }
}
