package com.example.annotation.table;

/**
 * Created by minyangcheng on 2016/9/19.
 */
@TableName("table_person")
public class Person {

    @TableFiled(value="t_name",type=1)
    public String name;

    @TableFiled(value="t_age",type=2)
    public String age;

}
