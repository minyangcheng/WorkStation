package com.hitherejoe.androidboilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.androidboilerplate.data.DataManager;
import com.hitherejoe.androidboilerplate.data.local.PreferencesHelper;
import com.hitherejoe.androidboilerplate.data.remote.AndroidBoilerplateService;
import com.hitherejoe.androidboilerplate.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    AndroidBoilerplateService provideAndroidBoilerplateService() {
        return AndroidBoilerplateService.Factory.makeAndroidBoilerplateService(mApplication);
    }

    @Provides
    @Singleton
    PreferencesHelper preferencesHelper(@ApplicationContext Context context) {
        return new PreferencesHelper(context);
    }

    @Provides
    @Singleton
    DataManager dataManager(AndroidBoilerplateService watchTowerService,
                            PreferencesHelper preferencesHelper) {
        return new DataManager(watchTowerService, preferencesHelper);
    }

}