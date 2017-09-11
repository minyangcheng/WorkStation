package com.min.aop_demo.record;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by minyangcheng on 2017/9/7.
 * <p>
 * 用于注解切点方法
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RecordPoint {
}
