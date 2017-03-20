package com.valentech.imageservertest.service;

import com.valentech.imageservertest.model.binding.ImageList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by jdvp on 3/11/17.
 */

public interface ImageService {

    @GET("/img")
    public Observable<ImageList> getImages();

    @Multipart
    @POST("/img")
    public Observable<ImageList> postImage(@Part MultipartBody.Part image, @Part("user") RequestBody name, @Part("caption") RequestBody caption);
}
