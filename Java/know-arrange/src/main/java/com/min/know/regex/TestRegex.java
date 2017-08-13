package com.min.know.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by minyangcheng on 2016/11/15.
 */

public class TestRegex {

    public static void main(String args[]){
//        replaceAllUsage();
//        findUsage();
//        matchsUsage();
//        stringReplace();
//        stringReplaceAll();
        replacePhone();
    }

    public static void replacePhone(){
        String orginalStr="13657920128";
        String regex="(\\d{3})(\\d{4})(\\d{4})";
        String resultStr=orginalStr.replaceAll(regex,"$1****$2");
        System.out.println(resultStr);
    }

    public static void stringReplace(){
        String originalStr="我是aa中国dd";
        //此替换方法完全不考虑正则中的特殊字符
        String resultStr=originalStr.replace("aa","");
        System.out.println(resultStr);
    }

    public static void stringReplaceAll(){
        String originalStr="我是aa中国dd";
        //此替换等同Pattern.compile(regex).matcher(str).replaceAll(replacement);
        String resultStr=originalStr.replaceAll("\\w","");
        System.out.println(resultStr);
    }

    public static void replaceAllUsage(){
        String originalStr="我是aa中国dd";
        Pattern pattern=Pattern.compile("\\w+");
        Matcher matcher=pattern.matcher(originalStr);
        String resultStr=matcher.replaceAll("");
        System.out.println(resultStr);
    }

    public static void findUsage(){
        String originalStr="我是aa中国dd";
        Pattern pattern=Pattern.compile("\\w+");
        Matcher matcher=pattern.matcher(originalStr);
        while (matcher.find()){
            String s=matcher.group();
            System.out.println("s="+s+"  start="+matcher.start()+" end="+matcher.end());
        }
    }

    public static void matchsUsage(){
        String originalStr="我是aa中国dd";
        Pattern pattern=Pattern.compile("\\w+");
        Matcher matcher=pattern.matcher(originalStr);

        System.out.println(matcher.matches());
    }

}
