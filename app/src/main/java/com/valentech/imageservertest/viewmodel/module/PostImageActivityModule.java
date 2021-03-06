package com.valentech.imageservertest.viewmodel.module;

import com.valentech.imageservertest.activity.PostImageActivity;

import dagger.Module;

/**
 * Created by jdvp on 3/20/17.
 */
@Module(
        addsTo = ImageServerTestModule.class,
        injects = PostImageActivity.class
)
public class PostImageActivityModule {
}
