package com.min.know.generic;

import com.min.know.util.L;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by minyangcheng on 2016/9/20.
 */
public class B extends A<Animal> {

    public static void main(String args[]){
        //获取类上的指定的泛型参数
        B b=new B();
        b.t=new Animal();
        L.d("a class=%s",b.t);
        ParameterizedType parameterizedType= (ParameterizedType) b.getClass().getGenericSuperclass();
        Type[] types=parameterizedType.getActualTypeArguments();
        if(types!=null){
            L.d("main type=%s",types[0]);
        }
    }

}
