package com.valentech.imageservertest.model.datamanager.impl;

import com.valentech.imageservertest.model.binding.CommentRequestBody;
import com.valentech.imageservertest.model.binding.Image;
import com.valentech.imageservertest.model.binding.ImageList;
import com.valentech.imageservertest.model.datamanager.ImageDataManager;
import com.valentech.imageservertest.service.ImageService;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by jdvp on 3/20/17.
 */

public class ImageDataManagerImpl implements ImageDataManager{
    private ImageService imageService;

    @Inject
    public ImageDataManagerImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public Observable<ImageList> getImages() {
        return imageService.getImages();
    }

    @Override
    public Observable<ImageList> postImage(MultipartBody.Part image, RequestBody name, RequestBody caption) {
        return imageService.postImage(image, name, caption);
    }

    @Override
    public Observable<Image> postComment(String id, String name, String comment) {
        return imageService.postComment("/img/" + id, new CommentRequestBody(name, comment));
    }
}
