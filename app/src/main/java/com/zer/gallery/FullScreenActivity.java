package com.zer.gallery;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zer.gallery.customview.SelectiveViewPager;
import com.zer.gallery.customview.ZoomableImageView;
import com.zer.gallery.utils.Const;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import tyrantgit.explosionfield.ExplosionField;

public class FullScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private Animation animAlpha;
    private ExplosionField explosionField;
    private ImageView imvInfo , imvSetting , imvSharing , imvDelete , imvEdit;
    public static String TAG = "FullScreenActivity";
    private LinearLayout llMenuShowImage;
    ArrayList<String> filePath;
    int position , isSelected;
    float lastX;
    boolean startSlideShow;
    private SelectiveViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.detail_image));
        position = getIntent().getExtras().getInt(Const.POSITION);
        filePath = getIntent().getExtras().getStringArrayList(Const.FILEPATH);
        startSlideShow = getIntent().getExtras().getBoolean("slideshow");
        bindView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindView() {
        explosionField = ExplosionField.attach2Window(FullScreenActivity.this);
        viewPager = (SelectiveViewPager) findViewById(R.id.image_pager);
        imvInfo = (ImageView) findViewById(R.id.imv_info);
        imvSetting = (ImageView) findViewById(R.id.imv_setting);
        imvSharing = (ImageView) findViewById(R.id.imv_sharing);
        imvDelete = (ImageView) findViewById(R.id.imv_delete);
        imvEdit = (ImageView) findViewById(R.id.imv_edit_image);
        imvEdit.setOnClickListener(this);
        imvInfo.setOnClickListener(this);
        imvSetting.setOnClickListener(this);
        imvSharing.setOnClickListener(this);
        imvDelete.setOnClickListener(this);
        initPager();
        llMenuShowImage = (LinearLayout) findViewById(R.id.ll_menu_show_image);
        llMenuFake = (LinearLayout) findViewById(R.id.ll_menu_show_fake);
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        animAlpha.setFillAfter(true);
        llMenuShowImage.startAnimation(animAlpha);
    }
    private void initPager() {
        PagerAdapter imageAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {

                final ZoomableImageView imageView = (ZoomableImageView) LayoutInflater.from(FullScreenActivity.this).inflate(R.layout.image_zooming, null);
                new AsyncTask<Object, Object, Object>(){
                    Bitmap src;
                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            Picasso.with(FullScreenActivity.this).setLoggingEnabled(true);
                            src = Picasso.with(FullScreenActivity.this).load(new File(filePath.get(position))).resize(500,500).centerInside().get();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                src = Glide.with(FullScreenActivity.this).load(filePath.get(position)).asBitmap().into(500,500).get();
                            } catch (InterruptedException | ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        imageView.setImageBitmap(src);
                        container.addView(imageView);
                    }
                }.execute();
                return imageView;
            }
            @Override
            public int getCount() {
                return filePath.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == (ZoomableImageView)object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((ZoomableImageView)object);

            }
        };
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(position);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG , "onclick");
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        final boolean[] isOnPage = {true};
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                isSelected = position;
                isOnPage[0] = false ;
            }

            @Override
            public void onPageSelected(int position) {
                isOnPage[0] = true;
                Log.e(TAG , "onPageSelected : "+position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 0){
                    llMenuShowImage.startAnimation(animAlpha);
                }
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("Paging","touching");
                if(isOnPage[0])
                    if(((ZoomableImageView)viewPager.getChildAt(viewPager.getCurrentItem())).isZooming()){
                        Log.d("PAGING","disabled");
                        viewPager.setPaging(false);
                    }
                    else {
                        viewPager.setPaging(true);
                    }
                return false;
            }
        });
        imageAdapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //===========================crop image
    final int PIC_CROP = 1;
    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_CROP) {
            if (data != null) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap selectedBitmap = extras.getParcelable("data");

                ImageView imgView = (ImageView) findViewById(R.id.imv_edt_imv);
                imgView.setImageBitmap(selectedBitmap);
            }
        }
    }
    //============================================
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_info :
                explosionField.setActivated(false);
                explosionField.explode(imvInfo);
                Runnable runable = new Runnable() {
                    @Override
                    public void run() {
                        explo();
                    }
                };
                new Thread(runable).start();
                break;
            case R.id.imv_setting :
                    AlertDialog.Builder builder = new AlertDialog.Builder(FullScreenActivity.this);
                    builder.setMessage(getString(R.string.dialog_init_back_ground));
                    builder.setCancelable(true);
                    builder.setPositiveButton(
                            getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());;
                    try {
                        File imageFile = new File(filePath.get(isSelected));
                        Bitmap bitmap = BitmapFactory.decodeFile(imageFile
                                .getAbsolutePath());
                        myWallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                    // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                                    dialog.cancel();
                                }
                            });

                    builder.setNegativeButton(
                            getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert1 = builder.create();
                    alert1.show();
                break;
            case R.id.imv_edit_image :
                Uri uriEdit = Uri.fromFile(new File("/sdcard/sample.jpg"));
                performCrop(uriEdit);
                break;
            case R.id.imv_delete:
                final File file = new File(filePath.get(isSelected));
                if(file.exists()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(FullScreenActivity.this);
                    builder1.setMessage(getString(R.string.dialog_delete_image));
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    file.delete();
                                    dialog.cancel();
                                    FullScreenActivity.this.finish();
                                }
                            });

                    builder1.setNegativeButton(
                            getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    Log.e(TAG , "not delete");
                }
                break;
            case R.id.imv_sharing:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/*");


                String uri = filePath.get(isSelected);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(uri)));
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void onResume() {
        explosionField = ExplosionField.attach2Window(FullScreenActivity.this);
        explosionField.setActivated(true);
        Log.e(TAG ,"is activated : "+explosionField.isActivated());
        Log.e(TAG ,"is enable : "+explosionField.isEnabled());
        Log.e(TAG ,"is focus : "+explosionField.isFocusable());
        super.onResume();
        Log.e(TAG , "onresume");
    }
    private LinearLayout llMenuFake;
    private void explo() {
        int time = 1;
        while(time >= 0){
            try {
                Thread.sleep(900);
                time = 0;
                Intent intent = new Intent(FullScreenActivity.this , InforActivity.class);
                intent.putExtra(Const.FILEPATH , filePath.get(isSelected));
                startActivity(intent);
                explosionField.setActivated(false);
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //======================================================

}
