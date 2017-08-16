package com.min.know.generic;

import com.min.know.util.L;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by minyangcheng on 2017/8/14.
 */
public class GetGenericInfoDemo {

    /**
     * Java 泛型擦除是 Java 泛型中的一个重要特性，其目的是避免过多的创建类而造成的运行时的过度消耗。所以，想 ArrayList<Integer> 和 ArrayList<String> 这两个实例，其类实例是同一个。
     * 但很多情况下我们又需要在运行时获得泛型信息，那我们可以通过定义类的方式（通常为匿名内部类，因为我们创建这个类只是为了获得泛型信息）在运行时获得泛型参数，从而满足例如序列化、反序列化等工作的需要。
     *
     * @param args
     */
    public static void main(String args[]) {
        Child b = new Child();
        b.t = new Fruit();
        L.d("child t.class=%s", b.t.getClass());
        ParameterizedType parameterizedType = (ParameterizedType) b.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        if (types != null) {
            L.d("child type=%s", types[0]);
        }

        Parent<Fruit> p = new Parent<>();
        System.out.println(Arrays.toString(p.getClass().getTypeParameters()));
    }

}
