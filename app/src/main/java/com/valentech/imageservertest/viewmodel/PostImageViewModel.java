package com.valentech.imageservertest.viewmodel;

/**
 * Created by jdvp on 3/20/17.
 */

import com.valentech.imageservertest.model.binding.ImageList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
public interface PostImageViewModel {
    Observable<ImageList> postImage(MultipartBody.Part image, RequestBody name, RequestBody caption);
}
