package com.min.dagger.demo;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by minyangcheng on 2017/8/9.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(DaggerApplication application);


    //说明将BeanForApplication开放给其他Component使用
    ApplicationBean providerAppBean();
}
