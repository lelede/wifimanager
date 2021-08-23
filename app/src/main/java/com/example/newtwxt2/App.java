package com.example.newtwxt2;

import android.app.Application;

import com.example.newtwxt2.util.Utils;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
    }

    public static App getInstance() {
        return instance;
    }
}
