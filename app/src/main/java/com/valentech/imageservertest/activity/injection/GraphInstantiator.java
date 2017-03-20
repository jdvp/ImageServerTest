package com.valentech.imageservertest.activity.injection;

import android.support.annotation.Nullable;

import com.valentech.imageservertest.ImageServerTestApplication;

import dagger.ObjectGraph;

/**
 * Created by jdvp on 3/20/17.
 */

public abstract class GraphInstantiator implements IGraphInstantiator{

    public ObjectGraph createGraphFromParent(@Nullable ObjectGraph parent) {
        if(parent == null && ImageServerTestApplication.getInstance().getRootScope().getObjectGraph() == null) {
            throw new IllegalStateException("No graphs exist");
        }
        ObjectGraph objectGraph = parent;
        if(parent == null) {
            objectGraph = ImageServerTestApplication.getInstance().getRootScope().getObjectGraph();
        }
        return objectGraph.plus(getModules());
    }

    public ObjectGraph create() {
        return ObjectGraph.create(getModules());
    }
}
