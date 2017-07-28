package com.min.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minyangcheng on 2017/6/25.
 *
 */
public class VoteSimulation {

    Logger logger= LoggerFactory.getLogger(VoteSimulation.class);

    private CloseableHttpClient httpclient;
    private String resultFilePaht="D:\\vote\\log.txt";

    public VoteSimulation(){
        List<Header> defaultHeaders=new ArrayList<Header>();
        defaultHeaders.add(new BasicHeader("User-Agent","Mozilla/5.0 (Linux; Android 6.0.1; MI 5s Build/MXB48T; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043305 Safari/537.36 MicroMessenger/6.5.8.1060 NetType/WIFI Language/zh_CN"));
        defaultHeaders.add(new BasicHeader("Host","www.6nvt.cn"));
        defaultHeaders.add(new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/wxpic,image/sharpp,*/*;q=0.8"));
        defaultHeaders.add(new BasicHeader("Accept-Encoding","gzip, deflate"));
        defaultHeaders.add(new BasicHeader("Accept-Language","zh-CN,en-US;q=0.8"));
        defaultHeaders.add(new BasicHeader("Connection","keep-alive"));

        httpclient = HttpClientBuilder.create()
                            .setDefaultHeaders(defaultHeaders)
                            .build();
    }

    public void vote(int id){
        CloseableHttpResponse response=null;
        try {
            String url="http://www.6nvt.cn//app/index.php?i=3&c=entry&rid=340&id=18405&do=vote&m=tyzm_diamondvote";
            HttpPost httpost = new HttpPost(url);
            httpost.setHeader("Cookie","PHPSESSID=29c184229e104c9d7add0dbdb3c0c86f; __cfduid=d179deff6518736ccb8e53752348399e61500552053; PHPSESSID=29c184229e104c9d7add0dbdb3c0c86f");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("latitude", "0"));
            nvps.add(new BasicNameValuePair("longitude", "0"));
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
        for(int i=1;i<30000;i++){
            try {
                Thread.sleep((long) (Math.random()*500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            app.vote(1);
        }
    }

}
