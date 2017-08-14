package com.min.know.primitive;

import com.min.know.util.L;

/**
 * Created by minyangcheng on 2017/8/14.
 */
public class IntegerDemo {

    public static void main(String[] args) {
        Integer a = 1000;
        Integer b = 1000;
        L.d("a==b -->%s", a == b);
        L.d("a.equal(b) -->%s", a.equals(b));
    }

}
