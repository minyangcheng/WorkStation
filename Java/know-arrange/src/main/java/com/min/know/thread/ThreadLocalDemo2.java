package com.min.know.thread;

public class ThreadLocalDemo2 {

    public static ThreadLocal<String> localValue = new ThreadLocal<>();

    public static void main(String[] args) {
        localValue.set("nihao");
        System.out.println(localValue.get());
        localValue.remove();
        System.out.println(localValue.get());
    }

}
