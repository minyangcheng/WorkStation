package com.min.know.reflect;

import com.min.know.gson.GsonUtil;
import com.min.know.util.L;

import java.lang.reflect.Field;

/**
 * Created by minyangcheng on 2017/8/15.
 */
public class ReflectDemo {

    public static void main(String[] args) throws Exception {
        Dog dog=new Dog();
        dog.name="xiaohu";
        Person person=new Person();
        person.name="xiaohong";
        person.age=12;
        person.dog=dog;

        Field[] fields=person.getClass().getDeclaredFields();
        for(Field field: fields){
            String name=field.getName();
            Object obj=field.get(person);
            if(field.getType().isPrimitive()){
                L.d("primitive name=%s , value=%s",name,obj);
            }else if(field.getType()==String.class){
                L.d("string name=%s , value=%s",name,obj);
            }else{
                L.d("object name=%s , value=%s",name, GsonUtil.toJson(obj));
            }
        }

        L.d("isPrimitive=%s",dog.getClass().isPrimitive());
    }

}
