package com.example.util;

/**
 * Created by minyangcheng on 2016/9/16.
 */
public class L {

    public static void d(String mess , Object ...args){
        if(args!=null&&args.length>0){
            mess=String.format(mess,args);
        }
        System.out.println(mess);
    }

}
