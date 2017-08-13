package com.min.know;

import com.min.know.util.L;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MyClass {

    private static final String HEX_STRING = "0123456789ABCDEF";

    public static void main(String args[]){
        try {
            L.d(callJS("aa","bb","cc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String callJS(String methodName,String... args){
        String data="javascript:";
        data+=methodName+"(";
        if(args.length>0){
            for(Object s:args){
                data+="'"+s+"',";
            }
            data=data.substring(0,data.length()-1);
        }
        data+=")";
        return data;
    }

    public static String toBrowserCode(String url) {
        if(url.contains("%2f")||url.contains("%2F")){
            return url;
        }
        try {
            int index=url.indexOf("//",10);
            int cnt=2;
            if(index==-1){
                index=url.indexOf("/",10);
                cnt=1;
            }
            String part1=url.substring(0,index);
            String part2=url.substring(index+cnt);
            part2= URLEncoder.encode(part2,"utf-8");
            return part1+"/"+part2;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String toBrowserCode_(String word) {
        byte[] bytes = word.getBytes();

        //不包含中文，不做处理
        if (bytes.length == word.length())
            return word;

        StringBuilder browserUrl = new StringBuilder();
        String tempStr = "";

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);

            //不需要处理
            if ((int) currentChar <= 256) {

                if (tempStr.length() > 0) {
                    byte[] cBytes = tempStr.getBytes();

                    for (int j = 0; j < cBytes.length; j++) {
                        browserUrl.append('%');
                        browserUrl.append(HEX_STRING.charAt((cBytes[j] & 0xf0) >> 4));
                        browserUrl.append(HEX_STRING.charAt((cBytes[j] & 0x0f) >> 0));
                    }
                    tempStr = "";
                }

                browserUrl.append(currentChar);
            } else {
                //把要处理的字符，添加到队列中
                tempStr += currentChar;
            }
        }
        return browserUrl.toString();
    }

}
