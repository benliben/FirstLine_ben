package com.example.benben.firstline;

import android.app.Application;
import android.content.Context;

/**
 * Created by benben on 2016/5/14.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
