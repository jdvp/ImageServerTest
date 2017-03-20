package com.valentech.imageservertest.activity.injection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dagger.ObjectGraph;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jdvp on 3/20/17.
 */

public abstract class InjectorActivity extends AppCompatActivity {


    private ObjectGraph objectGraph;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected void createGraph() {
        final IGraphInstantiator graphInstantiator = new GraphInstantiator() {
            @Override
            public Object[] getModules() {
                return new Object[]{getModule()};
            }
        };

        objectGraph = graphInstantiator.createGraphFromParent(null);
        objectGraph.inject(this);
    }

    public abstract Object getModule();

    @Override
    public void onDestroy() {
        super.onDestroy();
        objectGraph = null;
    }

    @Override
    public void onPause() {
        if(compositeSubscription.hasSubscriptions()) {
            compositeSubscription.clear();
        }
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createGraph();
    }
}
