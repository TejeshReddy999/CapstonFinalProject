package com.example.sunny.moviemagazine;

public class TvShows {

    private String Tumbnail;
    private String BackDrop;
    private String Title;
    private String Rating;
    private String Release;
    private String Description;
    private String Id;

    public TvShows(String tumbnail, String backDrop, String title, String rating, String release, String description, String id) {
        Tumbnail = tumbnail;
        BackDrop = backDrop;
        Title = title;
        Rating = rating;
        Release = release;
        Description = description;
        Id = id;
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
