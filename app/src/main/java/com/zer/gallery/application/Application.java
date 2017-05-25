package com.zer.gallery.application;

import com.mz.ZAndroidSystemDK;

/**
 * Created by ngodi on 22/05/2017.
 */

public class Application extends android.app.Application{
    @Override
    public void onCreate() {

        super.onCreate();
        ZAndroidSystemDK.initApplication(this,getPackageName());
    }
}
