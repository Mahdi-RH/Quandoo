package com.quandoo.androidtask;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}