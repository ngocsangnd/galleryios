package com.zer.gallery;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mz.A;
import com.mz.ZAndroidSystemDK;
import com.zer.gallery.adapter.GlaryFragmentAdapter;
import com.zer.gallery.fragment.AlbumFragment;
import com.zer.gallery.fragment.ImageFragment;
import com.zer.gallery.fragment.VideoFragment;
import com.zer.gallery.model.Image;
import com.zer.gallery.utils.LoadFiles;
import com.zer.gallery.utils.ManagerGalary;
import com.zer.gallery.utils.OpenFileDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity" ;
    private ArrayList<Image> arrImagesAlbum;
    private ArrayList<String> imagePaths;
    private LoadFiles loadFiles = new LoadFiles(this);
    private ManagerGalary managerGalary;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GlaryFragmentAdapter glaryFragmentAdapter;
    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    private AlbumFragment albumFragment;


    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,};
    private static final int REQUEST_CODE_PERMISSION = 2;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static int checkPermission(String[] permissions, Context mContext) {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissions) {
            permissionCheck += ContextCompat.checkSelfPermission(mContext, permission);
        }
        return permissionCheck;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermissions(MainActivity.this , PERMISSIONS)) {
            requestPermissions(PERMISSIONS, REQUEST_CODE_PERMISSION);
        }
        else{
            initFragment();
        }
        ZAndroidSystemDK.init(this);
        A.f(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
            case 2:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    ZAndroidSystemDK.init(this);

                    A.f(this);
                    initFragment();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Quyền truy cập")
                            .setMessage("Bạn cần cấp quyền truy cập media để ứng dụng có thể hoạt động!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(PERMISSIONS, REQUEST_CODE_PERMISSION);
                                    }
                                }
                            })
                            .setCancelable(false)
                            .setNegativeButton("THOÁT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).create().show();
                }
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
//        initFragment();
        Log.e(TAG , "onresume !");
    }
    private void initFragment(){
        managerGalary=new ManagerGalary(this);
        imagePaths = managerGalary.getImagePaths();
        arrImagesAlbum = new ArrayList<>();
        arrImagesAlbum  = managerGalary.getArrImage();
        imageFragment=new ImageFragment();
        videoFragment=new VideoFragment();
        albumFragment=new AlbumFragment();
        imageFragment.setImagePaths(imagePaths);
        albumFragment.setImagesAlbum(arrImagesAlbum);
        glaryFragmentAdapter=new GlaryFragmentAdapter(getSupportFragmentManager());
        glaryFragmentAdapter.setImageFragment(imageFragment);
        glaryFragmentAdapter.setAlbumFragment(albumFragment);
        glaryFragmentAdapter.setVideoFragment(videoFragment);

        viewPager= (ViewPager) findViewById(R.id.viewpager_galary);
        tabLayout=(TabLayout)findViewById(R.id.tablayout_galary);

        viewPager.setAdapter(glaryFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.images);
        tabLayout.getTabAt(1).setIcon(R.drawable.video);
        tabLayout.getTabAt(2).setIcon(R.drawable.album);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if( id == R.id.action_choose_directory) {
//            Intent intent = new Intent();
//            intent.setClass(this, OpenFileDialog.class);
//            startActivityForResult(intent, 1);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                imagePaths.clear();
                imagePaths = loadFiles.getFilePaths(data.getStringExtra("folderpath"));
                Log.d("PATH",imagePaths.size() + "");
//                imagePaths = loadFiles.getFilePaths("/storage/emulated/0/DCIM/Camera/");
                imageFragment.setImagePaths(imagePaths);
                imageFragment.notifyDataChanged();
            }
        }
    }
}