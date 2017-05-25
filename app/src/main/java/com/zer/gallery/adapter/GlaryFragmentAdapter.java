package com.zer.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zer.gallery.R;
import com.zer.gallery.fragment.AlbumFragment;
import com.zer.gallery.fragment.ImageFragment;
import com.zer.gallery.fragment.VideoFragment;

/**
 * Created by Anh Son on 17/05/2017.
 */

public class GlaryFragmentAdapter extends FragmentStatePagerAdapter {
    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    private String image = String.valueOf(getClass().getResource(String.valueOf(R.string.images)));
    private String video = String.valueOf(getClass().getResource(String.valueOf(R.string.videos)));
    private String album = String.valueOf(getClass().getResource(String.valueOf(R.string.album)));

    private AlbumFragment albumFragment;
    public void setImageFragment(ImageFragment imageFragment) {
        this.imageFragment = imageFragment;
    }

    public void setVideoFragment(VideoFragment videoFragment) {
        this.videoFragment = videoFragment;
    }

    public void setAlbumFragment(AlbumFragment albumFragment) {
        this.albumFragment = albumFragment;
    }

    public GlaryFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return imageFragment;
            case 1:
                return videoFragment;
            case 2:
                return albumFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Images";
            case 1:
                return "Videos";
            case 2:
                return "Albums";
        }
        return null;
    }
}
