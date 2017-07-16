package com.min.crawler.vote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.min.crawler.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minyangcheng on 2017/7/5.
 */
public class Controller {

    private Logger logger = LoggerFactory.getLogger(HttpClientStack.class);

    private String CHARSET = "utf-8";
    private String accountPath = "D:\\vote\\log.txt";
    private String ipPath = "D:\\vote\\ip.txt";

    private void execute() {
        HttpClientStack httpClientStack=getHttpClientStack();

        saveIpFromNet(httpClientStack);
        IpPool ipPool = getIpPool();

        new VoteTask()
                .setIpPool(ipPool)
                .setHttpClientStack(httpClientStack)
                .run();
    }

    public HttpClientStack getHttpClientStack(){
        List<Header> defaultHeaders = new ArrayList<>();
        defaultHeaders.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0.1; MI 5s Build/MXB48T; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043305 Safari/537.36 MicroMessenger/6.5.8.1060 NetType/WIFI Language/zh_CN"));
//        defaultHeaders.add(new BasicHeader("Host", "43263716.ax.nofollow.51wtp.com"));
        defaultHeaders.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/wxpic,image/sharpp,*/*;q=0.8"));
        defaultHeaders.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
        defaultHeaders.add(new BasicHeader("Accept-Language", "zh-CN,en-US;q=0.8"));
        defaultHeaders.add(new BasicHeader("Connection", "keep-alive"));

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("PHPSESSID", "58ca4e81186d9d523739706ebb9c37b7");
        cookie.setDomain("www.989y.cn");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        cookieStore.addCookie(cookie);
        HttpClientStack httpClientStack = new HttpClientStack(defaultHeaders, cookieStore);
        return httpClientStack;
    }

    public void saveIpFromNet(HttpClientStack httpClientStack){
        for(int i=0;i<30;i++){
            String getIpsUrl="http://www.xdaili.cn/ipagent//privateProxy/getDynamicIP/DD20177665045a7fvV/40530f9afd9511e6942200163e1a31c0?returnType=2";
            String getIpsResult=httpClientStack.get(getIpsUrl);
            logger.info(getIpsResult);
            if(StringUtils.isNotEmpty(getIpsResult)){
                JSONObject jsonObject=JSON.parseObject(getIpsResult);
                if(jsonObject.getIntValue("ERRORCODE")==0){
                    FileUtil.writeFile(ipPath,jsonObject.getString("RESULT")+"\n",true);
                }
            }
            try {
                Thread.sleep(16000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private IpPool getIpPool(){
        IpPool ipPool = new IpPool();
        List<String> ipStrList=FileUtil.readFileToList(ipPath,CHARSET);
        if(ipStrList!=null&&!ipStrList.isEmpty()){
            for (String s:ipStrList){
                JSONObject jsonObject=JSON.parseObject(s);
                try {
                    ipPool.add(jsonObject.getString("wanIp"),Integer.parseInt(jsonObject.getString("proxyport")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return ipPool;
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.execute();
    }

}
