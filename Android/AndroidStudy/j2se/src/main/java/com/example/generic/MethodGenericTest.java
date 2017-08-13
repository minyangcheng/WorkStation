package com.example.generic;

import com.example.util.L;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by minyangcheng on 2017/4/6.
 */

public class MethodGenericTest {

    public static void main(String [] arags) {
        callJS("test");
    }

    public static void callJS(String methodName,Object... args){
        String data="javascript:";
        data+=methodName+"(";
        if(args.length>0){
            for(Object s:args){
                data+=s+",";
            }
            data=data.substring(0,data.length()-1);
        }
        data+=")";
        L.d("data=%s",data);
    }

}

class Demo<T>
{
    public void method(T t) {
        System.out.println("method :"+ t);
    }

    public static <W> void staticMethod(W w)
    {
        System.out.println("staticMethod :"+ w);
    }

    public <K> void show( K  k)
    {
        System.out.println("Show :"+ k);
    }

    public static <Q> List<Q> print(String a) {
        try {
            Method method=Demo.class.getDeclaredMethod("print",String.class);
            L.d("method-name=%s",method.getReturnType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
