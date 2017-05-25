package com.zer.gallery.model;

import java.util.ArrayList;

/**
 * Created by ngodi on 18/05/2017.
 */

public class DateImage {
    private String date;
    private ArrayList<Image> arrImage = new ArrayList<>();
    private ArrayList<String> arrPath = new ArrayList<>();
    public void addImage(Image image){
        arrImage.add(image);
    }
    public DateImage(String date, ArrayList<Image> arrImage) {
        this.date = date;
        this.arrImage = arrImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Image> getArrImage() {
        return arrImage;
    }
    public ArrayList<String> getArrPath(){
        arrPath.clear();
        for(int i = 0 ; i < arrImage.size() ; i++){
            arrPath.add(arrImage.get(i).getPath());
        }
        return arrPath;
    }
    public void setArrImage(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }
}
