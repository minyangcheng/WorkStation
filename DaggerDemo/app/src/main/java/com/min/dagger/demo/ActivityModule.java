package com.min.dagger.demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by minyangcheng on 2017/8/9.
 */

@Module
public class ActivityModule {
    @Provides
    ActivityBean providerActivityBean() {
        return new ActivityBean();
    }
}
