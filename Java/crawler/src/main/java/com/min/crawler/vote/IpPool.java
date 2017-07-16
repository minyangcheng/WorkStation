package com.min.crawler.vote;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by minyangcheng on 2017/7/5.
 */
public class IpPool {

    private Set<IpPortEntity> ipDataSet=new HashSet<>();

    public void add(String ip,int port){
        if(StringUtils.isEmpty(ip)||port<=0){
            return;
        }
        ipDataSet.add(new IpPortEntity(ip,port));
    }

    public void add(Set<IpPortEntity> dataSet){
        if(dataSet==null||dataSet.isEmpty()){
            return;
        }
        this.ipDataSet.addAll(dataSet);
    }

    public void add(List<IpPortEntity> dataList){
        if(dataList==null||dataList.isEmpty()){
            return;
        }
        this.ipDataSet.addAll(dataList);
    }

    public Set<IpPortEntity> getData(){
        return ipDataSet;
    }

}
