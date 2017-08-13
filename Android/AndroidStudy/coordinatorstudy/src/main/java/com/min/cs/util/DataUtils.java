package com.min.cs.util;

import com.min.cs.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class DataUtils {

    public static List<Person> getPersonData(int num){
        List<Person> personList=new ArrayList<>();
        Person p=null;
        for(int i=0;i<num;i++){
            p=new Person();
            p.name="name_"+i;
            p.address="address_"+i;
            p.age=i;
            personList.add(p);
        }
        return personList;
    }

}
