package ercanduman.notetakingapp;

import android.app.Application;

public class MainApplication extends Application {
    private static MainApplication context;

    public MainApplication() {
        if (context == null) context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MainApplication getContext() {
        return context;
    }
}
