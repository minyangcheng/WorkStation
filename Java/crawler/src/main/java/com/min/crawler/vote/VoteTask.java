package com.min.crawler.vote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by minyangcheng on 2017/7/5.
 */
public class VoteTask extends CarryTask {

    private Logger logger = LoggerFactory.getLogger(HttpClientStack.class);

    @Override
    public void run() {
        Map<String, Object> map = new HashMap<>();
        map.put("latitude", "0");
        map.put("longitude", "0");
        map.put("verify", "0");
        String url="http://www.989y.cn/app/index.php?i=1&c=entry&rid=166&id=10178&do=vote&m=tyzm_diamondvote";
        for(int i=0;i<100;i++){
            try {
                List<IpPortEntity> ipDataList=new ArrayList<>();
                ipDataList.addAll(ipPool.getData());

                int index=(new Random()).nextInt(ipDataList.size());
                IpPortEntity ipPortEntity=ipDataList.get(index);
                RequestConfig requestConfig=RequestConfig.copy(httpClientStack.getRequestConfig())
                        .setProxy(new HttpHost(ipPortEntity.getIp(),ipPortEntity.getPort()))
                        .build();
                String result=httpClientStack.post(url,map,requestConfig);
                if(StringUtils.isEmpty(result)){
                    logger.info("url={}，投票失败",url);
                }else{
                    logger.info("url={} , result={}",url,JSON.parseObject(result).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
