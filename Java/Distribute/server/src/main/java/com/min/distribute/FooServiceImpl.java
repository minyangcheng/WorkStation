package com.min.distribute;

/**
 * Created by minyangcheng on 2017/7/2.
 */
public class FooServiceImpl implements FooService {

    public String hello(String name) {
        System.out.println(name + " invoked rpc service");
        return "hello " + name;
    }
}
