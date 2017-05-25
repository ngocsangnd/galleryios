package com.zer.gallery.model;

import java.util.ArrayList;

/**
 * Created by ngodi on 16/05/2017.
 */

public class DateVideo {
    private String date;
    private ArrayList<Model_Video> arrModelVideo = new ArrayList<>();

    public DateVideo(String date, ArrayList<Model_Video> arrModelVideo) {
        this.date = date;
        this.arrModelVideo = arrModelVideo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Model_Video> getArrModelVideo() {
        return arrModelVideo;
    }

    public void setArrModelVideo(ArrayList<Model_Video> arrModelVideo) {
        this.arrModelVideo = arrModelVideo;
    }
}
