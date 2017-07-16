package com.min.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

/**
 * Created by minyangcheng on 2017/6/25.
 *
 * 模拟登陆，登陆成功后，再去访问我的信息页面
 */
public class VoteSimulation {

    Logger logger= LoggerFactory.getLogger(VoteSimulation.class);

    private static String redirectURL = "http://www.989y.cn/app/index.php?i=1&c=entry&rid=166&id=10197&do=view&m=tyzm_diamondvote&u=1462937&from=singlemessage&isappinstalled=0&wxref=mp.weixin.qq.com&wxref=mp.weixin.qq.com";

    private CloseableHttpClient httpclient;
    private String resultFilePaht="D:\\vote\\log.txt";

    public VoteSimulation(){
        List<Header> defaultHeaders=new ArrayList<Header>();
        defaultHeaders.add(new BasicHeader("User-Agent","Mozilla/5.0 (Linux; Android 6.0.1; MI 5s Build/MXB48T; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043305 Safari/537.36 MicroMessenger/6.5.8.1060 NetType/WIFI Language/zh_CN"));
        defaultHeaders.add(new BasicHeader("Host","43263716.ax.nofollow.51wtp.com"));
        defaultHeaders.add(new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/wxpic,image/sharpp,*/*;q=0.8"));
        defaultHeaders.add(new BasicHeader("Accept-Encoding","gzip, deflate"));
        defaultHeaders.add(new BasicHeader("Accept-Language","zh-CN,en-US;q=0.8"));
        defaultHeaders.add(new BasicHeader("Connection","keep-alive"));

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("PHPSESSID","edf282cb1a4efc4d7d5d3d5f9e83ff5e");
        cookie.setDomain("www.989y.cn");
        cookieStore.addCookie(cookie);
        cookieStore.addCookie(cookie);

        httpclient = HttpClientBuilder.create()
                            .setDefaultHeaders(defaultHeaders)
                            .setDefaultCookieStore(cookieStore)
                            .build();
    }

    private void go(String redirectLocation) {
        HttpGet httpget = new HttpGet(redirectLocation);
        CloseableHttpResponse response=null;
        try {
            response = httpclient.execute(httpget);
            logger.info(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void vote(int id){
        CloseableHttpResponse response=null;
        try {
            String url="http://www.989y.cn/app/index.php?i=1&c=entry&rid=166&id=10197&do=vote&m=tyzm_diamondvote";
            HttpPost httpost = new HttpPost("http://www.989y.cn/app/index.php?i=1&c=entry&rid=166&id="+id+"&do=vote&m=tyzm_diamondvote");
//            HttpHost proxy=new HttpHost("110.189.207.178", 35002);
//            RequestConfig requestConfig= RequestConfig.custom()
//                    .setConnectTimeout(50000)
//                    .setConnectionRequestTimeout(10000)
//                    .setSocketTimeout(50000)
//                    .setProxy(proxy)
//                    .setRedirectsEnabled(true)
//                    .build();
//            httpost.setConfig(requestConfig);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("latitude", "0"));
            nvps.add(new BasicNameValuePair("longitude", "0"));
            nvps.add(new BasicNameValuePair("verify", "0"));
            httpost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpclient.execute(httpost);
            JSONObject jsonObject= JSON.parseObject(EntityUtils.toString(response.getEntity()));
            jsonObject.put("id=",id);
            if(jsonObject.getString("status").equals("0")){
                if(jsonObject.getString("msg").contains("只能投1票")) {
                    log(jsonObject.toJSONString());
                }
            }else if(jsonObject.getString("status").equals("1")){
                log(jsonObject.toJSONString());
            }
            logger.info(jsonObject.toString());
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

    public void log(String str) {

        try {
            PrintWriter pw=new PrintWriter(new FileWriter(resultFilePaht,true));
            pw.println(str);
            pw.close () ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VoteSimulation app = new VoteSimulation();
//        app.go(redirectURL);
//        for(int i=1;i<30000;i++){
//            try {
//                Thread.sleep((long) (Math.random()*500));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            app.vote(i);
//        }
        app.vote(1);
    }

}
