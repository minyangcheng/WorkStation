package com.example.annotation.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by minyangcheng on 2016/9/19.
 */
@Target(ElementType.TYPE)  //@Target 表示该注解目标,可能的 ElemenetType 参数包括:
@Retention(RetentionPolicy.RUNTIME)  // @Retention 表示该注解的生命周期,
public @interface TableName {

    String value() default "";

}
