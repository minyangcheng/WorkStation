package com.example.generic;

import com.example.util.L;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by minyangcheng on 2016/9/20.
 */
public class C <T>{

    public T t;

    public T t1;

    public C(A<T> a){
    }

    public static void main(String args[]) throws Exception{
//        //如果没有指定泛型参数，在复制时可以指定任何类型
//        C c=new C();
//        c.t=1;
//        c.t1="a";
//        L.d("t=%s",c.t.getClass().getName());
//        L.d("t1=%s",c.t1.getClass().getName());
//
//        //创建对象时指定的泛型参数，是取不到值的
//        C<String> c1=new C();
//        ParameterizedType parameterizedType= (ParameterizedType) c1.getClass().getGenericSuperclass();
//        Type[] types=parameterizedType.getActualTypeArguments();
//        if(types!=null){
//            L.d("c type=%s",types[0]);
//        }
    }

}
