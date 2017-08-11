package com.min.dagger.demo;

import com.min.dagger.demo.bean.UserBean;

import dagger.Module;
import dagger.Provides;

/**
 * Created by minyangcheng on 2017/8/9.
 */

@Module
public class ActivityModule {

    private String name;

    public ActivityModule(String name){
        this.name=name;
    }

    @Provides
    UserBean providerUserBean(){
        return new UserBean(name);
    }

    @Provides
    ActivityBean providerActivityBean() {
        return new ActivityBean();
    }
}
