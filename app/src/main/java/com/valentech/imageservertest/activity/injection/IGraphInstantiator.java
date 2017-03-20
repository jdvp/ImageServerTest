package com.valentech.imageservertest.activity.injection;

import dagger.ObjectGraph;

/**
 * Created by jdvp on 3/20/17.
 */

public interface IGraphInstantiator {

    ObjectGraph createGraphFromParent(ObjectGraph parent);

    ObjectGraph create();

    Object[] getModules();
}
