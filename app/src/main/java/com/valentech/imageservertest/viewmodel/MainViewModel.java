package com.valentech.imageservertest.viewmodel;

import com.valentech.imageservertest.model.binding.Image;

import java.util.List;

import rx.Observable;

/**
 * Created by jdvp on 3/20/17.
 */

public interface MainViewModel {
    Observable<List<Image>> getImages();
}
