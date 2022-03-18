package com.example.sunny.moviemagazine;

/**
 * Created by sunny on 18-05-2018.
 */

public class Review {
    private String rauthor;
    private String rcontent;
    private String rid;
    private String rurl;


    public Review(String rauthor, String rcontent, String rid, String rurl) {
        this.rauthor = rauthor;
        this.rcontent = rcontent;
        this.rid = rid;
        this.rurl = rurl;
    }

    public Review() {
    }

    public String getRauthor() {
        //Log.i("Auther",getRauthor().toString());
        return rauthor;
    }

    public void setRauthor(String rauthor) {
        this.rauthor = rauthor;
    }

    public String getRcontent() {
        //Log.i("Content",getRcontent().toString());
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRurl() {
        return rurl;
    }

    public void setRurl(String rurl) {
        this.rurl = rurl;
    }

}
