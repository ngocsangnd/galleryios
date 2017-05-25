package com.zer.gallery.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.zer.gallery.R;
import com.zer.gallery.adapter.Adapter_VideoFolder;
import com.zer.gallery.model.Model_Video;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Anh Son on 17/05/2017.
 */

public class VideoFragment extends BaseFragment {
    private static final String TAG =   "VideoFolder" ;
    Adapter_VideoFolder obj_adapter;
    ArrayList<Model_Video> al_video = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private static final int REQUEST_PERMISSIONS = 100;
    @Override
    protected void initsView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        recyclerViewLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        fn_checkpermission();

    }

    @Override
    protected int getViews() {
        return R.layout.fragment_video;
    }
    private void fn_checkpermission(){
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_video();
        }
    }
    private String convertDate(String myDate){
        long millisecond = Long.parseLong(myDate);
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
        return dateString;
    }
    public void fn_video() {

        int int_position = 0;
        Uri uri , uri_in;
        Cursor cursor , cursor_in;
        int column_index_data, column_index_folder_name,column_id,thum , colum_date_video;
        String absolutePathOfImage = null , date = "";
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        uri_in = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,MediaStore.Video.Media._ID,MediaStore.Video.Thumbnails.DATA , MediaStore.Video.Media.DATE_TAKEN};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
        cursor_in  = getContext().getContentResolver().query(uri_in, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        colum_date_video = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
//            Log.e("Column", absolutePathOfImage);
//            Log.e("Folder", cursor.getString(column_index_folder_name));
//            Log.e("column_id", cursor.getString(column_id));
//            Log.e("thum", cursor.getString(thum));
            date = cursor.getString(colum_date_video);
            String mydate = convertDate(date);
//            Log.e(TAG , "date_video : "+mydate);
            Model_Video obj_model = new Model_Video();
            obj_model.setBoolean_selected(false);
            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));

            al_video.add(obj_model);
            Log.e(TAG , "date_video : "+mydate +"   "+ al_video.get(al_video.size() -1).getStr_path());


        }
        thum = cursor_in.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
        while (cursor_in.moveToNext()) {
            absolutePathOfImage = cursor_in.getString(column_index_data);
//            Log.e("Column", absolutePathOfImage);
//            Log.e("Folder", cursor_in.getString(column_index_folder_name));
//            Log.e("column_id", cursor_in.getString(column_id));
//            Log.e("thum", cursor.getString(thum));

            Model_Video obj_model = new Model_Video();
            obj_model.setBoolean_selected(false);
            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor_in.getString(thum));
            String date_in = cursor_in.getString(colum_date_video);
            String mydate = convertDate(date_in);
            al_video.add(obj_model);
            Log.e(TAG , "date_video : "+mydate +"   "+ al_video.get(al_video.size() -1).getStr_path());


        }

        obj_adapter = new Adapter_VideoFolder(getContext(),al_video,getActivity());
        recyclerView.setAdapter(obj_adapter);

    }
}
