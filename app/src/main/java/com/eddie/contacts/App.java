package com.eddie.contacts;

import android.app.Application;

public class App extends Application {
    private static App instance;
    public App(){
        super();
        instance = this;
    }
    public static App get(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        StoreProvider.getInstance().setContext(this);
    }
}

