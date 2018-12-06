package com.min.know.algorithm;

import java.util.Base64;

/**
 * https://mp.weixin.qq.com/s?__biz=MzI1MTIzMzI2MA==&mid=2650561380&idx=1&sn=f94b3eae23d0c6a975ae6dae1f0c1b68&chksm=f1feede7c68964f167ff9bc9be76bb348d551e64b9f57f0400a26cc397b238f9e90c06012b63&scene=21#wechat_redirect
 */
public class Base64Test {

    public static void main(String args[]) {

        String str = "abcd";
        String encodeStr = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println(encodeStr);
        str = new String(Base64.getDecoder().decode(encodeStr));
        System.out.println(str);

    }

}
