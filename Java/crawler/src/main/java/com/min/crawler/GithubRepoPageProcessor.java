package com.min.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheguo on 2017/6/28.
 */
public class GithubRepoPageProcessor implements PageProcessor {

    Logger logger= LoggerFactory.getLogger(GithubRepoPageProcessor.class);

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3)
            .setSleepTime(1000)
            .addCookie("Hm_lpvt_b3895b6a7c0b5fc7e3127f4922dace2f","1498705374")
            .addCookie("Hm_lvt_b3895b6a7c0b5fc7e3127f4922dace2f","1498704932")
            .addCookie("JSESSIONID","374256BFE42303864B86E8874F8588C5")
            .addCookie("route","132ac1b85bae6888e5870ea0481c6b2b");
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
//
//        // 部分三：从页面发现后续的url地址来抓取
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());

        String title=page.getHtml().css("#title","text").toString();
        String content=page.getHtml().css("#content","text").toString();
        page.putField("title",title);
        page.putField("content",content);
        page.addTargetRequests(page.getHtml().links().all());
    }

    public Site getSite() {
        return site;
    }

    public static void deleteLogs(){
        File file=new File("D://crawler/log.log");
        if(file.exists()){
            file.delete();
        }
        file=new File("D://crawler/error.log");
        if(file.exists()){
            file.delete();
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        deleteLogs();
        Request request = new Request("http://10.10.13.6:8080/mobile/getDepositBankList");
        request.setMethod(HttpConstant.Method.POST);
        Map<String,Object> paramsMap=new HashMap<String, Object>();
        paramsMap.put("username","user");
        paramsMap.put("userpass","pass");

        request.setRequestBody(HttpRequestBody.form(paramsMap,"utf-8"));
        Spider.create(new GithubRepoPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://www.cheguo.com/usercenter/myinfo.html")
//                .addRequest(request)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new FilePipeline("D:\\webmagic\\"))
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }
}
