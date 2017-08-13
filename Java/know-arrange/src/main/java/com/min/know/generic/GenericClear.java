package com.min.know.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by minyangcheng on 2017/8/13.
 */
public class GenericClear {

    public static void main(String[] args){
        Class c1 = new ArrayList<Integer>().getClass();
        Class c2 = new ArrayList<String>().getClass();
        System.out.println(c1 == c2);

//        List<Integer> list = new ArrayList<Integer>();
//        Map<Integer, String> map = new HashMap<Integer, String>();
//        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
//        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));

        Map<String, Integer> map = new HashMap<String, Integer>(){};
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        Type type = map.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
        for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
            System.out.println(typeArgument.getTypeName());
        }

    }

}
