package com.naro.blog;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class AppConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
