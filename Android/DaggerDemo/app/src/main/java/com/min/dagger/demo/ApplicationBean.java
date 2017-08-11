package com.min.dagger.demo;

/**
 * Created by minyangcheng on 2017/8/9.
 */

public class ApplicationBean {
    private String name = null;


    public ApplicationBean() {
        name = "AppBean";
    }


    public String getAppBeanName() {
        return name;
    }
}
