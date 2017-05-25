package com.zer.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.zer.gallery.model.Image;
import com.zer.gallery.utils.Const;
import com.zer.gallery.utils.ManagerGalary;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ngodi on 18/05/2017.
 */

public class InforActivity extends AppCompatActivity{
    private TextView tvTimeImage , tvNameImage , tvSizeImage , tvPathImage , tvKbImage;
    private static final String TAG = "InforActivity";
    private String pathFile;
    private Image image ;
    private ArrayList<Image>arrImage = new ArrayList<>();
    private ManagerGalary managerGalary;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_image);
        Intent intent = getIntent();
        pathFile = intent.getStringExtra(Const.FILEPATH);
        Log.e(TAG , "pathFile : "+pathFile);
        managerGalary = new ManagerGalary(InforActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.infor_image));
        bindView();
        getData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        arrImage = managerGalary.getArrImage();
        for(int i = 0 ; i < arrImage.size() ; i++){
            if(pathFile.equals(arrImage.get(i).getPath())){
                image = arrImage.get(i);
                tvTimeImage.setText(image.getDate());
                String[] arr = image.getPath().split(File.separator);
                tvNameImage.setText(arr[arr.length - 1]);
                tvSizeImage.setText(image.getWidth()+" * "+image.getHeight());
                tvKbImage.setText(image.getSize()+" kb");
                tvPathImage.setText(image.getPath());
            }
        }
    }

    private void bindView() {
        tvTimeImage = (TextView) findViewById(R.id.tv_time_image);
        tvNameImage = (TextView) findViewById(R.id.tv_name_image);
        tvSizeImage = (TextView) findViewById(R.id.tv_size_image);
        tvKbImage = (TextView) findViewById(R.id.tv_kb_image);
        tvPathImage = (TextView) findViewById(R.id.tv_path_image);
    }
}
