package com.valentech.imageservertest.activity.injection;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jdvp on 2/18/17.
 */

public class RxTransform {

    public static <T>Observable.Transformer<T, T> ui() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
