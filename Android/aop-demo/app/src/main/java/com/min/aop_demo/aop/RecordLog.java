package com.min.aop_demo.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by minyangcheng on 2017/9/7.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RecordLog {

    String value() default "";

}
