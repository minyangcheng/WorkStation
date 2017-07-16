package com.min.crawler.vote;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by minyangcheng on 2017/7/5.
 */
public class AccountPool<T> {

    private Set<T> accountSet=new HashSet<>();

    public void add(T account){
        if(account==null){
            return;
        }
        accountSet.add(account);
    }

    public void add(Set<T> dataSet){
        if(dataSet==null||dataSet.isEmpty()){
            return;
        }
        this.accountSet.addAll(dataSet);
    }

    public Set<T> getData(){
        return accountSet;
    }

}
