package com.example.ssboy14.earthquake;

import java.util.Date;

public class EarthQuake {

    private String mag;
    private String location;
    private String date;
    private String url;

    public EarthQuake(String mag, String location, String date, String url) {
        this.mag = mag;
        this.location = location;
        this.date = date;
        this.url = url;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
