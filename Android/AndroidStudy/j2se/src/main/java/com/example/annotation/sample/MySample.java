package com.example.annotation.sample;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@MyClassAnnotation(uri = "com.ross.MySample", desc = "The class name")
public class MySample
{
    @com.example.annotation.sample.MyFieldAnnotation(uri = "com.ross.MySample#id", desc = "The class field")
    public String id;

    /**
     * Description: default constructor
     */
    @com.example.annotation.sample.MyConstructorAnnotation(uri = "com.ross.MySample#MySample", desc = "The default constuctor")
    public MySample()
    {
    }

    /**
     * Description: normal method
     */
    @com.example.annotation.sample.MyMethodAnnotation(uri = "com.ross.MySample#setId", desc = "The class method")
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Description: MyAnnotation test
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static void main(String[] args) throws SecurityException,
            NoSuchMethodException, NoSuchFieldException
    {
        MySample oMySample = new MySample();
        // get class annotation
        MyClassAnnotation oMyAnnotation = MySample.class
                .getAnnotation(MyClassAnnotation.class);
        System.out.println("Class's uri: " + oMyAnnotation.uri() + "; desc: "
                + oMyAnnotation.desc());

        // get constructor annotation
        Constructor oConstructor = oMySample.getClass().getConstructor();
        com.example.annotation.sample.MyConstructorAnnotation oMyConstructorAnnotation = (com.example.annotation.sample.MyConstructorAnnotation) oConstructor.getAnnotation(com.example.annotation.sample.MyConstructorAnnotation.class);
        System.out.println("Constructor's uri: "
                + oMyConstructorAnnotation.uri() + "; desc: "
                + oMyConstructorAnnotation.desc());

        // get method annotation
        Method oMethod = oMySample.getClass().getDeclaredMethod("setId",String.class);
        com.example.annotation.sample.MyMethodAnnotation oMyMethodAnnotation = oMethod.getAnnotation(com.example.annotation.sample.MyMethodAnnotation.class);
        System.out.println("Method's uri: " + oMyMethodAnnotation.uri()
                + "; desc: " + oMyMethodAnnotation.desc());

        // get field annotation
        Field oField = oMySample.getClass().getDeclaredField("id");
        com.example.annotation.sample.MyFieldAnnotation oMyFieldAnnotation = oField.getAnnotation(com.example.annotation.sample.MyFieldAnnotation.class);
        System.out.println("Field's uri: " + oMyFieldAnnotation.uri()
                + "; desc: " + oMyFieldAnnotation.desc());

    }

}
