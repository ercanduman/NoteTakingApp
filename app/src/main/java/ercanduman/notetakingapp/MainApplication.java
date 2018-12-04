package ercanduman.notetakingapp;

import android.app.Application;

public class MainApplication extends Application {
    private MainApplication context;

    public MainApplication() {
        if (context == null) context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public MainApplication getContext() {
        return context;
    }

    public void setContext(MainApplication context) {
        this.context = context;
    }
}
