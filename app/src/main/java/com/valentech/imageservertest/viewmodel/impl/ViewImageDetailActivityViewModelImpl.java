package com.valentech.imageservertest.viewmodel.impl;


import com.valentech.imageservertest.activity.injection.RxTransform;
import com.valentech.imageservertest.model.binding.Image;
import com.valentech.imageservertest.model.datamanager.ImageDataManager;
import com.valentech.imageservertest.viewmodel.ViewImageDetailActivityViewModel;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by jdvp on 3/20/17.
 */

public class ViewImageDetailActivityViewModelImpl implements ViewImageDetailActivityViewModel {
    private ImageDataManager imageDataManager;

    @Inject
    public ViewImageDetailActivityViewModelImpl(ImageDataManager imageDataManager) {
        this.imageDataManager = imageDataManager;
    }

    @Override
    public Observable<Image> postComment(String id, String name, String comment) {
        return imageDataManager.postComment(id, name, comment).compose(RxTransform.ui());
    }
}
