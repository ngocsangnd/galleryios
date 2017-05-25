package com.zer.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ngodi on 17/05/2017.
 */

public class Image implements Parcelable {
    private String date;
    private String bucket;
    private String path;
    private String width ;
    private String height;
    private String size;
//    public Image(String date, String bucket, String path) {
//        this.date = date;
//        this.bucket = bucket;
//        this.path = path;
//    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Image(String date, String bucket, String path, String width, String height, String size) {
        this.date = date;
        this.bucket = bucket;
        this.path = path;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    protected Image(Parcel in) {
        date = in.readString();
        bucket = in.readString();
        path = in.readString();
    }
    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(bucket);
        dest.writeString(path);
    }
}
