package com.sample.abhijeet.inventorymanager.util;

import android.app.Application;
import android.content.Context;

import com.sample.abhijeet.inventorymanager.modules.ApplicationComponent;
import com.sample.abhijeet.inventorymanager.modules.ApplicationModule;
import com.sample.abhijeet.inventorymanager.modules.DaggerApplicationComponent;

/**
 * Created by abhi2 on 3/25/2018.
 */

public class MyApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return MyApplication.context;
    }

    private ApplicationComponent mApiComponent;


    @Override
    public void onCreate() {
        super.onCreate();
       MyApplication.context = getApplicationContext();
        mApiComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getNetComponent() {
        return mApiComponent;
    }

}
