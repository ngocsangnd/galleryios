package com.zer.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import com.zer.gallery.adapter.AdapterDateImage;
import com.zer.gallery.model.DateImage;
import com.zer.gallery.model.Image;
import com.zer.gallery.utils.Const;

import java.util.ArrayList;

/**
 * Created by ngodi on 18/05/2017.
 */

public class AlbumActivity extends AppCompatActivity {
    private AdapterDateImage adapterDateImage;
    private ArrayList<DateImage> arrDateImage = new ArrayList<>();
    private ArrayList<String> mArrDate = new ArrayList<>();
    private static final String TAG = "AlbumActivity";
    private ArrayList<Image> arrImage = new ArrayList<>();
    private String title;
    private ListView lvDateImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Intent intent = getIntent();
        arrImage = (ArrayList<Image>) intent.getSerializableExtra(Const.IAMGES);
        title = intent.getStringExtra(Const.TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(title);
        Log.e(TAG , "arr size : "+arrImage.size());
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        lvDateImage = (ListView) findViewById(R.id.lv_date_image);
        adapterDateImage = new AdapterDateImage(AlbumActivity.this , android.R.layout.simple_list_item_1 , arrDateImage);
        lvDateImage.setAdapter(adapterDateImage);
        getArrDateImage();
    }

    private void getArrDateImage() {
        for(int i = 0 ; i < arrImage.size() ; i ++){
            getArrDate(arrImage.get(i).getDate() , mArrDate);
        }
        for(int i = 0 ; i < mArrDate.size() ; i++){
            Log.e(TAG , "date_album : "+mArrDate.get(i));
        }
        for(int i = 0 ; i < mArrDate.size() ; i++){
            DateImage dateImage = new DateImage(mArrDate.get(i).toString() , new ArrayList<Image>());
            for(int j = 0 ; j < arrImage.size() ; j ++){
                if(dateImage.getDate().equals(arrImage.get(j).getDate())){
                    dateImage.addImage(arrImage.get(j));
                }
            }
            arrDateImage.add(dateImage);
        }
        for(int i = 0 ; i < arrDateImage.size() ; i++){
            Log.e(TAG , "date : "+arrDateImage.get(i).getDate()+"      size : "+arrDateImage.get(i).getArrImage().size());
        }
        adapterDateImage.notifyDataSetChanged();
    }

    public void getArrDate(String date , ArrayList<String> arrDate){
        boolean isAd = false;
        for(int i = 0 ; i < arrDate.size() ; i++){
            if(date.equals(arrDate.get(i).toString())){
                isAd = true;
            }
        }
        if(!isAd){
            mArrDate.add(date);
        }
    }
}
