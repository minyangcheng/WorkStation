package com.example.collection;

/**
 * Created by minyangcheng on 2016/11/18.
 */

public class Person {

    public String name;
    public int age;

    public Person(String name ,int age){
        this.name=name;
        this.age=age;
    }

    @Override
    public int hashCode() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Person){
            Person p= (Person) obj;
            return p.age==age;
        }
        return super.equals(obj);
    }
}
