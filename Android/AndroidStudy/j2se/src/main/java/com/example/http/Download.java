package com.example.http;

import com.example.util.L;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by minyangcheng on 2017/6/8.
 */

public class Download {

    private static final String HEX_STRING = "0123456789ABCDEF";

    public static void main(String args[]){
//        Test("http://shengan-image.cheguo.com/cgw360/cls/loan_patch/6421d2be-aff4-44a8-a78b-b3fe8920991d.mp4");
        Test("http://cheguo-image.cheguo.com/files/2017-06-05/201706051650530805072.jpg");
    }
    public static void Test(String httpurl){
        try {
            URL url = new URL(httpurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection= (HttpURLConnection) reload(connection);
            connection.setInstanceFollowRedirects(true);
            connection.connect();
            System.out.println(connection.getURL().toString()+" ----------------------------url");
            int code = connection.getResponseCode();
            System.out.println(code);
            System.out.println(connection.getHeaderField("Location"));
            System.out.println(connection.getURL().toString()+" ----------------------------url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String doucUrl) {

        try {
            String urlStr=(doucUrl);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(6000);
            con.setReadTimeout(6000);
            con.connect();
            int responseCode=con.getResponseCode();
            InputStream is = con.getInputStream();
            int contentLength = con.getContentLength();
            System.out.println("长度 :" + contentLength+"  responseCode="+responseCode+" newUrl="+con.getURL());
            byte[] bs = new byte[1024];
            int len;
            FileOutputStream os = new FileOutputStream("F:\\abc.mp4");
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static URLConnection reload(URLConnection uc) throws Exception {
        HttpURLConnection huc =(HttpURLConnection) uc;
        if (huc.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP || huc.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM){
            return reload(new URL(huc.getHeaderField("location")).openConnection());
        }else{
            return huc;
        }
    }

}
