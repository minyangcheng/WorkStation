package com.min.dagger.demo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by minyangcheng on 2017/8/9.
 */

@Module
public class ApplicationModule {
    //作为单例模式注入app
    @Singleton
    @Provides
    ApplicationBean privoderAppBean() {
        return new ApplicationBean();
    }
}