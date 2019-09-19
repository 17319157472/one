package com.example.cy.cy_qimo.app;

import android.app.Application;

public class MainApplication extends Application {
    private static MainApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    public static MainApplication getApp() {
        return app;
    }
}
