package com.valentech.imageservertest.model.datamanager;

import com.valentech.imageservertest.model.binding.ImageList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by jdvp on 3/20/17.
 */

public interface ImageDataManager {
    public Observable<ImageList> getImages();
    public Observable<ImageList> postImage(MultipartBody.Part image, RequestBody name, RequestBody caption);
}
