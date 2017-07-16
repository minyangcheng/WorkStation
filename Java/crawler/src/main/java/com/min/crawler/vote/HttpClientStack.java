package com.min.crawler.vote;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by minyangcheng on 2017/7/5.
 */
public class HttpClientStack {

    private Logger logger = LoggerFactory.getLogger(HttpClientStack.class);

    private String CHARSET = "utf-8";

    private CloseableHttpClient httpClient;

    public HttpClientStack(){
        this(null,null,null);
    }

    public HttpClientStack(List<Header> defaultHeaders, CookieStore defaultCookieStore) {
        this(defaultHeaders, defaultCookieStore, null);
    }

    public HttpClientStack(List<Header> defaultHeaders, CookieStore defaultCookieStore, HttpHost proxyHost) {
        HttpClientBuilder builder = HttpClientBuilder.create();
        if (defaultHeaders != null) {
            builder.setDefaultHeaders(defaultHeaders);
        }
        if (defaultCookieStore != null) {
            builder.setDefaultCookieStore(defaultCookieStore);
        }
        if (proxyHost != null) {
            builder.setProxy(proxyHost);
        }
        builder.setDefaultRequestConfig(getRequestConfig());
        httpClient = builder.build();
    }

    public String post(String url, Map<String, Object> params) {
        return post(url,params,null);
    }

    public String post(String url, Map<String, Object> params,RequestConfig requestConfig) {
        if (StringUtils.isEmpty(url)) return null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<>(params.size());
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if(requestConfig!=null){
                httpPost.setConfig(requestConfig);
            }
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String get(String url) {
        return this.get(url,null,null);
    }

    public String get(String url, Map<String, Object> params) {
        return this.get(url,params,null);
    }

    public String get(String url, Map<String, Object> params,RequestConfig requestConfig) {
        if (StringUtils.isEmpty(url)) return null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<>(params.size());
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            HttpGet httpGet = new HttpGet(url);
            if(requestConfig!=null){
                httpGet.setConfig(requestConfig);
            }
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public RequestConfig getRequestConfig(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000)
                .setSocketTimeout(15000)
                .setRedirectsEnabled(true)
                .build();
        return requestConfig;
    }

}
