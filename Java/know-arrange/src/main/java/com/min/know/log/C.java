package com.min.know.log;

/**
 * Created by minyangcheng on 2017/8/17.
 */
public class C {

    public void log(){
        System.out.println("c log");
        StackTraceUtil.printStackTrace();
    }

    public static void main(String[] args){
        A a=new A();
        a.log();
    }

}
