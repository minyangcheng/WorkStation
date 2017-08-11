package com.min.dagger.demo;

import dagger.Component;

/**
 * Created by minyangcheng on 2017/8/9.
 */

@ForActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);


    void inject(MainActivity.OtherClass otherClass);
}
