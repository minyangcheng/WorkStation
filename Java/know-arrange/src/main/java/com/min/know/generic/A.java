package com.min.know.generic;

import com.min.know.util.L;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by minyangcheng on 2016/9/20.
 */
public class A <T>{

    public T t;

    public A(){
        //获取类上的指定的泛型参数
        ParameterizedType parameterizedType= (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types=parameterizedType.getActualTypeArguments();
        if(types!=null){
            L.d("a type=%s", types[0]);
        }
    }

}
