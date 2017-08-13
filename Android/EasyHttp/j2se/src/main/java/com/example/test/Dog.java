package com.example.test;

import com.example.bean.UserBean;

import java.io.File;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public class Dog extends Animal{

    private String name;
    private int age;
    private File introFile;
    private File headFile;

    private transient UserBean userBean=new UserBean("100","min","abc123",123);

    private transient String traName;
    private static int traAge;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public File getIntroFile() {
        return introFile;
    }

    public void setIntroFile(File introFile) {
        this.introFile = introFile;
    }

    public String getTraName() {
        return traName;
    }

    public void setTraName(String traName) {
        this.traName = traName;
    }

    public static int getTraAge() {
        return traAge;
    }

    public static void setTraAge(int traAge) {
        Dog.traAge = traAge;
    }
}
