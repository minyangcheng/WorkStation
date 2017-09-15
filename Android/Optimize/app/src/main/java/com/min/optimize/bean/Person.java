package com.min.optimize.bean;

import android.support.annotation.Keep;

/**
 * Created by minyangcheng on 2017/9/13.
 */

public class Person {

    public String name;
    public int age;
    public Animal animal;

    @Keep
    public void add() {
        this.age++;
    }

}
