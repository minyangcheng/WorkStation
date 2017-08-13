package com.min.te;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by minyangcheng on 2016/11/14.
 */
public class CalculatorTest {

    private Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        mCalculator=new Calculator();
    }

    @Test
    public void sum() throws Exception {
        assertEquals(4,mCalculator.sum(2,2),0);
    }

    @Test
    public void substract() throws Exception {
        assertEquals(0,mCalculator.substract(2,2),0);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(0,mCalculator.substract(2,2),0);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(4,mCalculator.multiply(2,2),0);
    }

}