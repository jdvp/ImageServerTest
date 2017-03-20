package com.valentech.imageservertest.viewmodel.impl;

import com.valentech.imageservertest.activity.injection.RxTransform;
import com.valentech.imageservertest.model.binding.ImageList;
import com.valentech.imageservertest.model.datamanager.ImageDataManager;
import com.valentech.imageservertest.viewmodel.PostImageViewModel;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by jdvp on 3/20/17.
 */

public class PostImageViewModelImpl implements PostImageViewModel {

    private ImageDataManager imageDataManager;

    @Inject
    public PostImageViewModelImpl(ImageDataManager imageDataManager) {
        this.imageDataManager = imageDataManager;
    }

    @Override
    public Observable<ImageList> postImage(MultipartBody.Part image, RequestBody name, RequestBody caption) {
        return imageDataManager.postImage(image, name, caption).compose(RxTransform.ui());
    }
}
