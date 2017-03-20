package com.valentech.imageservertest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jdvp on 3/11/17.
 */

public class ServiceInstantiator {

    public static <T> T create (Class<T> service) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClientBuilder.build());
        builder.baseUrl("https://img-server.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder.build().create(service);
    }
}
