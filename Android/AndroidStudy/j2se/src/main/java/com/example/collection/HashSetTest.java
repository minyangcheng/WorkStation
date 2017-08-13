package com.example.collection;

import com.example.gson.GsonUtil;
import com.example.util.L;

import java.util.HashSet;

/**
 * Created by minyangcheng on 2016/12/14.
 */

public class HashSetTest {

    public static void main(String args[]){
        HashSet<Person> set=new HashSet<>();
        Person p1=new Person("minyangcheng",12);
        Person p2=new Person("zhangheng",12);
        set.add(p1);
        set.add(p2);
        L.d(GsonUtil.toPrettyJson(set));
    }

}
