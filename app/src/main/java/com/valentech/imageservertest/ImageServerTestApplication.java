package com.valentech.imageservertest;

import android.app.Application;

import com.valentech.imageservertest.viewmodel.module.ImageServerTestModule;

import dagger.ObjectGraph;
import mortar.Mortar;
import mortar.MortarScope;

/**
 * Created by jdvp on 3/20/17.
 */
public class ImageServerTestApplication extends Application {
    private MortarScope rootScope;
    private static ImageServerTestApplication instance;


    public ImageServerTestApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        rootScope = Mortar.createRootScope(true, ObjectGraph.create(new ImageServerTestModule(this)));
    }

    public static ImageServerTestApplication getInstance() {
        return instance;
    }

    public MortarScope getRootScope() {
        return rootScope;
    }

}
