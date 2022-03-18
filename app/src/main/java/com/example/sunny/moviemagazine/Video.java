package com.example.sunny.moviemagazine;

/**
 * Created by sunny on 16-05-2018.
 */

public class Video {
    private String vid;
    private String vkey;
    private String vname;
    private String vsite;
    private String vsize;
    private String vtype;
    private String iso_639_1;
    private String iso_3166_1;

    public Video(String vid, String vkey, String vname, String vsite, String vsize, String vtype, String iso_639_1, String iso_3166_1) {
        this.vid = vid;
        this.vkey = vkey;
        this.vname = vname;
        this.vsite = vsite;
        this.vsize = vsize;
        this.vtype = vtype;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
    }

    public Video(String vtype) {
        this.vtype = vtype;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVsite() {
        return vsite;
    }

    public void setVsite(String vsite) {
        this.vsite = vsite;
    }

    public String getVsize() {
        return vsize;
    }

    public void setVsize(String vsize) {
        this.vsize = vsize;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }
}
