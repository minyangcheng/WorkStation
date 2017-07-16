package com.min.gradle;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by minyangcheng on 2017/7/8.
 */
public class MyTest {

    @Test
    public void testService(){
        System.out.println("come from child-one test");
        Assert.assertEquals(1L,2L);
    }

}
