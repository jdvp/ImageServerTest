package com.valentech.imageservertest.viewmodel;

import com.valentech.imageservertest.model.binding.Image;

import rx.Observable;

/**
 * Created by jdvp on 3/20/17.
 */

public interface ViewImageDetailActivityViewModel {
    public Observable<Image> postComment(String id, String name, String comment);
}
