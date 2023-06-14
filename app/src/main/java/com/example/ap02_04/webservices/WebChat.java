package com.example.ap02_04.webservices;

import android.app.Application;
import android.content.Context;

public class WebChat extends Application {
    public static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
