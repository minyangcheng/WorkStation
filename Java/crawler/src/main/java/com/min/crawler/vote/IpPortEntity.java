package com.min.crawler.vote;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by minyangcheng on 2017/7/5.
 */
public class IpPortEntity {

    private String ip;
    private int port;

    public IpPortEntity(){}

    public IpPortEntity(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof IpPortEntity) {
            IpPortEntity other = (IpPortEntity) obj;
            if (StringUtils.equals(this.getIp(),other.getIp())){
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if(StringUtils.isNotEmpty(this.getIp())){
            return this.getIp().hashCode();
        }
        return super.hashCode();
    }
}
