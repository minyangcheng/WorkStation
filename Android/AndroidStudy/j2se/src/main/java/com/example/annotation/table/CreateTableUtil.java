package com.example.annotation.table;

import com.example.util.L;

import java.lang.reflect.Field;

/**
 * Created by minyangcheng on 2016/9/19.
 */
public class CreateTableUtil {

    public static void main(String args[]){
        Person person=new Person();

        TableName tableName=person.getClass().getAnnotation(TableName.class);
        L.d("tableName=%s",tableName.value());

        Field[] fields=person.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Field field=fields[i];
            TableFiled tableFiled=field.getDeclaredAnnotation(TableFiled.class);
            L.d("filedName=%s, tableField=%s, tableType=%s",field.getName()
                        ,tableFiled.value(),tableFiled.type());
        }

    }

}
