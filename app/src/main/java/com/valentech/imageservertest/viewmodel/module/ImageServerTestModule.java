package com.valentech.imageservertest.viewmodel.module;

import com.valentech.imageservertest.ImageServerTestApplication;
import com.valentech.imageservertest.ServiceInstantiator;
import com.valentech.imageservertest.model.datamanager.ImageDataManager;
import com.valentech.imageservertest.model.datamanager.impl.ImageDataManagerImpl;
import com.valentech.imageservertest.service.ImageService;
import com.valentech.imageservertest.viewmodel.MainViewModel;
import com.valentech.imageservertest.viewmodel.PostImageViewModel;
import com.valentech.imageservertest.viewmodel.impl.MainViewModelImpl;
import com.valentech.imageservertest.viewmodel.impl.PostImageViewModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jdvp on 3/20/17.
 */

@Module(
        injects = {ImageServerTestApplication.class},
        library = true
)
public class ImageServerTestModule {

    private final ImageServerTestApplication application;

    public ImageServerTestModule(ImageServerTestApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public ImageServerTestApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public ImageService providesImageService() {
        return ServiceInstantiator.create(ImageService.class);
    }

    @Provides
    @Singleton
    public ImageDataManager provideImageDataManager(ImageService imageService) {
        return new ImageDataManagerImpl(imageService);
    }

    @Provides
    @Singleton
    public MainViewModel provideMainViewModel(ImageDataManager imageDataManager) {
        return new MainViewModelImpl(imageDataManager);
    }

    @Provides
    @Singleton
    public PostImageViewModel providePostImageVieModel(ImageDataManager imageDataManager) {
        return new PostImageViewModelImpl(imageDataManager);
    }
}
