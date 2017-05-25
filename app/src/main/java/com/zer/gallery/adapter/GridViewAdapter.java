package com.zer.gallery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {


    private ArrayList<String> filePath;
    private Context context;

    public GridViewAdapter(Context context, ArrayList<String> filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    public void setFilePath(ArrayList<String> filePath) {
        this.filePath = filePath;
    }

    @Override
    public int getCount() {
        return this.filePath.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filePath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        } else {
            imageView = (ImageView) convertView;
        }
//        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(new File(filePath.get(position))).resize(200,200).centerInside()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Glide.with(context).load(filePath.get(position)).thumbnail(0.3f).into(imageView);

                    }
                });

        return imageView;
    }

}
