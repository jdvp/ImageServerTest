package com.valentech.imageservertest.viewmodel.impl;

import com.valentech.imageservertest.activity.injection.RxTransform;
import com.valentech.imageservertest.model.binding.Image;
import com.valentech.imageservertest.model.datamanager.ImageDataManager;
import com.valentech.imageservertest.viewmodel.MainViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by jdvp on 3/20/17.
 */

public class MainViewModelImpl implements MainViewModel {
    private ImageDataManager imageDataManager;

    @Inject
    public MainViewModelImpl(ImageDataManager imageDataManager) {
        this.imageDataManager = imageDataManager;
    }

    @Override
    public Observable<List<Image>> getImages() {
        return imageDataManager.getImages().flatMap(imageList -> Observable.just(imageList.getImages())).compose(RxTransform.ui());
    }
}
