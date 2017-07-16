package com.min.crawler;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minyangcheng on 2017/6/25.
 *
 * 模拟登陆，登陆成功后，再去访问我的信息页面
 */
public class LoginSimulation {

    Logger logger= LoggerFactory.getLogger(LoginSimulation.class);

    private static String userPhone = "15257178923";
    private static String password = "123456.";

    private static String renRenLoginURL = "http://www.cheguo.com/loginon?randomTime=1498705960604";
    private static String redirectURL = "http://www.cheguo.com/usercenter/myinfo.html";

    private CloseableHttpClient httpclient;

    public LoginSimulation(){
        List<Header> defaultHeaders=new ArrayList<Header>();
//        defaultHeaders.add(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"));
//        defaultHeaders.add(new BasicHeader("Referer","http://www.cheguo.com/"));
//        defaultHeaders.add(new BasicHeader("Origin","http://www.cheguo.com"));
//        defaultHeaders.add(new BasicHeader("Host","www.cheguo.com"));
//        defaultHeaders.add(new BasicHeader("Accept","application/json, text/javascript, */*; q=0.01"));
//        defaultHeaders.add(new BasicHeader("Accept-Encoding","gzip, deflate"));
//        defaultHeaders.add(new BasicHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4"));
//        defaultHeaders.add(new BasicHeader("Connection","keep-alive"));
        httpclient = HttpClientBuilder.create()
//                            .setDefaultHeaders(defaultHeaders)
//                            .setProxy(new HttpHost("183.146.9.79",40840))
                            .build();
    }

    private boolean login() {
        CloseableHttpResponse response=null;
        try {
            HttpPost httpost = new HttpPost(renRenLoginURL);
            HttpHost proxy=new HttpHost("49.87.75.85", 40840);
            RequestConfig requestConfig= RequestConfig.custom()
                    .setConnectTimeout(50000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(50000)
                    .setProxy(proxy)
                    .setRedirectsEnabled(true)
                    .build();
            httpost.setConfig(requestConfig);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("userphone", userPhone));
            nvps.add(new BasicNameValuePair("userpwd", password));
            httpost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpclient.execute(httpost);
            logger.info(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(response!=null)response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void printRedirectPageInfo(String redirectLocation) {
        HttpGet httpget = new HttpGet(redirectLocation);
        CloseableHttpResponse response=null;
        try {
            response = httpclient.execute(httpget);
            logger.info(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null)response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LoginSimulation loginSimulation = new LoginSimulation();
        if (loginSimulation.login()) {
            loginSimulation.printRedirectPageInfo(redirectURL);
        }
    }

}
