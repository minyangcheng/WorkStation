package com.example.collection;

import com.example.gson.GsonUtil;
import com.example.util.L;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by minyangcheng on 2016/11/18.
 */
public class LinkHashMapTest {

    public static void main(String args[]){
        Map<String,Integer> map=new LinkedHashMap<>(0,0.75f,true);
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        logContent(map);
        int i=map.get("b");
        L.d(i+"");
        logContent(map);
        map.put("d",4);
        logContent(map);
    }

    private static void logContent(Map<String,Integer> map){
        L.d("=========================");
        Iterator<Map.Entry<String,Integer>> iterator=map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Integer> entry=iterator.next();
            L.d(entry.getKey()+" "+entry.getValue());
        }
        L.d("=========================");
    }

}
