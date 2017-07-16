package com.min.crawler.vote;

/**
 * Created by minyangcheng on 2017/7/5.
 */
public abstract class CarryTask implements Runnable {

    protected AccountPool accountPool;
    protected IpPool ipPool;
    protected HttpClientStack httpClientStack;

    public CarryTask setAcccountPool(AccountPool pool){
        this.accountPool=pool;
        return this;
    }

    public CarryTask setIpPool(IpPool ipPool){
        this.ipPool=ipPool;
        return this;
    }

    public CarryTask setHttpClientStack(HttpClientStack httpClientStack){
        this.httpClientStack=httpClientStack;
        return this;
    }

    @Override
    public abstract void run();

}
